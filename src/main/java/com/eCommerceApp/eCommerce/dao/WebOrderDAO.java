package com.eCommerceApp.eCommerce.dao;


import com.eCommerceApp.eCommerce.entities.AppOrder;
import com.eCommerceApp.eCommerce.entities.AppUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object to access WebOrder data.
 */
@Repository
public interface WebOrderDAO extends ListCrudRepository<AppOrder, Long> {

  List<AppOrder> findByUser(AppUser user);

}
