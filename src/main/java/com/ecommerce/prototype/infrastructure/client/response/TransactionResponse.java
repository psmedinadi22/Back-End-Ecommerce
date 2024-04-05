package com.ecommerce.prototype.infrastructure.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse {




    private long orderId;
    private String transactionId;
    private String state;
    private String paymentNetworkResponseCode;
    private String paymentNetworkResponseErrorMessage;
    private String responseCode;
    private String operationDate;
    private String errorCode;
    private String responseMessage;
}
