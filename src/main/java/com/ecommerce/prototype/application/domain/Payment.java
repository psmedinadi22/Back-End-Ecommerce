package com.ecommerce.prototype.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class Payment {

    private Integer paymentID;
    private String transactionID;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private Integer tokenId;
    private Integer buyerId;
    private Integer cartId;
    private String currency;
}
