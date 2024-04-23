package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.TokenizationErrorException;
import com.ecommerce.prototype.application.usecase.exception.UserDisabledException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CardRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.PayuConfig;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperTokenizedCard;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.client.payu.request.TokenizationRequest;
import com.ecommerce.prototype.infrastructure.client.payu.response.TokenizationResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CreateTokenizedCardUseCase {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private PayuConfig payuConfig;
    private UserRepository userRepository;

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

        User user = retrieveUser(userId);
        if (user.getIsDeleted()) {
            throw new UserDisabledException("The user with ID: " + userId + " is disabled.");
        }

        TokenizationRequest tokenizedCardRequest = MapperTokenizedCard.toTokenizedCardRequest(card, userId);

        TokenizationResponse tokenizedCard = payuConfig.tokenizeCard(tokenizedCardRequest);

        if ("SUCCESS".equals(tokenizedCard.getCode())) {
            TokenizedCarddb tokenizedCarddb = cardRepository.createTokenizedCard(tokenizedCard, userId)
															.orElseThrow(() -> new TokenizationErrorException("Tokenization error"));

            tokenizedCard.setTokenizationResponseId(tokenizedCarddb.getId());
            return Optional.of(tokenizedCard);
        } else {
            String error = tokenizedCard.getError();
            throw new TokenizationErrorException("Tokenization error: " + error);
        }
    }

    private User retrieveUser(Integer userId) {

        return userRepository.findUserById(userId);
    }
}
