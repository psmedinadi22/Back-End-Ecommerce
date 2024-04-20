package com.ecommerce.prototype.endpoint.rest;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.usecase.CreateTokenizedCardUseCase;
import com.ecommerce.prototype.application.usecase.DeleteTokenizedCardUseCase;
import com.ecommerce.prototype.application.usecase.exception.TokenizationErrorException;
import com.ecommerce.prototype.application.usecase.exception.TokenizedCardNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.UnauthorizedAccessException;
import com.ecommerce.prototype.application.usecase.exception.UserDisabledException;
import com.ecommerce.prototype.infrastructure.client.payu.response.TokenizationErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokenizationController {

    private final CreateTokenizedCardUseCase createTokenizedCardUseCase;
    private final DeleteTokenizedCardUseCase deleteTokenizedCard;

    @Autowired
    public TokenizationController(CreateTokenizedCardUseCase createTokenizedCardUseCase, DeleteTokenizedCardUseCase deleteTokenizedCard) {
        this.createTokenizedCardUseCase = createTokenizedCardUseCase;
        this.deleteTokenizedCard = deleteTokenizedCard;
    }

    @PostMapping("/tokenize")
    public ResponseEntity<?> tokenizeCard(@RequestBody Card card) throws IllegalAccessException, JsonProcessingException {
        try {
            return createTokenizedCardUseCase.createTokenizedCard(card, card.getPayerId())
                    .map(tokenizationResponse -> new ResponseEntity<>(tokenizationResponse, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (UserDisabledException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (TokenizationErrorException error){
            TokenizationErrorResponse response = new TokenizationErrorResponse();
            response.setCode("ERROR");
            response.setError(error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @DeleteMapping("/tokenize/{tokenizedCardId}")
    public ResponseEntity<String> deleteTokenizedCard(@PathVariable Integer tokenizedCardId) {
        try {
            deleteTokenizedCard.deleteTokenizedCard(tokenizedCardId);
            return ResponseEntity.ok("Tokenized card deleted successfully");
        } catch (TokenizedCardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to tokenized card");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
