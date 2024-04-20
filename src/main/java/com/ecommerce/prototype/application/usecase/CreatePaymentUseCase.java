package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import com.ecommerce.prototype.infrastructure.client.payu.response.TransactionResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.OrderProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CreatePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final OrderProvider orderProvider;
    private static final Logger logger = LoggerFactory.getLogger(CreatePaymentUseCase.class);


    /**
     * Creates a new payment and saves it to the database.
     *
     * @param transactionResponse The transaction response.
     * @param orderId             The ID of the order associated with the payment.
     * @param paymentMethod       The payment method used.
     * @return The created payment saved in the database.
     * @throws OrderNotFoundException If the order with the provided ID is not found.
     */
    public Paymentdb createPayment(TransactionResponse transactionResponse, Integer orderId, String paymentMethod) {

        logger.debug("Creating payment for order with ID {}", orderId);
        Order order = orderProvider.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        Payment payment = new Payment();
        payment.setTransactionID(transactionResponse.getTransactionId());
        payment.setPaymentDate(new Date());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(transactionResponse.getState());
        logger.info("Payment created successfully for order with ID {}", orderId);
        return paymentRepository.save(payment);
    }
}

