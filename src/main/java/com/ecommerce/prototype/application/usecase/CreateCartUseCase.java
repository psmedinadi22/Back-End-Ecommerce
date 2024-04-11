package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.CartPendingPaymentException;
import com.ecommerce.prototype.application.usecase.exception.UserDisabledException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@AllArgsConstructor
public class CreateCartUseCase {

    private CartRepository cartRepository;
    private GetUserCartsUseCase getUserCartsUseCase;
    private UserRepository userRepository;

    /**
     * Create a new cart for a given user, after checking if there are any pending carts.
     *
     * @param userId The ID of the user for which the cart is created.
     * @return The cart created.
     * @throws CartPendingPaymentException If the user already has a cart pending payment.
     */
    public Optional<Cart> createCart(Integer userId){

        User user = retrieveUser(userId);

        if (user.getIsDeleted()) {
            throw new UserDisabledException("The user with ID: " + userId + " is disabled.");
        }

        List<Cart> userCarts = getUserCartsUseCase.getUserCarts(userId);
        for (Cart cart : userCarts) {
            if ("outstanding".equals(cart.getStatus())) {
                throw new CartPendingPaymentException("There is a cart pending payment with ID: " + cart.getCartId());
            }
        }
        return cartRepository.createCart(userId);
    }
    private User retrieveUser(Integer userId) {
        Userdb userdb = userRepository.findById(userId);
        if (userdb!= null) {
            return MapperUser.toUserDomain(userdb);
        } else {
            throw new UserNoExistException("User not found with ID: " + userId);
        }
    }
}
