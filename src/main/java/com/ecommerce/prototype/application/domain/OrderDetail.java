package com.ecommerce.prototype.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private Integer orderDetailId;
    private Date purchaseDate;
    private double totalAmount;
    private String paymentMethod;
    private String purchaseStatus;
    private Integer buyerId;
    private String buyerFullName;
    private String buyerEmailAddress;
    private String buyerContactPhone;
    private String buyerDniNumber;
    private UserShippingAddress shippingAddress;
    private UserBillingAddress billingAddress;
    private Order order;
    private Cart cart;
}
