package com.ecommerce.prototype.infrastructure.client.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TokenizationRequest {

        private Integer payerId;
        private String name;
        private String identificationNumber;
        private String paymentMethod;
        private String number;
        private String expirationDate;
}


