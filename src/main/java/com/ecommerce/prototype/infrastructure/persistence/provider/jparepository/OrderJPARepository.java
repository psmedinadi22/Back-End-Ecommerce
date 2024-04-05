package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJPARepository extends JpaRepository<Orderdb, Integer> {

    @Query("SELECT c FROM Orderdb c WHERE c.user.userId = :userId")
    List<Orderdb> findByUserId(Integer userId);

    Optional<Orderdb> findByOrderID(Integer orderId);
}
