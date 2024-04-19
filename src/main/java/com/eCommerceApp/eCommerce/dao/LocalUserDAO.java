package com.eCommerceApp.eCommerce.dao;


import com.eCommerceApp.eCommerce.entities.AppUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Data Access Object for the LocalUser data.
 */
public interface LocalUserDAO extends ListCrudRepository<AppUser, Long> {

  Optional<AppUser> findByUsernameIgnoreCase(String username);

  Optional<AppUser> findByEmailIgnoreCase(String email);

}
