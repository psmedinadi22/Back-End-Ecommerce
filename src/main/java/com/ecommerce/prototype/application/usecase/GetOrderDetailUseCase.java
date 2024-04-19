package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.OrderDetail;
import com.ecommerce.prototype.application.usecase.repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetOrderDetailUseCase {

    private OrderDetailRepository orderDetailRepository;
    public Optional<OrderDetail> getOrderDetailById(Integer orderDetailId) {
        return orderDetailRepository.findById(orderDetailId);
    }
}
