package com.ecommerce.prototype.application.usecase.repository;

import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.infrastructure.client.response.TokenizationResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;

import java.util.List;
import java.util.Optional;

public interface TokenizedCardRepository {


    Optional<TokenizedCarddb> findById(Integer tokenizedCardId);


    Optional<TokenizedCarddb> createTokenizedCard(TokenizationResponse tokenizedCard, Integer userId);

    List<TokenizedCard> findByUserId(Integer userId);

    void delete(TokenizedCarddb tokenizedCarddb);
}
