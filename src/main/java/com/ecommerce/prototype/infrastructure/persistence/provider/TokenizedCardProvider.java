package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.TokenizedCardRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperTokenizedCard;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.client.response.CreditCardToken;
import com.ecommerce.prototype.infrastructure.client.response.TokenizationResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.TokenizedCardJPARepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Component
public class TokenizedCardProvider implements TokenizedCardRepository {

    private final TokenizedCardJPARepository tokenizedCardJPARepository;
    private final UserProvider userProvider;

    /**
     * Saves a tokenization response.
     *
     * @param tokenizationResponse The tokenization response to be saved.
     * @return An optional containing the saved tokenization response if successful and the credit card token is present, otherwise empty.
     */
    @Override
    public Optional<TokenizationResponse> save(Optional<TokenizationResponse> tokenizationResponse) {
        if (tokenizationResponse.isPresent() && tokenizationResponse.get().getCreditCardToken() != null) {
            CreditCardToken creditCardToken = tokenizationResponse.get().getCreditCardToken();
            TokenizedCarddb tokenizedCarddb = new TokenizedCarddb(
                    tokenizationResponse.get().getCode(),
                    creditCardToken.getCreditCardTokenId(),
                    creditCardToken.getName(),
                    creditCardToken.getPayerId(),
                    creditCardToken.getIdentificationNumber(),
                    creditCardToken.getPaymentMethod(),
                    creditCardToken.getMaskedNumber(),
                    creditCardToken.getExpirationDate()
            );
            tokenizedCarddb = tokenizedCardJPARepository.save(tokenizedCarddb);
            return Optional.of(TokenizationResponse.builder()
                    .code("SUCCESS")
                    .creditCardToken(CreditCardToken.builder()
                            .creditCardTokenId(tokenizedCarddb.getCreditCardTokenId())
                            .name(tokenizedCarddb.getName())
                            .payerId(String.valueOf(tokenizedCarddb.getPayerId()))
                            .identificationNumber(String.valueOf(tokenizedCarddb.getIdentificationNumber()))
                            .paymentMethod(tokenizedCarddb.getPaymentMethod())
                            .maskedNumber(tokenizedCarddb.getMaskedNumber())
                            .build())
                    .build());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retrieves a tokenized card by its ID.
     *
     * @param tokenizedCardId The ID of the tokenized card to retrieve.
     * @return An optional containing the tokenized card if found, otherwise empty.
     */
    @Override
    public Optional<TokenizedCarddb> findById(Integer tokenizedCardId) {
        return tokenizedCardJPARepository.findById(tokenizedCardId);
    }

    @Override
    public Optional<TokenizationResponse> createTokenizedCard(TokenizationResponse tokenizedCard, Integer userId) {
        TokenizedCarddb tokenizedCarddb = MapperTokenizedCard.toTokenizedCardModel(tokenizedCard);
        tokenizedCarddb.setUser(userProvider.findById(userId));
        tokenizedCardJPARepository.save(tokenizedCarddb);
        return Optional.of(tokenizedCard);

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

}
