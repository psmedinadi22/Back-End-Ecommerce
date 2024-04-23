package com.ecommerce.prototype.application.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserShippingAddress {

    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
}
