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

    public Card toCard(){
        return Card.builder()
                .withPayerId(Integer.valueOf(this.payerId))
                .withName(this.name)
                .withIdentificationNumber(this.identificationNumber)
                .withPaymentMethod(this.paymentMethod)
                .withNumber(this.maskedNumber)
                .withExpirationDate(this.expirationDate)
                .withTokenId(this.creditCardTokenId)
                .build();
    }
}
