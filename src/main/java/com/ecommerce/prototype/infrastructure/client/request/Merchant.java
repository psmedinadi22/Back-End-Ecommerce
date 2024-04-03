package com.ecommerce.prototype.infrastructure.client.request;

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

