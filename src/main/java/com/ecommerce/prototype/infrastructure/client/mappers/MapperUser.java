package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.infrastructure.client.payu.request.UserRequest;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapperUser {
    /**
     * Maps a UserRequest object to a User domain object.
     *
     * @param userRequest The UserRequest object to be mapped.
     * @return User The mapped User domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static User toUserDomain(UserRequest userRequest) throws IllegalArgumentException{

        return User.builder()
                .name(userRequest.getName())
                .email(new Email(userRequest.getEmail()))
                .password(new Password(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .identificationType(userRequest.getIdentificationType())
                .identificationNumber(userRequest.getIdentificationNumber())
                .shippingAddress(userRequest.getShippingAddress())
                .billingAddress(userRequest.getBillingAddress())
                .build();
    }

    /**
     * Maps a Userdb object to a User domain object.
     *
     * @param userdb The Userdb object to be mapped.
     * @return User The mapped User domain object.
     * @throws IllegalArgumentException If there is an error during mapping.
     */
    public static User toUserDomain(Userdb userdb) throws IllegalArgumentException{

        List<TokenizedCard> tokenizedCards = Optional.ofNullable(userdb.getTokenizedCards())
                .map(cards -> cards.stream().map(MapperTokenizedCard::mapToDomain).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<Order> orders = Optional.ofNullable(userdb.getOrders())
                .map(orderdbs -> orderdbs.stream().map(MapperOrder::mapToDomain).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<Cart> carts = Optional.ofNullable(userdb.getCarts())
                .map(cartdbs -> cartdbs.stream().map(MapperCart::mapToDomain).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return User.builder()
                .userId(userdb.getUserId())
                .name(userdb.getName())
                .email(new Email(userdb.getEmail()))
                .password(new Password(userdb.getPassword()))
                .phoneNumber(userdb.getPhoneNumber())
                .identificationType(userdb.getIdentificationType())
                .identificationNumber(userdb.getIdentificationNumber())
                .shippingAddress(userdb.getShippingAddress())
                .billingAddress(userdb.getBillingAddress())
                .isAdmin(userdb.getIsAdmin())
                .isDeleted(userdb.getIsDeleted())
                .tokenizedCards(Optional.ofNullable(tokenizedCards)
                        .orElse(Collections.emptyList()))
                .carts(Optional.ofNullable(carts)
                        .orElse(Collections.emptyList()))
                .orders(Optional.ofNullable(orders)
                        .orElse(Collections.emptyList()))
                .build();
    }

    /**
     * Maps a domain User object to a persistence Userdb model.
     *
     * @param user The User object to map.
     * @return The mapped Userdb model.
     * @throws IllegalArgumentException If the provided User object is null.
     */
    public static Userdb toUserModel(User user) throws IllegalArgumentException {
        List<TokenizedCarddb> tokenizedCardbs = Optional.ofNullable(user.getTokenizedCards())
                .map(cards -> cards.stream().map(MapperTokenizedCard::mapToModel).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<Orderdb> orderdbs = Optional.ofNullable(user.getOrders())
                .map(orders -> orders.stream().map(MapperOrder::mapToModel).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<Cartdb> cartdbs = Optional.ofNullable(user.getCarts())
                .map(cards -> cards.stream().map(MapperCart::mapToModel).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return Userdb.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail().getAddress())
                .password(user.getPassword().getValue())
                .phoneNumber(user.getPhoneNumber())
                .identificationType(user.getIdentificationType())
                .identificationNumber(user.getIdentificationNumber())
                .shippingAddress(user.getShippingAddress())
                .billingAddress(user.getBillingAddress())
                .isAdmin(user.getIsAdmin())
                .isDeleted(user.getIsDeleted())
                .tokenizedCards(Optional.ofNullable(tokenizedCardbs)
                        .orElse(Collections.emptyList()))
                .carts(Optional.ofNullable(cartdbs)
                        .orElse(Collections.emptyList()))
                .orders(Optional.ofNullable(orderdbs)
                        .orElse(Collections.emptyList()))
                .build();
    }


    public static User mapToDomainWithoutCarts(Userdb userdb) throws IllegalArgumentException {

        List<TokenizedCard> tokenizedCards = Optional.ofNullable(userdb.getTokenizedCards())
                .map(cards -> cards.stream().map(MapperTokenizedCard::mapToDomain).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<Order> orders = Optional.ofNullable(userdb.getOrders())
                .map(orderdbs -> orderdbs.stream().map(MapperOrder::mapToDomain).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return User.builder()
                .userId(userdb.getUserId())
                .name(userdb.getName())
                .email(new Email(userdb.getEmail()))
                .password(new Password(userdb.getPassword()))
                .phoneNumber(userdb.getPhoneNumber())
                .identificationType(userdb.getIdentificationType())
                .identificationNumber(userdb.getIdentificationNumber())
                .shippingAddress(userdb.getShippingAddress())
                .billingAddress(userdb.getBillingAddress())
                .isAdmin(userdb.getIsAdmin())
                .isDeleted(userdb.getIsDeleted())
                .tokenizedCards(Optional.ofNullable(tokenizedCards)
                        .orElse(Collections.emptyList()))
                .orders(Optional.ofNullable(orders)
                        .orElse(Collections.emptyList()))
                .build();
    }

    public static User mapToDomainWithoutOrders(Userdb userdb) throws IllegalArgumentException {
        List<TokenizedCard> tokenizedCards = Optional.ofNullable(userdb.getTokenizedCards())
                .map(cards -> cards.stream().map(MapperTokenizedCard::mapToDomain).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return User.builder()
                .userId(userdb.getUserId())
                .name(userdb.getName())
                .email(new Email(userdb.getEmail()))
                .password(new Password(userdb.getPassword()))
                .phoneNumber(userdb.getPhoneNumber())
                .identificationType(userdb.getIdentificationType())
                .identificationNumber(userdb.getIdentificationNumber())
                .shippingAddress(userdb.getShippingAddress())
                .billingAddress(userdb.getBillingAddress())
                .isAdmin(userdb.getIsAdmin())
                .isDeleted(userdb.getIsDeleted())
                .tokenizedCards(Optional.ofNullable(tokenizedCards)
                        .orElse(Collections.emptyList()))
                .build();
    }




}
