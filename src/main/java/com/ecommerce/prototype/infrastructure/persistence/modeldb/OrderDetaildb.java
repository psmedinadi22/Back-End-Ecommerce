package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.UserBillingAddress;
import com.ecommerce.prototype.application.domain.UserShippingAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderDetail")
public class OrderDetaildb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne
    private Orderdb order;
    @OneToOne
    private Cartdb cart;
}
