package com.ecommerce.prototype.infrastructure.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {

    private String productName;
    private String description;
    private String image;
    private double price;
    private int quantity;
}
