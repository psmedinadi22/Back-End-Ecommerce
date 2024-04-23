package com.ecommerce.prototype.endpoint.rest;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.CreateProductUseCase;
import com.ecommerce.prototype.application.usecase.DeleteProductUseCase;
import com.ecommerce.prototype.application.usecase.GetProductUseCase;
import com.ecommerce.prototype.application.usecase.UpdateProductQuantityUseCase;
import com.ecommerce.prototype.application.usecase.exception.InsufficientProductQuantityException;
import com.ecommerce.prototype.application.usecase.exception.ProductAlreayExistException;
import com.ecommerce.prototype.application.usecase.exception.ProductNotFoundException;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperProduct;
import com.ecommerce.prototype.infrastructure.client.payu.request.ProductRequest;
import com.ecommerce.prototype.infrastructure.client.payu.response.ProductResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final UpdateProductQuantityUseCase updateProductQuantityUseCase;
    private final GetProductUseCase getProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    /**
     * Creates a new product.
     *
     * @param productRequest The request body containing the details of the product to be created.
     * @return ResponseEntity<ProductResponse> A response entity containing the created product details if successful,
     * otherwise an error message and the corresponding HTTP status code.
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {

        ProductResponse.ProductResponseBuilder productBuilder = ProductResponse.builder();

        try {

            Product product = createProductUseCase.createProduct(MapperProduct.toProductDomain(productRequest));
            productBuilder.withProductId(product.getProductId());
            productBuilder.withProductName(product.getProductName());
            productBuilder.withDescription(product.getDescription());
            productBuilder.withImage(product.getImage());
            productBuilder.withPrice(product.getPrice());
            productBuilder.withProductId(product.getProductId());
            productBuilder.withQuantity(product.getQuantity());

            return new ResponseEntity<>(productBuilder.build(), HttpStatus.CREATED);

        } catch (IllegalArgumentException | ProductAlreayExistException | InsufficientProductQuantityException e) {
            productBuilder.withError(e.getMessage());
            return new ResponseEntity<>(productBuilder.build(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            productBuilder.withError(e.getMessage());
            return new ResponseEntity<>(productBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to be deleted.
     * @return ResponseEntity<Void> A response entity indicating the success or failure of the deletion operation.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        try {
            deleteProductUseCase.deleteProduct(productId);
            return ResponseEntity.ok("Product with ID " + productId + " has been deleted successfully.");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the product.");
        }
    }

    /**
     * Updates the quantity of a product.
     *
     * @param productId   The ID of the product to be updated.
     * @param newQuantity The new quantity to be assigned to the product.
     * @return ResponseEntity<ProductResponse> A response entity containing the updated product details if successful,
     * otherwise an error message and the corresponding HTTP status code.
     */
    @PutMapping("/{productId}/quantity/{newQuantity}")
    public ResponseEntity<?> updateProductQuantity(@PathVariable Integer productId, @PathVariable Integer newQuantity) {

        try {
            Product product = updateProductQuantityUseCase.updateProductQuantity(productId, newQuantity);

            ProductResponse.ProductResponseBuilder productBuilder = ProductResponse.builder();
            productBuilder.withProductName(product.getProductName());
            productBuilder.withDescription(product.getDescription());
            productBuilder.withImage(product.getImage());
            productBuilder.withPrice(product.getPrice());
            productBuilder.withQuantity(product.getQuantity());

            return new ResponseEntity<>(productBuilder.build(), HttpStatus.OK);

        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the product.");
        }
    }

    /**
     * Retrieves a product by its name.
     *
     * @param productId The name of the product to be retrieved.
     * @return ResponseEntity<Optional<Product>> A response entity containing the optional product if found,
     * otherwise an empty optional and the corresponding HTTP status code.
     */
    @GetMapping("/get/{productId}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable("productId") Integer productId) {

        try {
            Optional<Product> product = getProductUseCase.findById(productId);

            if (product.isPresent()) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
