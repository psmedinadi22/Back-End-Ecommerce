package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Paymentdb;

public class MapperPayment {

    /**
     * Maps a Paymentdb object to its corresponding domain model Payment.
     *
     * @param paymentdb The Paymentdb object to map.
     * @return A Payment object mapped from the Paymentdb object.
     * @throws IllegalArgumentException If the provided Paymentdb object is null.
     */
    public static Payment mapToDomain(Paymentdb paymentdb) throws IllegalArgumentException{

        Payment payment = new Payment();
        payment.setPaymentID(paymentdb.getPaymentID());
        payment.setTransactionID(paymentdb.getTransactionID());
        payment.setPaymentDate(paymentdb.getPaymentDate());
        payment.setAmount(paymentdb.getAmount());
        payment.setPaymentMethod(paymentdb.getPaymentMethod());
        payment.setPaymentStatus(paymentdb.getPaymentStatus());
       // payment.setOrder(MapperOrder.mapToDomain(paymentdb.getOrder()));

        return payment;
    }

    /**
     * Maps a Payment object to its corresponding data model Paymentdb.
     *
     * @param payment The Payment object to map.
     * @return A Paymentdb object mapped from the Payment object.
     * @throws IllegalArgumentException If the provided Payment object is null.
     */
    public static Paymentdb mapToModel(Payment payment) throws IllegalArgumentException{

        Paymentdb paymentdb = new Paymentdb();
        paymentdb.setPaymentID(payment.getPaymentID());
        paymentdb.setTransactionID(payment.getTransactionID());
        paymentdb.setPaymentDate(payment.getPaymentDate());
        paymentdb.setAmount(payment.getAmount());
        paymentdb.setPaymentMethod(payment.getPaymentMethod());
        paymentdb.setPaymentStatus(payment.getPaymentStatus());
     //   paymentdb.setOrder(MapperOrder.mapToModel(payment.getOrder()));

        return paymentdb;
    }

}
