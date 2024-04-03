package com.ecommerce.prototype.application.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class UserBillingAddress {

    private String billingStreet;
    private String billingCity;
    private String billingState;
    private String billingCountry;
    private String billingPostalCode;
    private String billingPhone;

    @Override
    public String toString() {
        return "UserBillingAddress{" +
                "billingStreet='" + billingStreet + '\'' +
                ", billingCity='" + billingCity + '\'' +
                ", billingState='" + billingState + '\'' +
                ", billingCountry='" + billingCountry + '\'' +
                ", billingPostalCode='" + billingPostalCode + '\'' +
                ", billingPhone='" + billingPhone + '\'' +
                '}';
    }
}
