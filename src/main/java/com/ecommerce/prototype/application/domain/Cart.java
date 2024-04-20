package com.ecommerce.prototype.application.domain;

import lombok.*;
import java.util.List;
@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer cartId;
    private List<Product> products;
    private List<Integer> productQuantities;
    private String status;
    private Buyer buyer;

    public Cart(Integer cartId, String status) {
        this.cartId = cartId;
        this.status = status;

    }
}
