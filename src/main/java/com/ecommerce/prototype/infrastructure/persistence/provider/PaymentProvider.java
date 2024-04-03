package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperPayment;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.PaymentJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentProvider implements PaymentRepository {

    private final PaymentJPARepository paymentJPARepository;

    @Override
    public Paymentdb save(Payment payment) {

        return paymentJPARepository.save(MapperPayment.mapToModel(payment));
    }
}
