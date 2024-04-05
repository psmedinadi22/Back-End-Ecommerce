package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.OrderDetaildb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailJPARepository extends JpaRepository<OrderDetaildb, Integer> {

    Optional<OrderDetaildb> findByOrderDetailId(Integer orderDetailId);
}
