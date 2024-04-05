package com.ecommerce.prototype.endpoint;

import com.ecommerce.prototype.application.domain.OrderDetail;
import com.ecommerce.prototype.application.usecase.GetOrderDetailUseCase;
import com.ecommerce.prototype.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.prototype.application.usecase.exception.OrderDetailNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderDetailController {

    private final GetOrderDetailUseCase getOrderDetailUseCase;
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    /**
     * Retrieves an order detail by its ID.
     * @param orderDetailId ID of the order detail.
     * @return ResponseEntity with the order detail if found, or 404 NOT FOUND if not found.
     */
    @GetMapping("/orderDetail/{orderDetailId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable int orderDetailId) {
        try {
            Optional<OrderDetail> orderOptional = getOrderDetailUseCase.getOrderDetailById(orderDetailId);
            logger.info("order: " + orderOptional);
            return orderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (OrderDetailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
