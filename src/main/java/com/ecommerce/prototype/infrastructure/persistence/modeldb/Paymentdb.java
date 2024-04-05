package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    @JsonIgnore
//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Orderdb order;


}
