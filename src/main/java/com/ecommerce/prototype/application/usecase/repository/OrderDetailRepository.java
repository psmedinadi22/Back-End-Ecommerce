package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.OrderDetaildb;

import java.util.Optional;

public interface OrderDetailRepository {
    
    OrderDetail save(OrderDetail orderDetail);
    Optional<OrderDetail> findById(Integer orderDetailId);

    OrderDetaildb createOrderDetail(OrderDetail orderDetail);
}
