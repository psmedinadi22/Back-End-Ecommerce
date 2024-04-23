package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);
    boolean existByEmail(Email email);
    void deleteById(Integer userId);
    Buyer findBuyerById(Integer userId);
    User findUserById(Integer userId);

    Optional<User> findByEmail(String email);
}
