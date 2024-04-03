package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.exception.ProductNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Setter
@Getter
public class UpdateOrderStatus {

    private OrderRepository orderRepository;

    /**
     * Updates the status of an order.
     *
     * @param orderId   The ID of the order to be updated.
     * @param newStatus The new status to be assigned to the order.
     * @return The updated order.
     * @throws RuntimeException if no order is found with the given ID.
     */
    public Order updateOrderStatus(Integer orderId, String newStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("The order with ID " + orderId + " does not exist"));
        order.setOrderStatus(newStatus);
        return orderRepository.save(order);

    }
}
