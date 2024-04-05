package com.ecommerce.prototype.application.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cart {

    private Integer cartId;
    private List<Product> products;
    private List<Integer> productsQuantity;
    private String status;
    private User user;

    public Cart(Integer cartId, String status) {
        this.cartId = cartId;
        this.status = status;

    }
}
