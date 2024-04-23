package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.Payment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payments")
@Builder(setterPrefix = "with")
public class Paymentdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;
    private String transactionID;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private Integer tokenId;
    private Integer cartId;
    @ManyToOne
    private Userdb user;

    public Payment toPayment(){

        return Payment.builder()
                .withPaymentID(this.paymentID)
                .withTransactionID(this.transactionID)
                .withPaymentDate(this.paymentDate)
                .withAmount(this.amount)
                .withPaymentMethod(this.paymentMethod)
                .withPaymentStatus(this.paymentStatus)
                .withCartId(this.cartId)
                .build();
    }
}
