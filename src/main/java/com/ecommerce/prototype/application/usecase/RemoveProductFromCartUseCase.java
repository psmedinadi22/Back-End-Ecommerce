package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.ProductNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperProduct;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RemoveProductFromCartUseCase {

    private ProductRepository productRepository;
    private CartRepository cartRepository;

    /**
     * Removes a product from the cart.
     *
     * @param cartId    The ID of the cart from which the product will be removed.
     * @param productId The ID of the product to be removed from the cart.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public void removeProductFromCart(Integer cartId, Integer productId) {

        cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        cartRepository.removeProduct(cartId, product);
    }
}
