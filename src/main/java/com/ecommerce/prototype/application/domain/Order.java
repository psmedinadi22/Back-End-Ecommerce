package com.ecommerce.prototype.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Order {

    private Integer orderID;
    private Date creationDate;
    private double totalAmount;
    private String orderStatus;
    private Buyer buyer;
    private Address shippingAddress;
    private Address billingAddress;
    private Cart cart;
    private Card card;
}
