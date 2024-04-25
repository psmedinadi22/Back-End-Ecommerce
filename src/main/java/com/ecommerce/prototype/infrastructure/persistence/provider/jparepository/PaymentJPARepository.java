package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJPARepository extends JpaRepository<Paymentdb, Integer> {
    Optional<Paymentdb> findByPaymentID (Integer paymentId);


}
