package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperCart;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@Service
public class GetUserCartsUseCase {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    /**
     * Retrieves all carts associated with a user.
     *
     * @param userId The ID of the user.
     * @return A list of carts associated with the user.
     * @throws UserNoExistException if no user is found with the given ID.
     */

    public List<Cart> getUserCarts(Integer userId) {

        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new UserNoExistException("User not found with ID: " + userId);
        }
        List<Cart> carts = cartRepository.findByUserId(userId);

        return carts;
    }
}

