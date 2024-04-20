package com.ecommerce.prototype.application.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder(setterPrefix = "with")
@NoArgsConstructor
public class TokenizedCard {

    private String creditCardTokenId;
    private String name;
    private String payerId;
    private String identificationNumber;
    private String paymentMethod;
    private String number;
    private String expirationDate;
    private Date creationDate;
    private String maskedNumber;
    private String errorDescription;
    private Buyer buyer;
}
