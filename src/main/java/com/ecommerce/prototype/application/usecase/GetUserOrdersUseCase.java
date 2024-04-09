package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperCart;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrder;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
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

    public List<Order> getUserOrders(Integer userId) {

        Userdb userdb = userRepository.findById(userId);
        if(userdb == null){
            throw new UserNoExistException("User not found with ID: " + userId);
        }
        List<Orderdb> orderdbs = orderRepository.findByUserId(userId);

        List<Order> orders = new ArrayList<>();
        for (Orderdb orderdb : orderdbs) {
            orders.add(MapperOrder.mapToDomainWithoutUser(orderdb));
        }
        return orders;
    }

}
