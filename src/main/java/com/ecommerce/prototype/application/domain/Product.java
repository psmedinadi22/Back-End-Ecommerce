package com.ecommerce.prototype.application.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Integer productId;
    private String productName;
    private String description;
    private String image;
    private double price;
    private int quantity;
    private boolean deleted = false;

//TODO: APLICAR DRY
    /**
     * Validates the data of a product.
     *
     * @param product The product to validate.
     * @throws IllegalArgumentException if the product is null, or if any of its attributes violate the validation rules.
     */
    public static void validateProductData(Product product) {
        if (product == null || product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new IllegalArgumentException("The product name cannot be null or empty");
        }
        if (product.getProductName().length() > 255) {
            throw new IllegalArgumentException("The product name cannot exceed 255 characters");
        }
        if (product.getDescription() != null && product.getDescription().length() > 1000) {
            throw new IllegalArgumentException("The product description cannot exceed 1000 characters");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("The product price must be greater than zero");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("The product quantity cannot be negative");
        }
    }
}
