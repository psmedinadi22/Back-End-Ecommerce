package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.PaymentResponse;
import com.ecommerce.prototype.application.domain.RefundResponse;

import java.util.Optional;

public interface ExternalPlatformRepository {

	Optional<PaymentResponse> doPayment(Order order);
}
