package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.CardRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperTokenizedCard;
import com.ecommerce.prototype.infrastructure.client.payu.response.TokenizationResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.TokenizedCardJPARepository;
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
    private final UserProvider userProvider;


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
        tokenizedCarddb.setUser(userProvider.findById(userId));
        return Optional.ofNullable(tokenizedCardJPARepository.save(tokenizedCarddb));

    }

    @Override
    public List<TokenizedCard> findByUserId(Integer userId) {

        Userdb userdb = userProvider.findById(userId);
        if (userdb != null) {

            List<TokenizedCarddb> tokenizedCarddbList = tokenizedCardJPARepository.findByUserUserId(userId);
            return tokenizedCarddbList.stream()
                    .map(MapperTokenizedCard::mapToDomain)
                    .collect(Collectors.toList());
        } else {
            throw new UserNoExistException("User not found with ID: " + userId);
        }
    }

    @Override
    public void delete(TokenizedCarddb tokenizedCarddb) {
        tokenizedCardJPARepository.delete(tokenizedCarddb);
    }
}
