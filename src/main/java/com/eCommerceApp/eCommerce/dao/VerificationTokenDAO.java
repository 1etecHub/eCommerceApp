package com.eCommerceApp.eCommerce.dao;


import com.eCommerceApp.eCommerce.entities.AppUser;
import com.eCommerceApp.eCommerce.entities.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the VerificationToken data.
 */
public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {

  Optional<VerificationToken> findByToken(String token);

  void deleteByUser(AppUser user);

  List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);

}
