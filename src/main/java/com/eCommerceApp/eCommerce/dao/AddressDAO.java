package com.eCommerceApp.eCommerce.dao;


import com.eCommerceApp.eCommerce.entities.Address;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for the Address data.
 */
@Repository
public interface AddressDAO extends ListCrudRepository<Address, Long> {

  List<Address> findByUser_Id(Long id);

}
