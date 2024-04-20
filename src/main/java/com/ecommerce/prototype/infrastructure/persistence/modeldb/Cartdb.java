package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.Cart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Integer> productQuantities;
    @ManyToMany
    private List<Productdb> products;
    @ManyToOne
    private Userdb user;


    public Cart toCart() {

        return Cart.builder()
                   .withBuyer(this.user.toBuyer())
                   .withCartId(this.cartId)
                   .withStatus(this.status)
                   .withProductQuantities(this.productQuantities)
                   .withProducts(this.products.stream().map(Productdb::toProduct).collect(Collectors.toList()))
                   .build();
    }
}
