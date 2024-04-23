package com.ecommerce.prototype.infrastructure.client.payu.response;


import com.ecommerce.prototype.application.domain.Address;
import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.Password;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {

    private Integer userId;
    private String name;
    private Email email;
    private Password password;
    private String phoneNumber;
    private String identificationType;
    private String identificationNumber;
    private Address shippingAddress;
    private Address billingAddress;
    private Boolean admin;
    private String deleted;
    private String error;

}
