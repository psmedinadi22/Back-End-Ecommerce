package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> getPayment(Integer paymentId);
}
