package com.eCommerceApp.eCommerce.service;

import com.eCommerceApp.eCommerce.dto.RegistrationBody;
import com.eCommerceApp.eCommerce.entities.AppUser;
import com.eCommerceApp.eCommerce.exception.EmailFailureException;
import com.eCommerceApp.eCommerce.exception.UserAlreadyExistsException;

public interface UserService {


    public AppUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException;
}
