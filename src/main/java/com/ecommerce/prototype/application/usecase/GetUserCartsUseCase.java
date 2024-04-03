package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
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
import java.util.stream.Collectors;

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

        Userdb userdb = userRepository.findById(userId);
        if(userdb == null){
            throw new UserNoExistException("User not found with ID: " + userId);
        }
        List<Cartdb> cartdbs = cartRepository.findByUserId(userId);

        List<Cart> carts = new ArrayList<>();
        for (Cartdb cartdb : cartdbs) {
            carts.add(MapperCart.mapToDomain(cartdb));
        }
        return carts;
    }
}

