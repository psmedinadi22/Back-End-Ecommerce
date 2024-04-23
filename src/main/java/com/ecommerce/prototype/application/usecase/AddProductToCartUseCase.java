package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.CartStateException;
import com.ecommerce.prototype.application.usecase.exception.InsufficientProductQuantityException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@AllArgsConstructor
public class AddProductToCartUseCase {

    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private static final Logger logger = LoggerFactory.getLogger(AddProductToCartUseCase.class);


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

        logger.info("Adding product with ID {} to cart with ID {}", productId, cartId);
        Cart cart = findCartById(cartId);
        checkCartStatus(cart.getStatus());
        Product product = findProductById(productId);
        validateProductQuantity(product, quantity);
        addToCart(product, quantity, cartId);
        logger.info("Product with ID {} added to cart with ID {}", productId, cartId);
    }

    /**
     * Finds a cart by its ID.
     *
     * @param cartId The ID of the cart to find.
     * @return The cart object if found.
     * @throws IllegalArgumentException If the provided cart ID is invalid.
     */
    private Cart findCartById(int cartId) {

        logger.debug("Finding cart with ID {}", cartId);
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

        logger.debug("Checking cart status: {}", cartStatus);
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
    private Product findProductById(Integer productId) {

        logger.debug("Finding product with ID {}", productId);
        return productRepository.findById(productId)
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

        logger.debug("Validating product quantity: {} for product with ID {}", quantity, product.getProductId());
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

        logger.debug("Adding product with ID {} and quantity {} to cart with ID {}", product.getProductId(), quantity, cartId);
        cartRepository.addProductToCart(product, quantity, cartId);
    }
}
