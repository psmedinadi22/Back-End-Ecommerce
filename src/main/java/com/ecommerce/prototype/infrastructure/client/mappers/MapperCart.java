package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapperCart {

    /**
     * Maps a Cartdb entity to a Cart domain object.
     *
     * @param cartdb The Cartdb entity to be mapped.
     * @return Cart The mapped Cart domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Cart mapToDomain(Cartdb cartdb) throws IllegalArgumentException {

        List<Product> products = cartdb.getProducts().stream()
                .map(MapperProduct::toProductDomain)
                .collect(Collectors.toList());
        List<Integer> productsQuantity = new ArrayList<>(cartdb.getProductQuantities());

        Cart cart = new Cart();
        cart.setCartId(cartdb.getCartId());
        cart.setStatus(cartdb.getStatus());
        cart.setProducts(products);
        cart.setProductQuantities(productsQuantity);
        cart.setBuyer(cartdb.getUser().toBuyer());
        return cart;
    }

    /**
     * Maps a Cart domain object to a Cartdb entity.
     *
     * @param cart The Cart domain object to be mapped.
     * @return Cartdb The mapped Cartdb entity.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Cartdb mapToModel(Cart cart) throws IllegalArgumentException {

        List<Productdb> productdbs = cart.getProducts().stream()
                .map(MapperProduct::toProductModel)
                .collect(Collectors.toList());
        List<Integer> productsQuantity = new ArrayList<>(cart.getProductQuantities());

        Cartdb cartdb = new Cartdb();
        cartdb.setCartId(cart.getCartId());
        cartdb.setStatus(cart.getStatus());
        cartdb.setProducts(productdbs);
        cartdb.setProductQuantities(productsQuantity);
        cartdb.setUser(MapperUser.toUserModel(cart.getBuyer().toUser()));
        return cartdb;
    }
}
