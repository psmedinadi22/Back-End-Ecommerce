package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.application.usecase.exception.PaymentNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperPayment;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.PaymentJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PaymentProvider implements PaymentRepository {

    private final PaymentJPARepository paymentJPARepository;

    @Override
    public Payment save(Payment payment) {

        var paymentdb = MapperPayment.mapToModel(payment);
        return paymentJPARepository.save(paymentdb).toPayment();
    }

    @Override
    public Optional<Payment> getPayment(Integer paymentId) {

        Paymentdb paymentdb = paymentJPARepository.findByPaymentID(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: "+ paymentId));
        return Optional.of(MapperPayment.mapToDomain(paymentdb));
    }
}
