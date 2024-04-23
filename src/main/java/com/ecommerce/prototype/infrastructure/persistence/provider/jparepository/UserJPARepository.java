package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<Userdb, Integer> {

    boolean existsByEmail(Email email);
    Optional<Userdb> findByUserId(Integer userId);
    Userdb findByEmail(String email);
}

