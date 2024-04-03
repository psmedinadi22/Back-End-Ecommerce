package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import java.util.List;
import java.util.Optional;

public interface CartRepository {

    void addProductToCart(Product product, int quantity, int cartId);

    void removeProduct(Integer cartId, Product product);

    Optional<Cart> getCartDetails(int cartId);

    Optional<Cart> createCart(Integer userId);

    Optional<Cartdb> findById(int cartId);

    void save(Cartdb cartdb);

    String getCartStatus(int cartId);
    List<Cartdb> findByUserId(Integer userId);
}
