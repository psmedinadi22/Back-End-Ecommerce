package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrder;
import com.ecommerce.prototype.infrastructure.client.response.TransactionResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.OrderProvider;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.OrderJPARepository;
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

        Order order = orderProvider.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        Payment payment = new Payment();
        payment.setTransactionID(transactionResponse.getTransactionId());
        payment.setPaymentDate(new Date());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(transactionResponse.getState());
       // payment.setOrder(order);

        return paymentRepository.save(payment);
    }
}

