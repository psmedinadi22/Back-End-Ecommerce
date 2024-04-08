package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.infrastructure.client.request.ProductRequest;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;

public class MapperProduct {

    /**
     * Maps a ProductRequest object to a Product domain object.
     *
     * @param productRequest The ProductRequest object to be mapped.
     * @return Product The mapped Product domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Product toProductDomain(ProductRequest productRequest) throws IllegalArgumentException {

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .image(productRequest.getImage())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        Product.validateProductData(product);

        return product;
    }

    /**
     * Maps a Productdb entity to a Product domain object.
     *
     * @param productdb The Productdb entity to be mapped.
     * @return Product The mapped Product domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Product toProductDomain(Productdb productdb) throws IllegalArgumentException {
        Product product = Product.builder()
                .productId(productdb.getProductId())
                .productName(productdb.getProductName())
                .description(productdb.getDescription())
                .image(productdb.getImage())
                .price(productdb.getPrice())
                .quantity(productdb.getQuantity())
                .deleted(productdb.isDeleted())
                .build();
        Product.validateProductData(product);

        return product;
    }

    /**
     * Maps a Product domain object to a Productdb entity.
     *
     * @param product The Product domain object to be mapped.
     * @return Productdb The mapped Productdb entity.
     */
    public static Productdb toProductModel(Product product) throws IllegalArgumentException {
        return Productdb.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

}
