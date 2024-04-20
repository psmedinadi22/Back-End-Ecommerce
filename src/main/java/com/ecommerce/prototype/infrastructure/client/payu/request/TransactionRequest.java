package com.ecommerce.prototype.infrastructure.client.payu.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionRequest {

    private OrderRequest order;
    private PayerRequest payer;
    private String creditCardTokenId;
    private String type;
    private String paymentMethod;
    private String paymentCountry;
    private String deviceSessionId;
    private String ipAddress;
    private String cookie;
    private String userAgent;
}
