package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.ProductAlreadyInCartException;
import com.ecommerce.prototype.application.usecase.exception.ProductNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperCart;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.CartJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CartProvider implements CartRepository {

    private final CartJPARepository cartJPARepository;
    private final ProductRepository productRepository;
    private final UserRepository userProvider;

    /**
     * Adds a product to a cart.
     *
     * @param product The product to be added.
     * @param quantity The quantity of the product to be added.
     * @param cartId The ID of the cart.
     * @throws ProductNotFoundException If the product does not exist.
     * @throws CartNotFoundException If the cart does not exist.
     * @throws ProductAlreadyInCartException If the product is already in the cart.
     */
    @Override
    public void addProductToCart(Product product, int quantity, int cartId) {

        Productdb productdb = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + product.getProductId() + " not found"));

        Cartdb cartdb = cartJPARepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with ID " + cartId + " not found"));

        boolean productAlreadyInCart = cartdb.getProducts().stream()
                            .anyMatch(cartProduct -> Objects.equals(cartProduct.getProductId(), product.getProductId()));

        if (productAlreadyInCart) {
            throw new ProductAlreadyInCartException("Product with ID " + product.getProductId() + " is already in the cart");
        }
        cartdb.getProducts().add(productdb);
        cartdb.getProductsQuantity().add(quantity);
        cartJPARepository.save(cartdb);
    }

    /**
     * Removes a product from a cart.
     *
     * @param cartId The ID of the cart.
     * @param product The product to be removed.
     * @throws CartNotFoundException If the cart does not exist.
     * @throws ProductNotFoundException If the product does not exist in the cart.
     */
    @Override
    public void removeProduct(Integer cartId, Product product) {

        Cartdb cart = cartJPARepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));

        Productdb existingProduct = cart.getProducts().stream()
                .filter(p -> p.getProductId().equals(product.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found in cart"));

        int index = cart.getProducts().indexOf(existingProduct);
        cart.getProducts().remove(existingProduct);


        if (index != -1 && index < cart.getProductsQuantity().size()) {
            cart.getProductsQuantity().remove(index);
        } else {
            throw new ProductNotFoundException("Product quantity not found in cart");
        }
        
        cartJPARepository.save(cart);
    }


    /**
     * Creates a new cart for a user.
     *
     * @param userId The ID of the user.
     * @return Optional of Cart.
     */
    @Override
    public Optional<Cart> createCart(Integer userId) {

        Cartdb cartdb = new Cartdb();
        cartdb.setStatus("outstanding");
        cartdb.setUser(userProvider.findById(userId));
        cartdb = cartJPARepository.save(cartdb);

        Cart savedCart =new Cart(
                cartdb.getCartId(),
                cartdb.getStatus()
        );
        return Optional.of(savedCart);
    }


    /**
     * Finds a cart by its ID.
     *
     * @param cartId The ID of the cart.
     * @return Optional of Cartdb.
     */
    @Override
    public Optional<Cartdb> findById(int cartId) {

        return cartJPARepository.findById(cartId);
    }

    /**
     * Saves a cart.
     *
     * @param cartdb The cart to be saved.
     */
    @Override
    public void save(Cartdb cartdb) {

        cartJPARepository.save(cartdb);
    }

    /**
     * Retrieves the status of a cart.
     *
     * @param cartId The ID of the cart.
     * @return The status of the cart.
     * @throws CartNotFoundException If the cart does not exist.
     */
    @Override
    public String getCartStatus(int cartId) {

        Cartdb cartOptional = cartJPARepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));

            return cartOptional.getStatus();
    }

    /**
     * Finds carts by user ID.
     *
     * @param userId The ID of the user.
     * @return A list of carts belonging to the user.
     */
    @Override
    public List<Cartdb> findByUserId(Integer userId) {

        return cartJPARepository.findByUserId(userId);
    }

    @Override
    public Optional<Cart> getCart(int cartId) {
        Cartdb cartdb = cartJPARepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        return Optional.of(MapperCart.mapToDomain(cartdb));
    }
}
