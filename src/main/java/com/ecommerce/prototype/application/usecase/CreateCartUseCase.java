package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.usecase.exception.CartPendingPaymentException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CreateCartUseCase {

    private CartRepository cartRepository;
    private GetUserCartsUseCase getUserCartsUseCase;

    /**
     * Create a new cart for a given user, after checking if there are any pending carts.
     *
     * @param userId The ID of the user for which the cart is created.
     * @return The cart created.
     * @throws CartPendingPaymentException If the user already has a cart pending payment.
     */
    public Optional<Cart> createCart(Integer userId){

        List<Cart> userCarts = getUserCartsUseCase.getUserCarts(userId);
        for (Cart cart : userCarts) {
            if ("outstanding".equals(cart.getStatus())) {
                throw new CartPendingPaymentException("There is a cart pending payment for user with ID: " + userId);
            }
        }
        return cartRepository.createCart(userId);
    }
}
