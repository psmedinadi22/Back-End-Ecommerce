package com.ecommerce.prototype.application.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Card {

    private Integer payerId;
    private String name;
    private String identificationNumber;
    private String paymentMethod;
    private String number;
    private String expirationDate;
    private String tokenId;
}
