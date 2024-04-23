package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.application.usecase.exception.*;
import com.ecommerce.prototype.application.usecase.repository.*;
import com.ecommerce.prototype.infrastructure.client.mappers.*;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.*;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.OrderJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class ProcessPaymentUseCase {

    private final CardRepository cardRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UpdateProductQuantityUseCase updateProductQuantityUseCase;
    private final OrderJPARepository orderJPARepository;
    private final ExternalPlatformRepository externalPlatformRepository;
    private final PaymentRepository paymentRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

    /**
     * Processes a payment request.
     *
     * @return The payment response.
     */
    public PaymentResponse processPayment(Payment payment) {

        logger.info("Processing payment for request with payment method : {} will init", payment.getPaymentMethod());

        var buyer = getBuyer(payment.getBuyer().getId());
        var card = verifyTokenizedCard(payment.getTokenId(), buyer.getId());
        var cart = getCart(payment.getCartId(), buyer.getId());

        var order = createOrder(cart, buyer, card);

        var paymentResponse = externalPlatformRepository.doPayment(order)
                .orElseThrow(() -> new InvalidPaymentException("Couldn't generate the payment"));


        logger.info("Payment processed successfully with response: {}", paymentResponse);

        return paymentResponse;
    }


    /**
     * Verifies the existence of the tokenized card with the specified ID.
     *
     * @param tokenizedCardId The ID of the tokenized card to verify.
     * @return The tokenized card if found.
     * @throws PaymentProcessingException If the tokenized card is not found.
     */
    private Card verifyTokenizedCard(Integer tokenizedCardId, Integer userId) {

        logger.info("Verifying tokenized card with ID: {} for user ID: {}", tokenizedCardId, userId);

        var card = cardRepository.findById(tokenizedCardId)
                .orElseThrow(() -> new PaymentProcessingException("Tokenized card not found with ID: " + tokenizedCardId));

        if ( !card.getPayerId().equals(userId) ) {
            throw new UnauthorizedCartAccessException("The Tokenized Card does not belong to the user.");
        }

        return card;
    }

    /**
     * Retrieves the cart with the specified ID.
     *
     * @param cartId The ID of the cart to retrieve.
     * @return The retrieved cart.
     * @throws OrderNotFoundException If the cart with the specified ID is not found.
     */
    private Cart getCart(Integer cartId, Integer buyerId) {

        logger.info("Retrieving cart with ID: {} for user ID: {}", cartId, buyerId);

        Cart cart = cartRepository.findById(cartId)
                                  .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));

        if ( !cart.getBuyer().getId().equals(buyerId) ) {
            throw new UnauthorizedCartAccessException("The cart does not belong to the user.");
        }

        if ( !cart.getStatus().equals("outstanding") ) {
            throw new CartStateException("Cannot process payment for a cart with status: " + cart.getStatus());
        }

        return cart;
    }

    /**
     * Retrieves the user associated with the provided ID.
     *
     * @param buyerId The ID of the user to retrieve.
     * @return The retrieved user.
     */
    private Buyer getBuyer(Integer buyerId) {

        logger.info("Getting user with ID: {}", buyerId);

        var buyer = userRepository.findBuyerById((buyerId));

        if ( buyer != null && buyer.getIsDeleted() ) {
            throw new UserDisabledException("The buyer with ID: " + buyerId + " is disabled.");
        } else return buyer;
    }


    /**
     * Creates an order based on the provided cart and user information.
     *
     * @param cart The cart associated with the order.
     * @param buyer The user associated with the order.
     * @return The created order.
     */
    private Order createOrder(Cart cart, Buyer buyer, Card card) {

        logger.info("Creating order for user ID: {}", buyer.getId());

        double totalAmount = calculateTotalAmount(cart);

        var order = Order.builder()
                         .withCreationDate(new Date())
                         .withBuyer(buyer)
                         .withOrderStatus("PENDING")
                         .withTotalAmount(totalAmount)
                         .withCart(cart)
                         .withCard(card)
                         .build();

        logger.info("Start creating Order with User Id : {}", buyer.getId());

        return orderRepository.createOrder(order, buyer.getId());
    }


    private double calculateTotalAmount(Cart cart) {

        double totalAmount = 0.0;
        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getProductQuantities();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }

    /**
     * Updates the order, order detail, and cart based on the provided payment response.
     *
     * @param order The order to update.
     * @param cart The cart to update.
     * @param response The payment response.
     */
    private void updateStatus(Order order, Cart cart, PaymentResponse response) {

        logger.info("Updating status for order ID: {}", order.getOrderID());

       /* String purchaseStatus = response.getCode().equals("SUCCESS") ? response.getTransactionResponse().getState() : "ERROR";
        orderDetail.setPurchaseStatus(purchaseStatus);
        order.setOrderStatus(purchaseStatus);
        cart.setStatus(purchaseStatus.equals("SUCCESS") ? "PAID" : purchaseStatus.equals("DECLINED") ? "DECLINED" : purchaseStatus);

        if (purchaseStatus.equals("APPROVED")) {
            updateInventory(cart);
        }

        cartRepository.save(MapperCart.mapToModel(cart));
        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);*/
    }


    /**
     * Updates product inventory after a purchase, based on the products and quantities in a cart.
     *
     * @param cart The cart containing the purchased products and quantities.
     * @throws ProductNotFoundException If a product is not found in the database.
     * @throws InsufficientProductQuantityException If the quantity in the cart is greater than the current product quantity.
     */
    public void  updateInventory(Cart cart) {

        logger.info("Updating inventory for cart ID: {}", cart.getCartId());

        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getProductQuantities();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantityInCart = quantities.get(i);

            Productdb productdb = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + product.getProductId()));

            int updatedQuantity = productdb.getQuantity() - quantityInCart;

            if (updatedQuantity < 0) {
                throw new InsufficientProductQuantityException("Insufficient quantity for product with ID: " + product.getProductId());
            }

            updateProductQuantityUseCase.updateProductQuantity(product.getProductId(), updatedQuantity);
            productRepository.save(MapperProduct.toProductDomain(productdb));
        }
    }
}
