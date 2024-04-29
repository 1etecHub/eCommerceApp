package com.eCommerceApp.eCommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "app_User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "username", unique = true, length = 20)
    private String username;

    @Column(nullable = false, name = "password", length = 30)
    private String password;

    @Column(nullable = false, name = "email", unique = true, length = 30)
    private String email;

    @Column(nullable = false, name = "firstName")
    private String firstName;

    @Column(nullable = false, name = "lastName")
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser")
    private List<Address> addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AppOrder> order;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    /**
     * Is the email verified?
     * @return True if it is, false otherwise.
     */
    public Boolean isEmailVerified() {
        return emailVerified;
    }

}
