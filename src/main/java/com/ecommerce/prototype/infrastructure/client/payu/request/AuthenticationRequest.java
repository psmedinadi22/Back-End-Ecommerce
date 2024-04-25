package com.ecommerce.prototype.infrastructure.client.payu.request;

import com.ecommerce.prototype.application.domain.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private Email email;
    private String password;
}
