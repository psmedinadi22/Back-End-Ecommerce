package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.application.usecase.repository.OrderDetailRepository;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.OrderDetaildb;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@AllArgsConstructor
public class CreateOrderDetailUseCase {

    private OrderDetailRepository orderDetailRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateOrderDetailUseCase.class);


    /**
     * Creates a new order detail based on the provided order, user, cart, and payment method.
     *
     * @param order The order associated with the order detail.
     * @param user The user placing the order.
     * @param cart The cart containing the products to be purchased.
     * @param paymentMethod The payment method used for the order.
     * @return OrderDetail The newly created order detail.
     */
    public OrderDetaildb createOrderDetail(Order order, User user, Cart cart, String paymentMethod) {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setPurchaseDate(new Date());
        orderDetail.setTotalAmount(order.getTotalAmount());
        orderDetail.setPaymentMethod(paymentMethod);
        orderDetail.setBuyerId(user.getUserId());
        orderDetail.setBuyerFullName(user.getName());
        orderDetail.setBuyerEmailAddress(user.getEmail().getAddress());
        orderDetail.setBuyerContactPhone(user.getPhoneNumber());
        orderDetail.setBuyerDniNumber(user.getIdentificationNumber());
        orderDetail.setShippingAddress(user.getShippingAddress());
        orderDetail.setBillingAddress(user.getBillingAddress());
        orderDetail.setPurchaseStatus("PENDING");
        orderDetail.setCart(cart);
        orderDetail.setOrder(order);

        logger.info("Start creating Order Detail with ID: {}", orderDetail);
        return orderDetailRepository.createOrderDetail(orderDetail);
    }
}
