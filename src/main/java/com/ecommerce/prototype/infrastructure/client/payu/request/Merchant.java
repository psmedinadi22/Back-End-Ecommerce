package com.ecommerce.prototype.infrastructure.client.payu.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Merchant {

    private String apiLogin;
    private String apiKey;
}

