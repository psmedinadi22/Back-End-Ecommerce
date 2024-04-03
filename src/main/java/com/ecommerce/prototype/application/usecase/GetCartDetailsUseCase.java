package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import lombok.AllArgsConstructor;
import java.util.Optional;

@AllArgsConstructor
public class GetCartDetailsUseCase {

    private CartRepository cartRepository;

    /**
     * Retrieves the details of a cart by its ID.
     *
     * @param cartId The ID of the cart to retrieve details for.
     * @return An Optional containing the cart details, or empty if the cart with the given ID does not exist.
     */

    public Optional<Cart> getCartDetails(int cartId) {

        return cartRepository.getCartDetails(cartId);
    }
}