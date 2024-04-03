package com.ecommerce.prototype.infrastructure.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Integer productId;
    private String productName;
    private String description;
    private String image;
    private double price;
    private int quantity;
    private String error;
}
