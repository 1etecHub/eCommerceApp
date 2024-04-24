package com.eCommerceApp.eCommerce.service.serviceImpl;

import com.eCommerceApp.eCommerce.dao.AppUserDAO;
import com.eCommerceApp.eCommerce.dto.RegistrationBody;
import com.eCommerceApp.eCommerce.entities.AppUser;
import com.eCommerceApp.eCommerce.exception.EmailFailureException;
import com.eCommerceApp.eCommerce.exception.UserAlreadyExistsException;
import com.eCommerceApp.eCommerce.service.EncryptionService;
import com.eCommerceApp.eCommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EncryptionService encryptionService;

    private final AppUserDAO appUserDAO;
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
        //user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        //VerificationToken verificationToken = createVerificationToken(user);
        //emailService.sendVerificationEmail(verificationToken);
        return appUserDAO.save(user);

    }
}
