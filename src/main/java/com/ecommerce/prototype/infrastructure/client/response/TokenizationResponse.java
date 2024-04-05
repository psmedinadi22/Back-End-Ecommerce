package com.ecommerce.prototype.infrastructure.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenizationResponse {

    private Integer tokenizationResponseId;
    @JsonProperty("code")
    private String code;
    @JsonProperty("creditCardToken")
    private CreditCardToken creditCardToken;
    @JsonProperty("error")
    private String error;

}





