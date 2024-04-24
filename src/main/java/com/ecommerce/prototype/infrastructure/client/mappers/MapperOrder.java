package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Card;
import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;


public class MapperOrder {

    /**
     * Maps an Order domain object to an Orderdb entity.
     *
     * @param order The Order domain object to be mapped.
     * @return Orderdb The mapped Orderdb entity.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Orderdb mapToModel(Order order) throws IllegalArgumentException {
        return Orderdb.builder()
                .withOrderID(order.getOrderID())
                .withCreationDate(order.getCreationDate())
                .withTotalAmount(order.getTotalAmount())
                .withOrderStatus(order.getOrderStatus())
                .withUser(MapperUser.toUserModel(order.getBuyer().toUser()))
                .withBillingAddress(order.getBillingAddress())
                .withShippingAddress(order.getShippingAddress())
                .withTokenId(order.getCard().getTokenId())
                .withCartId(order.getCart().getCartId())
                .build();
    }



    /**
     * Maps an Orderdb entity to an Order domain object.
     *
     * @param orderdb The Orderdb entity to be mapped.
     * @return Order The mapped Order domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Order mapToDomain(Orderdb orderdb, Cart cart, Card card) throws IllegalArgumentException {
        return Order.builder()
                .withOrderID(orderdb.getOrderID())
                .withCreationDate(orderdb.getCreationDate())
                .withTotalAmount(orderdb.getTotalAmount())
                .withOrderStatus(orderdb.getOrderStatus())
                .withBuyer(orderdb.getUser().toBuyer())
                .withShippingAddress(orderdb.getShippingAddress())
                .withBillingAddress(orderdb.getBillingAddress())
                .withCart(cart)
                .withCard(card)
                .build();
    }

}
