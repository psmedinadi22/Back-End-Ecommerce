package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.usecase.exception.TokenizationErrorException;
import com.ecommerce.prototype.application.usecase.repository.TokenizedCardRepository;
import com.ecommerce.prototype.infrastructure.client.TokenizationService;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperTokenizedCard;
import com.ecommerce.prototype.infrastructure.client.request.TokenizationRequest;
import com.ecommerce.prototype.infrastructure.client.response.TokenizationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CreateTokenizedCardUseCase {

    @Autowired
    private  TokenizedCardRepository tokenizedCardRepository;
    @Autowired
    private TokenizationService tokenizationService;

    /**
     * Creates a tokenized card for a user.
     *
     * @param card   The card information.
     * @param userId The ID of the user.
     * @return An optional containing the tokenized card response if successful, empty otherwise.
     * @throws IllegalAccessException   If the tokenization service fails due to access issues.
     * @throws JsonProcessingException   If there's an error processing JSON data.
     */
    public Optional<TokenizationResponse> createTokenizedCard(Card card, Integer userId) throws IllegalAccessException, JsonProcessingException {

        TokenizationRequest tokenizedCardRequest = MapperTokenizedCard.toTokenizedCardRequest(card, userId);

        TokenizationResponse tokenizedCard = tokenizationService.tokenizeCard(tokenizedCardRequest);

        if ("SUCCESS".equals(tokenizedCard.getCode())) {
            return tokenizedCardRepository.createTokenizedCard(tokenizedCard, userId);
        } else {
            String error = tokenizedCard.getError();
            throw new TokenizationErrorException("Tokenization error: " + error);
        }
    }
}
