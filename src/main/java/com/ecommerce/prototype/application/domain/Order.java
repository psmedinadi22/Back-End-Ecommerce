package com.ecommerce.prototype.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer orderID;
    private Date creationDate;
    private double totalAmount;
    private String orderStatus;
    private User user;
}
