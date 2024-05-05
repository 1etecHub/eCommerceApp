package com.eCommerceApp.eCommerce.service;

import com.eCommerceApp.eCommerce.dto.LoginBody;
import com.eCommerceApp.eCommerce.dto.RegistrationBody;
import com.eCommerceApp.eCommerce.entities.AppUser;
import com.eCommerceApp.eCommerce.exception.EmailFailureException;
import com.eCommerceApp.eCommerce.exception.EmailNotFoundException;
import com.eCommerceApp.eCommerce.exception.UserAlreadyExistsException;
import com.eCommerceApp.eCommerce.exception.UserNotVerifiedException;

public interface UserService {


    public AppUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException;

    String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException;

    void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException;
}
