package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.infrastructure.client.request.TokenizationRequest;
import com.ecommerce.prototype.infrastructure.client.response.CreditCardToken;
import com.ecommerce.prototype.infrastructure.client.response.TokenizationResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;

import java.util.List;

public class MapperTokenizedCard {

    /**
     * Maps a Card object and a user ID to a TokenizationRequest object.
     *
     * @param card   The Card object to be mapped.
     * @param userId The ID of the user.
     * @return TokenizationRequest The mapped TokenizationRequest object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static TokenizationRequest toTokenizedCardRequest(Card card, Integer userId) throws IllegalArgumentException {
        TokenizationRequest tokenizationRequest = new TokenizationRequest();
        tokenizationRequest.setPayerId(userId);
        tokenizationRequest.setName(card.getName());
        tokenizationRequest.setIdentificationNumber(card.getIdentificationNumber());
        tokenizationRequest.setPaymentMethod(card.getPaymentMethod());
        tokenizationRequest.setNumber(card.getNumber());
        tokenizationRequest.setExpirationDate(card.getExpirationDate());
        return tokenizationRequest;
    }

    public static TokenizedCarddb toTokenizedCardModel(TokenizationResponse tokenizationResponse)throws IllegalArgumentException {

        CreditCardToken creditCardToken = tokenizationResponse.getCreditCardToken();
        TokenizedCarddb tokenizedCarddb = new TokenizedCarddb();
        tokenizedCarddb.setCreditCardTokenId(creditCardToken.getCreditCardTokenId());
        tokenizedCarddb.setMaskedNumber(creditCardToken.getMaskedNumber());
        tokenizedCarddb.setExpirationDate(creditCardToken.getExpirationDate());
        tokenizedCarddb.setName(creditCardToken.getName());
        tokenizedCarddb.setPayerId(creditCardToken.getPayerId());
        tokenizedCarddb.setPaymentMethod(creditCardToken.getPaymentMethod());
        tokenizedCarddb.setCode(tokenizationResponse.getCode());
        tokenizedCarddb.setIdentificationNumber(creditCardToken.getIdentificationNumber());
        return tokenizedCarddb;
    }

    public static TokenizedCard mapToDomain(TokenizedCarddb tokenizedCarddb) {
        TokenizedCard tokenizedCard = new TokenizedCard();
        tokenizedCard.setCreditCardTokenId(tokenizedCarddb.getCreditCardTokenId());
        tokenizedCard.setName(tokenizedCarddb.getName());
        tokenizedCard.setPayerId(tokenizedCarddb.getPayerId());
        tokenizedCard.setIdentificationNumber(tokenizedCarddb.getIdentificationNumber());
        tokenizedCard.setPaymentMethod(tokenizedCarddb.getPaymentMethod());
        tokenizedCard.setNumber(tokenizedCarddb.getMaskedNumber());
        tokenizedCard.setExpirationDate(tokenizedCarddb.getExpirationDate());
        tokenizedCard.setCreationDate(tokenizedCarddb.getCreatedAt().toString());
        tokenizedCard.setMaskedNumber(tokenizedCarddb.getMaskedNumber());

        return tokenizedCard;
    }


}




