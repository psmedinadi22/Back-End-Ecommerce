package com.ecommerce.prototype.infrastructure.client.payu.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenizationRequestPayload {

    private String language;
    private String command;
    private Merchant merchant;
    private TokenizationRequest creditCardToken;
}


