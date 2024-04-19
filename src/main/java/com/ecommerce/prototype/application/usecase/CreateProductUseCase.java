package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.InsufficientProductQuantityException;
import com.ecommerce.prototype.application.usecase.exception.ProductAlreayExistException;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@AllArgsConstructor
@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    /**
     * Creates a new product if it does not already exist.
     *
     * @param product The product to be created.
     * @return An Optional containing the created product if successful, empty otherwise.
     */
    public Product createProduct(Product product) {

        if(product.getQuantity() <= 0){
            throw new InsufficientProductQuantityException("Product quantity is not valid");
        }
        if (productRepository.findByName(product.getProductName()).isPresent()) {
            throw new ProductAlreayExistException("Product already exists: " + product.getProductName());
        }

        Product productdb = productRepository.save(product)
                .orElseThrow(() -> new IllegalArgumentException("Product saving error"));
        product.setProductId(productdb.getProductId());
        return product;
    }
}

