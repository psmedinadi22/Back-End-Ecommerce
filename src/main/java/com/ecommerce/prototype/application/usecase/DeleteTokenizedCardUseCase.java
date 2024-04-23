package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.usecase.exception.TokenizedCardNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.CardRepository;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteTokenizedCardUseCase {

    private final CardRepository cardRepository;

    @Transactional
    public void deleteTokenizedCard(Integer tokenizedCardId) {
        Card card = cardRepository.findById(tokenizedCardId)
                                                        .orElseThrow(() -> new TokenizedCardNotFoundException("Tokenized card not found with ID: " + tokenizedCardId));
        cardRepository.delete(card);
    }
}

