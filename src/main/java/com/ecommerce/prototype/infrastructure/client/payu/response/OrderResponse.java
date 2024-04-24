package com.ecommerce.prototype.infrastructure.client.payu.response;

import com.ecommerce.prototype.application.domain.Address;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class OrderResponse {

    private Integer orderId;
    private Date creationDate;
    private Double totalAmount;
    private String orderStatus;
    private Integer userId;
    private Address shippingAddress;
    private Address billingAddress;
    private Integer cartId;
    private String tokenId;
}

