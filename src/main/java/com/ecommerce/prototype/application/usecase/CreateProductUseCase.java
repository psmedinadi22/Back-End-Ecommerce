package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.ProductAlreayExistException;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        if (productRepository.findByName(product.getProductName()).isPresent()) {
            throw new ProductAlreayExistException("Product already exists: " + product.getProductName());
        }

        productRepository.save(product);

        return product;
    }

}

