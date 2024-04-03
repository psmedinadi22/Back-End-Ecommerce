package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.CartStateException;
import com.ecommerce.prototype.application.usecase.exception.InsufficientProductQuantityException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperProduct;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AddProductToCartUseCase {

    private GetProductUseCase getProductUseCase;
    private CartRepository cartRepository;

    /**
     * Adds a product to the specified cart.
     *
     * @param productId The ID of the product to add.
     * @param quantity The quantity of the product to add.
     * @param cartId The ID of the cart to add the product to.
     * @throws IllegalArgumentException If the provided cart ID or product ID is invalid.
     * @throws CartStateException If the cart has a status other than "outstanding".
     * @throws InsufficientProductQuantityException If the quantity of the product is insufficient.
     */
    public void addProductToCart(Integer productId, int quantity, int cartId) {
        Cartdb cart = findCartById(cartId);
        checkCartStatus(cart.getStatus());
        Product product = MapperProduct.toProductDomain(findProductById(productId));
        validateProductQuantity(product, quantity);
        addToCart(product, quantity, cartId);
    }

    /**
     * Finds a cart by its ID.
     *
     * @param cartId The ID of the cart to find.
     * @return The cart object if found.
     * @throws IllegalArgumentException If the provided cart ID is invalid.
     */
    private Cartdb findCartById(int cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));
    }

    /**
     * Checks the status of the cart.
     *
     * @param cartStatus The status of the cart.
     * @throws CartStateException If the cart status is not "outstanding".
     */
    private void checkCartStatus(String cartStatus) {
        if (!"outstanding".equals(cartStatus)) {
            throw new CartStateException("Cannot add products to cart with status: " + cartStatus);
        }
    }

    /**
     * Finds a product by its ID.
     *
     * @param productId The ID of the product to find.
     * @return The product object if found.
     * @throws IllegalArgumentException If the provided product ID is invalid.
     */
    private Productdb findProductById(Integer productId) {
        return getProductUseCase.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
    }

    /**
     * Validates the quantity of the product.
     *
     * @param product The product to validate.
     * @param quantity The quantity to validate.
     * @throws InsufficientProductQuantityException If the quantity of the product is insufficient.
     */
    private void validateProductQuantity(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            throw new InsufficientProductQuantityException("Insufficient quantity of product with ID: " + product.getProductId());
        }
    }

    /**
     * Adds the product to the cart.
     *
     * @param product The product to add.
     * @param quantity The quantity of the product.
     * @param cartId The ID of the cart to add the product to.
     */
    private void addToCart(Product product, int quantity, int cartId) {
        cartRepository.addProductToCart(product, quantity, cartId);
    }

}
