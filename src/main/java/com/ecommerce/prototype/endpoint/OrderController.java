package com.ecommerce.prototype.endpoint;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.usecase.GetOrderUseCase;
import com.ecommerce.prototype.application.usecase.GetUserOrdersUseCase;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
            Optional<Order> orderOptional = getOrderUseCase.getOrder(orderId);
            return orderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
