package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;

public interface PaymentRepository {

    Paymentdb save(Payment payment);
}
