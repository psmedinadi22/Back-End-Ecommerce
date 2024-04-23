package com.ecommerce.prototype.infrastructure.client.mappers;

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
    public static Orderdb mapToModel(Order order) throws IllegalArgumentException{

        Orderdb orderdb = new Orderdb();
        orderdb.setOrderID(order.getOrderID());
        orderdb.setCreationDate(order.getCreationDate());
        orderdb.setTotalAmount(order.getTotalAmount());
        orderdb.setOrderStatus(order.getOrderStatus());
        orderdb.setUser(MapperUser.toUserModel(order.getBuyer().toUser()));
        return orderdb;
    }


    /**
     * Maps an Orderdb entity to an Order domain object.
     *
     * @param orderdb The Orderdb entity to be mapped.
     * @return Order The mapped Order domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static Order mapToDomain(Orderdb orderdb) throws IllegalArgumentException{
        Order order = new Order();
        order.setOrderID(orderdb.getOrderID());
        order.setCreationDate(orderdb.getCreationDate());
        order.setTotalAmount(orderdb.getTotalAmount());
        order.setOrderStatus(orderdb.getOrderStatus());
        order.setBuyer(orderdb.getUser().toBuyer());
        return order;
    }


}
