package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.PaymentResponse;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.PaymentNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.PaymentJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PaymentProvider implements PaymentRepository {

    private final PaymentJPARepository paymentJPARepository;
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentProvider.class);
    @Override
    public PaymentResponse save(PaymentResponse paymentResponse) {

        var paymentdb = Paymentdb.builder()
                .withExternalState(paymentResponse.getExternalState())
                .withStatus(paymentResponse.getStatus())
                .withState(paymentResponse.getState())
                .withError(paymentResponse.getError())
                .withMessage(paymentResponse.getMessage())
                .withOrderId(paymentResponse.getOrder().getOrderID())
                .withExternalId(paymentResponse.getExternalId())
                .withCreationDate(paymentResponse.getCreationDate())
                .withUser(MapperUser.toUserModel(paymentResponse.getOrder().getBuyer().toUser()))
                .build();

        Paymentdb paymentdbSaved = paymentJPARepository.save(paymentdb);

        paymentResponse.setId(String.valueOf(paymentdbSaved.getPaymentID()));
        return paymentResponse;
    }

    @Override
    public Optional<PaymentResponse> getPayment(Integer paymentId) {

        Paymentdb paymentdb = paymentJPARepository.findByPaymentID(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: "+ paymentId));
        var paymentResponse = PaymentResponse.builder()
                .withId(String.valueOf(paymentdb.getPaymentID()))
                .withStatus(paymentdb.getStatus())
                .withState(paymentdb.getState())
                .withError(paymentdb.getError())
                .withMessage(paymentdb.getMessage())
                .withOrder(orderRepository.findById(paymentdb.getOrderId())
                        .orElseThrow(() -> new CartNotFoundException("Order not found with ID: " + paymentdb.getOrderId())))
                .withExternalId(paymentdb.getExternalId())
                .withCreationDate(paymentdb.getCreationDate())
                .withExternalState(paymentdb.getExternalState())
                .build();

        return Optional.of(paymentResponse);
    }
}
