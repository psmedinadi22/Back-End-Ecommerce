package com.ecommerce.prototype.infrastructure.client.payu.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private Date creationDate;
    private Double totalAmount;
    private String orderStatus;
    private Integer userId;

}

