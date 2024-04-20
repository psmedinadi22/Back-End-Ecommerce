package com.ecommerce.prototype.infrastructure.client.payu.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenizationErrorResponse {

    private String code;
    private String error;
}

