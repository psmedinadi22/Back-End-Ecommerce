package com.ecommerce.prototype.infrastructure.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardToken {

    private String creditCardTokenId;
    private String name;
    private String payerId;
    private String identificationNumber;
    private String paymentMethod;
    private String number;
    private String expirationDate;
    private String creationDate;
    private String maskedNumber;
    private String errorDescription;

}
