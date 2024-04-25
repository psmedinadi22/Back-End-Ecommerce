package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.PaymentResponse;
import java.util.Optional;

public interface PaymentRepository {

    PaymentResponse save(PaymentResponse paymentResponse);

    Optional<PaymentResponse> getPayment(Integer paymentId);
}
