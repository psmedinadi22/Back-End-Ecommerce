package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class GetCartProductsUseCase {

    private CartRepository cartRepository;

    /**
     * Retrieves the products associated with a cart identified by its ID.
     *
     * @param cartId The ID of the cart to retrieve products for.
     * @return A list of products associated with the specified cart.
     * @throws CartNotFoundException if the cart with the given ID does not exist.
     */
    public List<Product> getCartProducts(int cartId) {

        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {

            Cart cart = cartOptional.get();

            return cart.getProducts();

        } else {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found");
        }
    }
}

