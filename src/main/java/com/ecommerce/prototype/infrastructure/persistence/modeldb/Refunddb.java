package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table()
public class Refunddb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refundID;
    private double refundAmount;
    private String refundReason;
    private String refundStatus;
    @OneToOne
    private Paymentdb payment;
}
