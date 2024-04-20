package com.eCommerceApp.eCommerce.dao;


import com.eCommerceApp.eCommerce.entities.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for accessing Product data.
 */
@Repository
public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
