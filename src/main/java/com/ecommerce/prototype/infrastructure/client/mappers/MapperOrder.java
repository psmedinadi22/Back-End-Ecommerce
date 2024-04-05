package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;

public class MapperOrder {

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
        order.setUser(MapperUser.toUserDomain(orderdb.getUser()));
       // order.setPayment(orderdb.getPaymentdb() != null ? MapperPayment.mapToDomain(orderdb.getPaymentdb()) : null);

        return order;
    }

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
        orderdb.setUser(MapperUser.toUserModel(order.getUser()));
       // orderdb.setPaymentdb(order.getPayment() != null ? MapperPayment.mapToModel(order.getPayment()) : null);
        return orderdb;
    }
}
