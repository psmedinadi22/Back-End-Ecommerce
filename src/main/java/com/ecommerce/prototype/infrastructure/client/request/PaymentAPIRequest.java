package com.ecommerce.prototype.infrastructure.client.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentAPIRequest {

    private String language;
    private String command;
    private Merchant merchant;
    private TransactionRequest transaction;
    private Boolean test;
}


