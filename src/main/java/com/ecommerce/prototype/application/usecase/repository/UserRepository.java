package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);
    boolean existByEmail(String email);
    boolean existsById(Integer userId);
    void deleteById(Integer userId);
    Userdb findById(Integer userId);

    Optional<User> findByEmail(String email);
}
