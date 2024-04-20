package com.eCommerceApp.eCommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

/**
 * The information required to register a user.
 */
public class RegistrationBody {

  /**
   * The username.
   */
  @NotNull
  @NotBlank
  @Size(min = 3, max = 255)
  private String username;
  /**
   * The email.
   */
  @NotNull
  @NotBlank
  @Email
  private String email;
  /**
   * The password.
   */
  @NotNull
  @NotBlank
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
  @Size(min = 6, max = 32)
  private String password;
  /**
   * The first name.
   */
  @NotNull
  @NotBlank
  private String firstName;

  /**
   * The last name.
   */
  @NotNull
  @NotBlank
  private String lastName;

}

