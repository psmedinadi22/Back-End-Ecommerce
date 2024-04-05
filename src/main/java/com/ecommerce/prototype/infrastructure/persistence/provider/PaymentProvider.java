package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Payment;
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
    public Paymentdb save(Payment payment) {

        return paymentJPARepository.save(MapperPayment.mapToModel(payment));
    }

    @Override
    public Optional<Payment> getPayment(Integer paymentId) {
        Paymentdb paymentdb = paymentJPARepository.findByPaymentID(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: "+ paymentId));
        return Optional.of(MapperPayment.mapToDomain(paymentdb));
    }
}
