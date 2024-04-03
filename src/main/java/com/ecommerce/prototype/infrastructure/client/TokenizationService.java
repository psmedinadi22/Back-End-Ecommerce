package com.ecommerce.prototype.infrastructure.client;

import com.ecommerce.prototype.infrastructure.client.request.Merchant;
import com.ecommerce.prototype.infrastructure.client.request.TokenizationRequest;
import com.ecommerce.prototype.infrastructure.client.request.TokenizationRequestPayload;
import com.ecommerce.prototype.infrastructure.client.response.TokenizationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenizationService {

    @Value("${payu.api.url}")
    private String apiUrl;

    @Value("${payu.api.login}")
    private String apiLogin;

    @Value("${payu.api.key}")
    private String apiKey;
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Tokenizes the provided card using the given tokenization request.
     *
     * @param tokenizationRequest The tokenization request containing card information.
     * @return TokenizationResponse The response from the tokenization process.
     * @throws JsonProcessingException If there is an error during JSON processing.
     */
    public TokenizationResponse tokenizeCard(TokenizationRequest tokenizationRequest) throws JsonProcessingException {
        HttpHeaders headers = buildHttpHeaders();
        TokenizationRequestPayload requestPayload = buildTokenizationRequestPayload(tokenizationRequest);
        HttpEntity<TokenizationRequestPayload> requestEntity = buildHttpEntity(requestPayload, headers);
        ResponseEntity<String> responseEntity = sendTokenizationRequest(requestEntity);
        return processTokenizationResponse(responseEntity);
    }

    /**
     * Builds the HTTP headers required for the tokenization request.
     *
     * @return HttpHeaders The HTTP headers.
     */
    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Builds the payload for the tokenization request.
     *
     * @param tokenizationRequest The tokenization request containing card information.
     * @return TokenizationRequestPayload The payload for the tokenization request.
     */
    private TokenizationRequestPayload buildTokenizationRequestPayload(TokenizationRequest tokenizationRequest) {
        return TokenizationRequestPayload.builder()
                .language("es")
                .command("CREATE_TOKEN")
                .merchant(buildMerchant())
                .creditCardToken(tokenizationRequest)
                .build();
    }

    /**
     * Builds the merchant information for the tokenization request.
     *
     * @return Merchant The merchant information.
     */
    private Merchant buildMerchant() {
        return Merchant.builder()
                .apiLogin(apiLogin)
                .apiKey(apiKey)
                .build();
    }

    /**
     * Builds the HTTP entity containing the tokenization request payload and headers.
     *
     * @param requestPayload The tokenization request payload.
     * @param headers The HTTP headers.
     * @return HttpEntity<TokenizationRequestPayload> The HTTP entity.
     */
    private HttpEntity<TokenizationRequestPayload> buildHttpEntity(TokenizationRequestPayload requestPayload, HttpHeaders headers) {
        return new HttpEntity<>(requestPayload, headers);
    }

    /**
     * Sends the tokenization request to the API endpoint.
     *
     * @param requestEntity The HTTP request entity containing the tokenization request payload.
     * @return ResponseEntity<String> The response entity from the API.
     * @throws RuntimeException If there is an error during the tokenization process.
     */
    private ResponseEntity<String> sendTokenizationRequest(HttpEntity<TokenizationRequestPayload> requestEntity) {
        return new RestTemplate().exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    /**
     * Processes the tokenization response received from the API.
     *
     * @param responseEntity The response entity from the tokenization request.
     * @return TokenizationResponse The response from the tokenization process.
     * @throws RuntimeException If there is an error tokenizing the card.
     * @throws JsonProcessingException If there is an error during JSON processing.
     */
    private TokenizationResponse processTokenizationResponse(ResponseEntity<String> responseEntity) throws JsonProcessingException {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            return objectMapper.readValue(responseBody, TokenizationResponse.class);
        } else {
            throw new RuntimeException("Error tokenizing the card");
        }
    }
}

