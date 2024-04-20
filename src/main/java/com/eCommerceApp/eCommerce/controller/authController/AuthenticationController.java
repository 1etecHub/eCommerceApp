package com.eCommerceApp.eCommerce.controller.authController;


import com.eCommerceApp.eCommerce.dto.RegistrationBody;
import com.eCommerceApp.eCommerce.exception.EmailFailureException;
import com.eCommerceApp.eCommerce.exception.UserAlreadyExistsException;
import com.eCommerceApp.eCommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

        private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
