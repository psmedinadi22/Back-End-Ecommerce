package com.ecommerce.prototype.infrastructure.client.payu.request;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private Integer tokenizedCardId;
    private Integer cartId;
    private Integer userId;
    private String paymentMethod;
    private String currency;
}
