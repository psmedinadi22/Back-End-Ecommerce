package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
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

    public Product toProduct() {

        return Product.builder()
                      .withProductId(this.productId)
                      .withDeleted(this.deleted)
                      .withDescription(this.description)
                      .withImage(this.image)
                      .withPrice(this.price)
                      .withQuantity(this.quantity)
                      .withProductName(this.productName)
                      .build();
    }

}
