package com.ecommerce.prototype.endpoint;

import com.ecommerce.prototype.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.prototype.application.usecase.exception.*;
import com.ecommerce.prototype.infrastructure.client.request.PaymentRequest;
import com.ecommerce.prototype.infrastructure.client.response.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase paymentProcessUseCase;

    /**
     * Processes a payment request.
     *
     * @param paymentRequest The request body containing the details of the payment to be processed.
     * @return ResponseEntity<?> A response entity containing the result of the payment processing if successful,
     * otherwise an error message and the corresponding HTTP status code.
     */
    @PostMapping("/payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {

        try {
            PaymentResponse result = paymentProcessUseCase.processPayment(paymentRequest);
            return ResponseEntity.ok(result);
        } catch (UserNoExistException e) {
            return ResponseEntity.badRequest().body("User not found: " + e.getMessage());
        } catch (CartNotFoundException e) {
            return ResponseEntity.badRequest().body("Cart not found: " + e.getMessage());
        } catch (InvalidPaymentException | UnauthorizedCartAccessException | CartStateException e) {
            return ResponseEntity.badRequest().body("Invalid Payment Request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error : " + e.getMessage());
        }
    }


}


