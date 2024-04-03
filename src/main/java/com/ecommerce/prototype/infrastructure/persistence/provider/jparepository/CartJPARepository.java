package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartJPARepository extends JpaRepository<Cartdb, Integer> {

    @Query("SELECT c FROM Cartdb c WHERE c.user.userId = :userId")
    List<Cartdb> findByUserId(Integer userId);
}
