package com.ecommerce.prototype.infrastructure.client.response;


import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.Password;
import com.ecommerce.prototype.application.domain.UserBillingAddress;
import com.ecommerce.prototype.application.domain.UserShippingAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private UserShippingAddress shippingAddress;
    private UserBillingAddress billingAddress;
    private Boolean admin;
    private String deleted;
    private String error;

}
