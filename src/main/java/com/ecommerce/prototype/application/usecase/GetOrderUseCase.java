package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetOrderUseCase {

    private OrderRepository orderRepository;

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return An optional containing the order if found, otherwise empty.
     */
    public Optional<Order> getOrder(int orderId) {

        return orderRepository.getOrder(orderId);
    }

}
