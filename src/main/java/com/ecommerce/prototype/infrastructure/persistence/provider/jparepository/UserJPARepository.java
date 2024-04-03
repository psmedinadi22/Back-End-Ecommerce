package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository<Userdb, Integer> {

    boolean existsByEmail(String email);
    Userdb findByUserId(Integer userId);
}

