package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.CartPendingPaymentException;
import com.ecommerce.prototype.application.usecase.exception.UserDisabledException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCartUseCase {

    private CartRepository cartRepository;
    private GetUserCartsUseCase getUserCartsUseCase;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateCartUseCase.class);

    /**
     * Create a new cart for a given user, after checking if there are any pending carts.
     *
     * @param userId The ID of the user for which the cart is created.
     * @return The cart created.
     * @throws CartPendingPaymentException If the user already has a cart pending payment.
     */
    public Optional<Cart> createCart(Integer userId) {

        User user = retrieveUser(userId);
        validateUser(user);
        validateUserCarts(userId);

        return cartRepository.createCart(userId);
    }

    /**
     * Retrieves a user by ID from the database.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user object if found.
     * @throws UserNoExistException If the user is not found in the database.
     */
    private User retrieveUser(Integer userId) {

        logger.debug("Retrieving user with ID {}", userId);
        User user = userRepository.findUserById(userId);
        if (user != null) {
            logger.debug("User found with ID {}", userId);
            return user;
        } else {
            logger.error("User not found with ID {}", userId);
            throw new UserNoExistException("User not found with ID: " + userId);
        }
    }

    /**
     * Validates if the retrieved user is disabled.
     *
     * @param user The user object to validate.
     * @throws UserDisabledException If the user is disabled.
     */
    private void validateUser(User user) {

        if (user.getIsDeleted()) {
            throw new UserDisabledException("The user with ID: " + user.getUserId() + " is disabled.");
        }
    }

    /**
     * Validates if the user has any pending carts.
     *
     * @param userId The ID of the user to check for pending carts.
     * @throws CartPendingPaymentException If the user has any pending carts.
     */
    private void validateUserCarts(Integer userId) {

        List<Cart> userCarts = getUserCartsUseCase.getUserCarts(userId);
        for (Cart cart : userCarts) {
            if ("outstanding".equals(cart.getStatus())) {
                throw new CartPendingPaymentException("There is a cart pending payment with ID: " + cart.getCartId());
            }
        }
    }
}
