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
    public static Payment mapToDomain(Paymentdb paymentdb) throws IllegalArgumentException {
        return Payment.builder()
                .withPaymentID(paymentdb.getPaymentID())
                .withTransactionID(paymentdb.getTransactionID())
                .withPaymentDate(paymentdb.getPaymentDate())
                .withAmount(paymentdb.getAmount())
                .withPaymentMethod(paymentdb.getPaymentMethod())
                .withPaymentStatus(paymentdb.getPaymentStatus())
                .build();
    }


    /**
     * Maps a Payment object to its corresponding data model Paymentdb.
     *
     * @param payment The Payment object to map.
     * @return A Paymentdb object mapped from the Payment object.
     * @throws IllegalArgumentException If the provided Payment object is null.
     */
    public static Paymentdb mapToModel(Payment payment) throws IllegalArgumentException{

        return Paymentdb.builder()
                .withPaymentID(payment.getPaymentID())
                .withTransactionID(payment.getTransactionID())
                .withPaymentDate(payment.getPaymentDate())
                .withAmount(payment.getAmount())
                .withPaymentMethod(payment.getPaymentMethod())
                .withPaymentStatus(payment.getPaymentStatus())
                .build();
    }

}
