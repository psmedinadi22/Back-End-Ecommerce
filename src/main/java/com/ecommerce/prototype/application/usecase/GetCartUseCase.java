package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.usecase.repository.CartRepository;
import lombok.AllArgsConstructor;
import java.util.Optional;

@AllArgsConstructor
public class GetCartUseCase {

    private CartRepository cartRepository;

    public Optional<Cart> getCartDetails(int cartId) {
        return cartRepository.getCart(cartId);
    }
}