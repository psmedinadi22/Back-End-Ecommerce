package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Paymentdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;
    private String transactionID;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    @OneToOne
    private  Orderdb order;
}
