package com.ecommerce.prototype.infrastructure.client.response;

import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.application.domain.UserBillingAddress;
import com.ecommerce.prototype.application.domain.UserShippingAddress;
import lombok.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private Integer orderDetailId;
    private Date purchaseDate;
    private Double totalAmount;
    private String paymentMethod;
    private String purchaseStatus;
    private Integer buyerId;
    private String buyerFullName;
    private String buyerEmailAddress;
    private String buyerContactPhone;
    private String buyerDniNumber;
    private UserShippingAddress shippingAddress;
    private UserBillingAddress billingAddress;
}

