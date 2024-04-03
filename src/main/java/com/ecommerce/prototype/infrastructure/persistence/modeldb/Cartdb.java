package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cartdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    private String status;
    @ElementCollection
    private List<Integer> productsQuantity;
    @ManyToMany
    private List<Productdb> products;
    @ManyToOne
    private Userdb user;
}
