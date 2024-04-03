package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.application.usecase.exception.*;
import com.ecommerce.prototype.application.usecase.repository.*;
import com.ecommerce.prototype.infrastructure.client.PaymentService;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperCart;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrderDetail;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperProduct;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.client.request.PaymentRequest;
import com.ecommerce.prototype.infrastructure.client.response.PaymentResponse;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProcessPaymentUseCase {

    private final TokenizedCardRepository tokenizedCardRepository;
    private final CartRepository cartRepository;
    private final CreateOrderUseCase createOrderUseCase;
    private final CreateOrderDetailUseCase createOrderDetailUseCase;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CreatePaymentUseCase createPaymentUseCase;

    public PaymentResponse processPayment(PaymentRequest paymentRequest) throws JsonProcessingException {

        User user = retrieveUser(paymentRequest.getUserId());
        TokenizedCarddb tokenizedCard = verifyTokenizedCard(paymentRequest.getTokenizedCardId(), paymentRequest.getUserId());
        Cart cart = retrieveCart(paymentRequest.getCartId(), paymentRequest.getUserId());
        Order order = createOrder(cart, user);

        PaymentResponse response = processPayment(user,(int) order.getTotalAmount(),
                tokenizedCard.getCreditCardTokenId(),
                paymentRequest);
        OrderDetail orderDetail = createOrderDetail(order, user, cart, paymentRequest.getPaymentMethod());
        createPaymentUseCase.createPayment(response.getTransactionResponse(), order.getOrderID(), paymentRequest.getPaymentMethod());
        updateStatus(order, cart, orderDetail, response);
        return response;
    }


    /**
     * Verifies the existence of the tokenized card with the specified ID.
     *
     * @param tokenizedCardId The ID of the tokenized card to verify.
     * @return The tokenized card if found.
     * @throws PaymentProcessingException If the tokenized card is not found.
     */
    private TokenizedCarddb verifyTokenizedCard(Integer tokenizedCardId, Integer userId) {

        TokenizedCarddb tokenizedCarddb = tokenizedCardRepository.findById(tokenizedCardId)
                .orElseThrow(() -> new PaymentProcessingException("Tokenized card not found with ID: " + tokenizedCardId));

        if (!tokenizedCarddb.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedCartAccessException("The Tokenized Card does not belong to the user.");
        }
        return  tokenizedCarddb;
    }

    /**
     * Retrieves the cart with the specified ID.
     *
     * @param cartId The ID of the cart to retrieve.
     * @return The retrieved cart.
     * @throws OrderNotFoundException If the cart with the specified ID is not found.
     */
    private Cart retrieveCart(Integer cartId, Integer userId) {
        Cart cart = MapperCart.mapToDomain(cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId)));

        if (!cart.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedCartAccessException("The cart does not belong to the user.");
        }

        if (!cart.getStatus().equals("outstanding")) {
            throw new CartStateException("Cannot process payment for a cart with status: " + cart.getStatus());
        }

        return cart;
    }

    /**
     * Retrieves the user associated with the provided ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The retrieved user.
     */
    private User retrieveUser(Integer userId) {
        Userdb userdb = userRepository.findById(userId);
        if (userdb!= null) {
            return MapperUser.toUserDomain(userdb);
        } else {
            throw new UserNoExistException("User not found with ID: " + userId);
        }
    }


    /**
     * Creates an order based on the provided cart and user information.
     *
     * @param cart The cart associated with the order.
     * @param user The user associated with the order.
     * @return The created order.
     */
    private Order createOrder(Cart cart, User user) {

        return createOrderUseCase.createOrder(cart, user.getUserId());
    }

    /**
     * Creates an order detail for the provided order, user, cart, and payment method.
     *
     * @param order The order associated with the order detail.
     * @param user The user associated with the order detail.
     * @param cart The cart associated with the order detail.
     * @param paymentMethod The payment method associated with the order detail.
     * @return The created order detail.
     */
    private OrderDetail createOrderDetail(Order order, User user, Cart cart, String paymentMethod) {
        return MapperOrderDetail.mapToDomain(createOrderDetailUseCase.createOrderDetail(order, user, cart, paymentMethod));
    }


    private PaymentResponse processPayment(User user, int totalAmount, String creditCardTokenId, PaymentRequest paymentRequest) throws JsonProcessingException {

        PaymentService paymentService = new PaymentService();
        return paymentService.processPayment(user, totalAmount, creditCardTokenId, paymentRequest);
    }

    /**
     * Updates the order, order detail, and cart based on the provided payment response.
     *
     * @param order The order to update.
     * @param cart The cart to update.
     * @param orderDetail The order detail to update.
     * @param response The payment response.
     */
    private void updateStatus(Order order, Cart cart, OrderDetail orderDetail, PaymentResponse response) {

        String purchaseStatus = response.getCode().equals("SUCCESS") ? response.getTransactionResponse().getState() : "ERROR";
        orderDetail.setPurchaseStatus(purchaseStatus);
        order.setOrderStatus(purchaseStatus);
        cart.setStatus(purchaseStatus.equals("SUCCESS") ? "PAID" : purchaseStatus.equals("DECLINED") ? "DECLINED" : purchaseStatus);

        if (purchaseStatus.equals("SUCCESS")) {
            updateInventory(cart);
        }

        cartRepository.save(MapperCart.mapToModel(cart));
        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);
    }


    /**
     * Updates product inventory after a purchase, based on the products and quantities in a cart.
     *
     * @param cart The cart containing the purchased products and quantities.
     * @throws ProductNotFoundException If a product is not found in the database.
     * @throws InsufficientProductQuantityException If the quantity in the cart is greater than the current product quantity.
     */
    public void  updateInventory(Cart cart) {

        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getProductsQuantity();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantityInCart = quantities.get(i);

            Productdb productdb = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + product.getProductId()));

            int updatedQuantity = productdb.getQuantity() - quantityInCart;
            if (updatedQuantity < 0) {
                throw new InsufficientProductQuantityException("Insufficient quantity for product with ID: " + product.getProductId());
            }
            productdb.setQuantity(updatedQuantity);
            productRepository.save(MapperProduct.toProductDomain(productdb));
        }
    }
}
