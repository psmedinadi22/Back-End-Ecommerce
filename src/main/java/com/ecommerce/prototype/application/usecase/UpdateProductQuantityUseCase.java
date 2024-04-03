package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.ProductNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperProduct;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@AllArgsConstructor
@Component
public class UpdateProductQuantityUseCase {

    private ProductRepository productRepository;

    /**
     * Updates the quantity of a product.
     *
     * @param productId   The ID of the product to be updated.
     * @param newQuantity The new quantity to be assigned to the product.
     * @return An Optional containing the updated product if found, otherwise empty.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public Product updateProductQuantity(Integer productId, int newQuantity) {

        Productdb productdb = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        productdb.setQuantity(newQuantity);
        Product product = MapperProduct.toProductDomain(productdb);
        productRepository.save(product);
        return product;
    }
}
