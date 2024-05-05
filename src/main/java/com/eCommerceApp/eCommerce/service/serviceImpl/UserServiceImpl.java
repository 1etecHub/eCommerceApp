package com.eCommerceApp.eCommerce.service.serviceImpl;

import com.eCommerceApp.eCommerce.dao.AppUserDAO;
import com.eCommerceApp.eCommerce.dao.VerificationTokenDAO;
import com.eCommerceApp.eCommerce.dto.LoginBody;
import com.eCommerceApp.eCommerce.dto.RegistrationBody;
import com.eCommerceApp.eCommerce.entities.AppUser;
import com.eCommerceApp.eCommerce.entities.VerificationToken;
import com.eCommerceApp.eCommerce.exception.EmailFailureException;
import com.eCommerceApp.eCommerce.exception.EmailNotFoundException;
import com.eCommerceApp.eCommerce.exception.UserAlreadyExistsException;
import com.eCommerceApp.eCommerce.exception.UserNotVerifiedException;
import com.eCommerceApp.eCommerce.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EncryptionService encryptionService;

    private final AppUserDAO appUserDAO;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final VerificationTokenDAO verificationTokenDAO;
    @Override
    public AppUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {

        if (appUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || appUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        AppUser user = new AppUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return appUserDAO.save(user);

    }

    @Override
    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<AppUser> opUser = appUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            AppUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;

    }

    private VerificationToken createVerificationToken(AppUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            AppUser user = verificationToken.getUser();
            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                appUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Sends the user a forgot password reset based on the email provided.
     * @param email The email to send to.
     * @throws EmailNotFoundException Thrown if there is no user with that email.
     * @throws EmailFailureException
     */
    @Override
    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<AppUser> opUser = appUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            AppUser user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        } else {
            throw new EmailNotFoundException();
        }
    }


    /**
     * Resets the users password using a given token and email.
     * @param body The password reset information.
     */
    /*public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            localUserDAO.save(user);
        }
    }*/
}
