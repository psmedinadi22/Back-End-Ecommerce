package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.application.usecase.repository.TokenizedCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetUserTokenizedCartsUseCase {
    private final TokenizedCardRepository tokenizedCartRepository;

    /**
     * Retrieves tokenized carts for a given user.
     * @param userId The ID of the user.
     * @return A list of tokenized carts.
     */
    public List<TokenizedCard> getUserTokenizedCarts(Integer userId) {
        return tokenizedCartRepository.findByUserId(userId);
    }
}
