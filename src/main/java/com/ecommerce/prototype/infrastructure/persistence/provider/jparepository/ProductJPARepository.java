package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductJPARepository extends JpaRepository<Productdb, Integer> {

    Optional<Productdb> findByProductId(Integer productId);
    boolean existsByProductIdAndDeletedFalse(Integer productId);
    @Query("SELECT p FROM Productdb p WHERE p.productName = :productName")
    Optional<Productdb> findByProductName(@Param("productName") String productName);

}
