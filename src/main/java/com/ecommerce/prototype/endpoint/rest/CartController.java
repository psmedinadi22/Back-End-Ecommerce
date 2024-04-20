package com.ecommerce.prototype.endpoint.rest;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.usecase.AddProductToCartUseCase;
import com.ecommerce.prototype.application.usecase.CreateCartUseCase;
import com.ecommerce.prototype.application.usecase.GetCartProductsUseCase;
import com.ecommerce.prototype.application.usecase.RemoveProductFromCartUseCase;
import com.ecommerce.prototype.application.usecase.exception.*;
import com.ecommerce.prototype.infrastructure.client.payu.request.AddProductRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.CartRequest;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CartController {

    private final CreateCartUseCase createCartUseCase;
    private final AddProductToCartUseCase addProductToCartUseCase;
    private final GetCartProductsUseCase getCartProductsUseCase;
    private final RemoveProductFromCartUseCase removeProductFromCartUseCase;

    /**
     * Creates a new cart for a user.
     *
     * @param cartRequest The request containing the user ID for which the cart is created.
     * @return ResponseEntity<Cart> A response entity containing the created cart if successful,
     * or an internal server error response if an exception occurs.
     */
    @PostMapping("/cart")
    public ResponseEntity<?> createCart(@RequestBody CartRequest cartRequest) {
        try {
            Cart cart = createCartUseCase.createCart(cartRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Error creating the cart"));
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }  catch (UserDisabledException | CartPendingPaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart cannot be created: " + e.getMessage());
        } catch (CartStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /**
     * Adds a product to a cart.
     *
     * @param addProductRequest The request containing the product ID and quantity to add.
     * @return ResponseEntity<String> A response entity indicating success or failure of the operation.
     */
    @PostMapping("/cart/addProduct")
    public ResponseEntity<String> addProductToCart(@RequestBody AddProductRequest addProductRequest) {
        try {
            addProductToCartUseCase.addProductToCart(addProductRequest.getProductId(), addProductRequest.getQuantity(), addProductRequest.getCartId());
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InsufficientProductQuantityException | ProductAlreadyInCartException | CartNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (CartStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Retrieves the products in a cart.
     *
     * @param cartId The ID of the cart to retrieve products from.
     * @return ResponseEntity<?> A response entity containing the list of products in the cart if successful,
     * or an internal server error response if an exception occurs.
     */
    @GetMapping("cart/{cartId}/products")
    public ResponseEntity<?> getCartProducts(@PathVariable int cartId) {

        try {
            List<Productdb> products = getCartProductsUseCase.getCartProducts(cartId);
            return ResponseEntity.ok(products);
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Removes a product from a cart.
     *
     * @param cartId The ID of the cart from which the product is removed.
     * @param productId The ID of the product to remove from the cart.
     * @return ResponseEntity<String> A response entity indicating success or failure of the operation.
     */
    @DeleteMapping("/cart/{cartId}/deleteProduct/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable  Integer cartId,@PathVariable Integer productId) {

        try {
            removeProductFromCartUseCase.removeProductFromCart(cartId,productId);
            return ResponseEntity.ok("Product removed from cart successfully");
        } catch (ProductNotFoundException | CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
