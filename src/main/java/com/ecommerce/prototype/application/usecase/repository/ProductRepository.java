package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> save(Product product);
    boolean existsById(Integer productId);
    void deleteById(Integer productId);
    Optional<Product> findById(Integer productId);
    Optional<Product> findByName(String productName);


}
