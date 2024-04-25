package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CardRepository;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrder;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.OrderJPARepository;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.UserJPARepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderProvider implements OrderRepository {

    private final OrderJPARepository orderJPARepository;
    private final UserJPARepository userJPARepository;
    private final CardRepository cardRepository;
    private final CartRepository cartRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderProvider.class);

    /**
     * Saves an order.
     *
     * @param order The order to be saved.
     * @return The saved order.
     */
    @Override
    public Order save(Order order) {

        Orderdb orderdb = MapperOrder.mapToModel(order);
        Orderdb savedOrder = orderJPARepository.save(orderdb);
        Cart cart = cartRepository.findById(orderdb.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + orderdb.getCartId()));
        Card card = cardRepository.findByTokenId(orderdb.getTokenId());

        return MapperOrder.mapToDomain(savedOrder, cart, card);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID.
     * @throws OrderNotFoundException If the order with the specified ID does not exist.
     */
    @Override
    public Optional<Order> findById(Integer orderId) {

        Orderdb orderdb = orderJPARepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        Cart cart = cartRepository.findById(orderdb.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + orderdb.getCartId()));
        Card card = cardRepository.findByTokenId(orderdb.getTokenId());
        return Optional.of(MapperOrder.mapToDomain(orderdb, cart, card));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID.
     * @throws RuntimeException If the order with the specified ID does not exist.
     */
    @Override
    public Optional<Order> getOrder(int orderId) {

        Optional<Orderdb> orderdbOptional = orderJPARepository.findByOrderID(orderId);

        Orderdb orderdb = orderdbOptional.orElseThrow(() -> new RuntimeException("Order not found with Id: " + orderId));
        Cart cart = cartRepository.findById(orderdb.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + orderdb.getCartId()));
        Card card = cardRepository.findByTokenId(orderdb.getTokenId());

        Order orderSaved = MapperOrder.mapToDomain(orderdb, cart, card);
        return Optional.of(orderSaved);
    }

    /**
     * Retrieves a list of orders associated with the specified user ID.
     *
     * @param userId The unique identifier of the user whose orders are to be retrieved.
     * @return List<Orderdb> The list of orders associated with the specified user ID.
     */
    @Override
    public List<Orderdb> findByUserId(Integer userId) {

        return orderJPARepository.findByUserId(userId);
    }

    /**
     * Creates a new order and associates it with the specified user ID.
     *
     * @param order The order to be created.
     * @param buyerId The unique identifier of the user to whom the order is associated.
     * @return Order The newly created order.
     */
    @Override
    public Order createOrder(Order order, Integer buyerId) {

        Orderdb orderdb = MapperOrder.mapToModel(order);

        var user = userJPARepository.findById(buyerId)
                                    .orElseThrow(() -> new UserNoExistException("The user not found in database"));

        orderdb.setUser(user);

        Orderdb orderSaved = orderJPARepository.save(orderdb);

            return MapperOrder.mapToDomain(orderSaved, order.getCart(), order.getCard());
    }
}
