package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);
    boolean existByEmail(String email);
    boolean existsById(Integer userId);
    void deleteById(Integer userId);
    Buyer findBuyerById(Integer userId);
    User findUserById(Integer userId);

    Optional<User> findByEmail(String email);
}
