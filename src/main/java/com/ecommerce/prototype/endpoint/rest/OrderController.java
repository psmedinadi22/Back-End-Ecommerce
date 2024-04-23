package com.ecommerce.prototype.endpoint.rest;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.GetOrderUseCase;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.infrastructure.client.payu.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
public class OrderController {

    private final GetOrderUseCase getOrderUseCase;


    /**
     * Retrieves a purchase order by its ID.
     * @param orderId ID of the purchase order.
     * @return ResponseEntity with the purchase order if found, or an error response if not found.
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable int orderId) {
        try {
            Order order = getOrderUseCase.getOrder(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with Id: " + orderId));

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(order.getOrderID());
            orderResponse.setCreationDate(order.getCreationDate());
            orderResponse.setTotalAmount(order.getTotalAmount());
            orderResponse.setOrderStatus(order.getOrderStatus());
            orderResponse.setUserId(order.getBuyer().getId());

            return ResponseEntity.ok(orderResponse);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
