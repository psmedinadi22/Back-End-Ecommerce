package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orderdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;
    private Date creationDate;
    private double totalAmount;
    private String orderStatus;
    @ManyToOne
    private Userdb user;
    @OneToOne(mappedBy = "order")
    private OrderDetaildb orderDetail;
    @OneToOne
    private Paymentdb paymentdb;

}