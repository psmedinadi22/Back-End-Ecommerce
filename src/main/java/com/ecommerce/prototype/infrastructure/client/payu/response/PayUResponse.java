package com.ecommerce.prototype.infrastructure.client.payu.response;

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
public class PayUResponse {

    private String code;
   private String error;
    private TransactionResponse transactionResponse;
    private Integer orderId;
    private Integer orderDetailId;
    private Integer paymentId;

}

