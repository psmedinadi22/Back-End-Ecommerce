package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.OrderDetaildb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailJPARepository extends JpaRepository<OrderDetaildb, Integer> {
}
