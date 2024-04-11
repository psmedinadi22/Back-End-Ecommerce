package com.ecommerce.prototype.infrastructure.client.request;

import com.ecommerce.prototype.application.domain.UserBillingAddress;
import com.ecommerce.prototype.application.domain.UserShippingAddress;
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
    private UserShippingAddress shippingAddress;
    private UserBillingAddress billingAddress;

}


