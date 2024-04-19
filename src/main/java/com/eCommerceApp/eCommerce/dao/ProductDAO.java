package com.eCommerceApp.eCommerce.dao;


import com.eCommerceApp.eCommerce.entities.Product;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Data Access Object for accessing Product data.
 */
public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
