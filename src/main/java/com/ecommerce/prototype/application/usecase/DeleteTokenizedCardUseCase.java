package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.usecase.exception.TokenizedCardNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.UnauthorizedAccessException;
import com.ecommerce.prototype.application.usecase.repository.TokenizedCardRepository;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteTokenizedCardUseCase {

    private final TokenizedCardRepository tokenizedCardRepository;

    @Transactional
    public void deleteTokenizedCard(Integer tokenizedCardId) {
        TokenizedCarddb tokenizedCarddb = tokenizedCardRepository.findById(tokenizedCardId)
                .orElseThrow(() -> new TokenizedCardNotFoundException("Tokenized card not found with ID: " + tokenizedCardId));

        tokenizedCardRepository.delete(tokenizedCarddb);
    }
}

