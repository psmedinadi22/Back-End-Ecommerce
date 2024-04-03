package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.AddProductToCartUseCase;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperProduct;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.ProductJPARepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductProvider implements ProductRepository {

    private final ProductJPARepository productJPARepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductProvider.class);


    /**
     * Saves a product.
     *
     * @param product The product to be saved.
     * @return An optional containing the saved product if successful, otherwise empty.
     */
    @Override
    public Optional<Product> save(Product product) {

            Productdb productdb = new Productdb(
                    product.getProductId() != null ? product.getProductId() : null,
                    product.getProductName(),
                    product.getDescription(),
                    product.getImage(),
                    product.getPrice(),
                    product.getQuantity()
            );
            productdb = productJPARepository.save(productdb);

            return Optional.of(new Product(
                    productdb.getProductId(),
                    productdb.getProductName(),
                    productdb.getDescription(),
                    productdb.getImage(),
                    productdb.getPrice(),
                    productdb.getQuantity()
            ));
    }

    /**
     * Checks if a product with the specified ID exists.
     *
     * @param productId The ID of the product to check.
     * @return True if the product exists, false otherwise.
     */
    @Override
    public boolean existsById(Integer productId) {

        return productJPARepository.existsByProductIdAndDeletedFalse(productId);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    public void deleteById(Integer productId) {

        Productdb productdb = productJPARepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productdb.setDeleted(true);
        productJPARepository.save(productdb);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return An optional containing the product if found, otherwise empty.
     */
    @Override
    public Optional<Productdb> findById(Integer productId) {
        logger.info("ProductId {}:", productId);
        Productdb productdb = productJPARepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        logger.info("ProductId {}:", productdb.getProductId());
        return Optional.of(productdb);
    }

    /**
     * Retrieves a product by its name.
     *
     * @param productName The name of the product to retrieve.
     * @return An optional containing the product if found, otherwise empty.
     */
    @Override
    public Optional<Product> findByName(String productName) {

        Optional<Productdb> productdbOptional = productJPARepository.findByProductName(productName);

        if (productdbOptional.isPresent()) {
            Productdb productdb = productdbOptional.get();
            Product product = new Product();
            product.setProductId(productdb.getProductId());
            product.setProductName(productdb.getProductName());
            product.setDescription(productdb.getDescription());
            product.setImage(productdb.getImage());
            product.setPrice(productdb.getPrice());
            product.setQuantity(productdb.getQuantity());

            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }
}
