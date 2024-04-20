package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order activeOrder);

    Optional<Order> findById(Integer orderId);

    Optional<Order> getOrder(int orderId);

    List<Orderdb> findByUserId(Integer userId);

    Order createOrder(Order order, Integer buyerId);
}
