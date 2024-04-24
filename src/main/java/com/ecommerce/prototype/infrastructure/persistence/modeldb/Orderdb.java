package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder(setterPrefix = "with")
public class Orderdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;
    private Date creationDate;
    private double totalAmount;
    private String orderStatus;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postal_code")),
            @AttributeOverride(name = "phone", column = @Column(name = "shipping_phone"))
    })
    private Address shippingAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postal_code")),
            @AttributeOverride(name = "phone", column = @Column(name = "billing_phone"))
    })
    private Address billingAddress;
    private Integer cartId;
    private String tokenId;
    @ManyToOne
    private Userdb user;

}
