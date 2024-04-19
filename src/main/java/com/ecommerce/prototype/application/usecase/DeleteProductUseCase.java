package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.usecase.exception.ProductNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class DeleteProductUseCase {
    private ProductRepository productRepository;

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @throws ProductNotFoundException If the product with the given ID is not found.
     */

    public void deleteProduct(Integer productId) {

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }
}
