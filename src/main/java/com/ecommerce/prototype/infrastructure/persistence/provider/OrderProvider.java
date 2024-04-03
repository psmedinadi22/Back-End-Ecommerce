package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrder;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.OrderJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderProvider implements OrderRepository {

    private final OrderJPARepository orderJPARepository;
    private final UserProvider userProvider;

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
        return MapperOrder.mapToDomain(savedOrder);
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
        return Optional.of(MapperOrder.mapToDomain(orderdb));
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

        Optional<Orderdb> orderdbOptional = orderJPARepository.findById(orderId);
        Orderdb orderdb = orderdbOptional.orElseThrow(() -> new RuntimeException("Order not found"));
        return Optional.of(MapperOrder.mapToDomain(orderdb));
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
     * @param userId The unique identifier of the user to whom the order is associated.
     * @return Order The newly created order.
     */
    @Override
    public Order createOrder(Order order, Integer userId) {
        Orderdb orderdb = MapperOrder.mapToModel(order);
        orderdb.setUser(userProvider.findById(userId));
        orderdb = orderJPARepository.save(orderdb);
        return MapperOrder.mapToDomain(orderdb);
    }

}
