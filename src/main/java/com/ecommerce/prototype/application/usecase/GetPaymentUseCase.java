package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.application.usecase.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPaymentUseCase {

    private PaymentRepository paymentRepository;

    public Optional<Payment> getPayment(Integer paymentId){

        return  paymentRepository.getPayment(paymentId);
    }
}
