package com.ecommerce.prototype.endpoint.rest;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.application.usecase.GetPaymentUseCase;
import com.ecommerce.prototype.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.prototype.application.usecase.exception.*;
import com.ecommerce.prototype.endpoint.rest.request.PaymentRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase paymentProcessUseCase;
    private final GetPaymentUseCase getPaymentUseCase;

    /**
     * Processes a payment request.
     *
     * @param paymentRequest The request body containing the details of the payment to be processed.
     * @return ResponseEntity<?> A response entity containing the result of the payment processing if successful,
     * otherwise an error message and the corresponding HTTP status code.
     */
    @PostMapping("/payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {

        return ResponseEntity.ok(
                paymentProcessUseCase.processPayment(paymentRequest.toPayment()));
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getPayment(@PathVariable Integer paymentId) {
        try {
            Optional<Payment> payment = getPaymentUseCase.getPayment(paymentId);
            if (payment.isPresent()) {
                return ResponseEntity.ok(payment.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}


