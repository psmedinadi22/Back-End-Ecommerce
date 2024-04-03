package com.ecommerce.prototype.application.domain;


import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer cartId;
    private List<Product> products;
    private List<Integer> productsQuantity;
    private String status;
    private User user;

    public Cart(Integer cartId, String status, User user) {
        this.cartId = cartId;
        this.status = status;
        this.user = user;
    }
}
