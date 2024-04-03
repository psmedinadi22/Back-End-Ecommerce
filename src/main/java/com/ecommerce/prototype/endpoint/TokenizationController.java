package com.ecommerce.prototype.endpoint;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.usecase.CreateTokenizedCardUseCase;
import com.ecommerce.prototype.application.usecase.exception.TokenizationErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenizationController {

    private final CreateTokenizedCardUseCase createTokenizedCardUseCase;

    @Autowired
    public TokenizationController(CreateTokenizedCardUseCase createTokenizedCardUseCase) {
        this.createTokenizedCardUseCase = createTokenizedCardUseCase;
    }

    @PostMapping("/tokenize")
    public ResponseEntity<?> tokenizeCard(@RequestBody Card card) throws IllegalAccessException, JsonProcessingException {

        try {
            return createTokenizedCardUseCase.createTokenizedCard(card, card.getPayerId())
                    .map(tokenizationResponse -> new ResponseEntity<>(tokenizationResponse, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }catch (TokenizationErrorException error){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }
}
