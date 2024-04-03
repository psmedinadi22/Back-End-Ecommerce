package com.ecommerce.prototype.infrastructure.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {

    private int cartId;
    private int productId;
    private int quantity;
}
