package com.ecommerce.prototype.infrastructure.client.payu.request;

import com.ecommerce.prototype.application.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String identificationType;
    private String identificationNumber;
    private Address shippingAddress;
    private Address billingAddress;
}


