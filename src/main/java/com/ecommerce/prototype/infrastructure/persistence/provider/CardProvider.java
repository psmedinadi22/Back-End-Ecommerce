package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CardRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperTokenizedCard;
import com.ecommerce.prototype.infrastructure.client.payu.response.TokenizationResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.TokenizedCardJPARepository;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.UserJPARepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Component
public class CardProvider implements CardRepository {

    private final TokenizedCardJPARepository tokenizedCardJPARepository;
    private final UserJPARepository userJPARepository;



    /**
     * Retrieves a tokenized card by its ID.
     *
     * @param tokenizedCardId The ID of the tokenized card to retrieve.
     * @return An optional containing the tokenized card if found, otherwise empty.
     */
    @Override
    public Optional<Card> findById(Integer tokenizedCardId) {

        return tokenizedCardJPARepository.findById(tokenizedCardId)
                                         .map(TokenizedCarddb::toCard);
    }

    @Override
    public Optional<TokenizedCarddb> createTokenizedCard(TokenizationResponse tokenizedCard, Integer userId) {

        TokenizedCarddb tokenizedCarddb = MapperTokenizedCard.toTokenizedCardModel(tokenizedCard);
        tokenizedCarddb.setUser(userJPARepository.findByUserId(userId)
                .orElseThrow(() -> new UserNoExistException("User not found with ID: " + userId)));
        return Optional.ofNullable(tokenizedCardJPARepository.save(tokenizedCarddb));

    }

    @Override
    public List<TokenizedCard> findByUserId(Integer userId) {

        userJPARepository.findByUserId(userId)
                .orElseThrow(() -> new UserNoExistException("User not found with ID: " + userId));

        List<TokenizedCarddb> tokenizedCarddbList = tokenizedCardJPARepository.findByUserUserId(userId);
        return tokenizedCarddbList.stream()
                    .map(MapperTokenizedCard::mapToDomain)
                    .collect(Collectors.toList());
    }

    @Override
    public void delete(Card card) {

        TokenizedCarddb tokenizedCarddb = tokenizedCardJPARepository.findByCreditCardTokenId(card.getTokenId());
        tokenizedCardJPARepository.deleteById(tokenizedCarddb.getId());
    }

    @Override
    public Card findByTokenId(String tokenId) {
        TokenizedCarddb tokenizedCarddb = tokenizedCardJPARepository.findByCreditCardTokenId(tokenId);
        return MapperTokenizedCard.mapToDomain(tokenizedCarddb).toCard();
    }
}
