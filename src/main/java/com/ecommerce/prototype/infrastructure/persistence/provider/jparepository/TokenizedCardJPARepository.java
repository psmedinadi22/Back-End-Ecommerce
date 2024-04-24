package com.ecommerce.prototype.infrastructure.persistence.provider.jparepository;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenizedCardJPARepository extends JpaRepository<TokenizedCarddb, Integer> {

    TokenizedCarddb save(TokenizedCarddb tokenizedCarddb);
    List<TokenizedCarddb> findByUserUserId(Integer userId);
    TokenizedCarddb findByCreditCardTokenId(String creditCardTokenId);


}
