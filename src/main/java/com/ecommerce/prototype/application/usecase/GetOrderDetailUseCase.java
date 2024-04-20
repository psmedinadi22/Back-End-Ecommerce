package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.usecase.repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetOrderDetailUseCase {

    private OrderDetailRepository orderDetailRepository;
    public Optional<OrderDetail> getOrderDetailById(Integer orderDetailId) {
        return orderDetailRepository.findById(orderDetailId);
    }
}
