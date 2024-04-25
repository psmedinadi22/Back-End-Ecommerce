package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.PaymentResponse;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPaymentUseCase {

    private PaymentRepository paymentRepository;

    public Optional<PaymentResponse> getPayment(Integer paymentId){

        return  paymentRepository.getPayment(paymentId);
    }
}
