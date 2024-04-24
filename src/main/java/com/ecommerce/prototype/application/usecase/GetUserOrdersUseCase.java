package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CardRepository;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrder;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GetUserOrdersUseCase {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CardRepository cardRepository;
    private final CartRepository cartRepository;

    public List<Order> getUserOrders(Integer userId) {

        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new UserNoExistException("User not found with ID: " + userId);
        }
        List<Orderdb> orderdbs = orderRepository.findByUserId(userId);
        List<Order> orders = new ArrayList<>();
        for (Orderdb orderdb : orderdbs) {
            Cart cart = cartRepository.findById(orderdb.getCartId())
                    .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + orderdb.getCartId()));
            Card card = cardRepository.findByTokenId(orderdb.getTokenId());
            orders.add(MapperOrder.mapToDomain(orderdb , cart, card));
        }
        return orders;
    }
}
