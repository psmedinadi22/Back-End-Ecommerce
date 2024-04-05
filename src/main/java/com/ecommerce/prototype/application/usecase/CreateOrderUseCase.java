package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.persistence.provider.UserProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateOrderUseCase {

    private OrderRepository orderRepository;
    private UserProvider userProvider;
    private static final Logger logger = LoggerFactory.getLogger(CreateOrderUseCase.class);


    /**
     * Creates an order based on the provided cart.
     *
     * @param cart The cart containing the products to be ordered.
     * @return The created order.
     */
    public Order createOrder(Cart cart, Integer userId) {

        User user = MapperUser.toUserDomain(userProvider.findById(userId));
        double totalAmount = calculateTotalAmount(cart);
        Order order = new Order();
        order.setCreationDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("PENDING");
        order.setUser(user);
//        Payment payment = new Payment();
//        payment.setPaymentStatus("PENDING");
        logger.info("begin create order in repository");
        return orderRepository.createOrder(order, userId);
    }

    /**
     * Calculates the total amount of the products in the cart.
     *
     * @param cart The cart containing the products.
     * @return The total amount of the products in the cart.
     */
    public double calculateTotalAmount(Cart cart) {

        double totalAmount = 0.0;
        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getProductsQuantity();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }
}

