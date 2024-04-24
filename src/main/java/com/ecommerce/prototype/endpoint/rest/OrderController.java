package com.ecommerce.prototype.endpoint.rest;

import com.ecommerce.prototype.application.domain.Address;
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

            OrderResponse orderResponse =  OrderResponse.builder()
                    .withOrderId(order.getOrderID())
                    .withCreationDate(order.getCreationDate())
                    .withTotalAmount(order.getTotalAmount())
                    .withOrderStatus(order.getOrderStatus())
                    .withUserId(order.getBuyer().getId())
                    .withCartId(order.getCart().getCartId())
                    .withTokenId(order.getCard().getTokenId())
                    .withShippingAddress(order.getShippingAddress())
                    .withBillingAddress(order.getBillingAddress())
                    .build();

            return ResponseEntity.ok(orderResponse);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
