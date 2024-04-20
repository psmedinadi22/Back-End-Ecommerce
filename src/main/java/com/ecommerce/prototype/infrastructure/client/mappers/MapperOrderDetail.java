package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.OrderDetaildb;

public class MapperOrderDetail {

    /**
     * Maps an OrderDetaildb entity to an OrderDetail domain object.
     *
     * @param orderDetaildb The OrderDetaildb entity to be mapped.
     * @return OrderDetail The mapped OrderDetail domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static OrderDetail mapToDomain(OrderDetaildb orderDetaildb) throws IllegalArgumentException {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(orderDetaildb.getOrderDetailId());
        orderDetail.setPurchaseDate(orderDetaildb.getPurchaseDate());
        orderDetail.setTotalAmount(orderDetaildb.getTotalAmount());
        orderDetail.setPaymentMethod(orderDetaildb.getPaymentMethod());
        orderDetail.setPurchaseStatus(orderDetaildb.getPurchaseStatus());
        orderDetail.setBuyerId(orderDetaildb.getBuyerId());
        orderDetail.setBuyerFullName(orderDetaildb.getBuyerFullName());
        orderDetail.setBuyerEmailAddress(orderDetaildb.getBuyerEmailAddress());
        orderDetail.setBuyerContactPhone(orderDetaildb.getBuyerContactPhone());
        orderDetail.setBuyerDniNumber(orderDetaildb.getBuyerDniNumber());
        orderDetail.setShippingAddress(orderDetaildb.getShippingAddress());
        orderDetail.setBillingAddress(orderDetaildb.getBillingAddress());
        orderDetail.setCart(MapperCart.mapToDomain(orderDetaildb.getCart()));
        orderDetail.setOrder(MapperOrder.mapToDomain(orderDetaildb.getOrder()));
        return orderDetail;
    }


    /**
     * Maps an OrderDetail domain object to an OrderDetaildb entity.
     *
     * @param orderDetail The OrderDetail domain object to be mapped.
     * @return OrderDetaildb The mapped OrderDetaildb entity.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static OrderDetaildb mapToModel(OrderDetail orderDetail) throws IllegalArgumentException {

        OrderDetaildb orderDetaildb = new OrderDetaildb();
        orderDetaildb.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetaildb.setPurchaseDate(orderDetail.getPurchaseDate());
        orderDetaildb.setTotalAmount(orderDetail.getTotalAmount());
        orderDetaildb.setPaymentMethod(orderDetail.getPaymentMethod());
        orderDetaildb.setPurchaseStatus(orderDetail.getPurchaseStatus());
        orderDetaildb.setBuyerId(orderDetail.getBuyerId());
        orderDetaildb.setBuyerFullName(orderDetail.getBuyerFullName());
        orderDetaildb.setBuyerEmailAddress(orderDetail.getBuyerEmailAddress());
        orderDetaildb.setBuyerContactPhone(orderDetail.getBuyerContactPhone());
        orderDetaildb.setBuyerDniNumber(orderDetail.getBuyerDniNumber());
        orderDetaildb.setShippingAddress(orderDetail.getShippingAddress());
        orderDetaildb.setBillingAddress(orderDetail.getBillingAddress());
        orderDetaildb.setCart(MapperCart.mapToModel(orderDetail.getCart()));
        orderDetaildb.setOrder(MapperOrder.mapToModel(orderDetail.getOrder()));
        return orderDetaildb;
    }
}

