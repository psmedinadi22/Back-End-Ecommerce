package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Productdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String description;
    private String image;
    private double price;
    private int quantity;
    private boolean deleted = false;

    public Productdb(Integer productId, String productName, String description, String image, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }
}
