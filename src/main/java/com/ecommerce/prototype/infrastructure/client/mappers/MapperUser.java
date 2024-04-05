package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.infrastructure.client.request.UserRequest;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Cartdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.TokenizedCarddb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;

import java.util.List;
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

        List<TokenizedCard> tokenizedCars = userdb.getTokenizedCards().stream()
                .map(MapperTokenizedCard::mapToDomain)
                .collect(Collectors.toList());

        List<Order> orders = userdb.getOrders().stream()
                .map(MapperOrder::mapToDomain)
                .collect(Collectors.toList());

        List<Cart> carts = userdb.getCarts().stream()
                .map(MapperCart::mapToDomain)
                .collect(Collectors.toList());

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
                .admin(userdb.getAdmin())
                .deleted(userdb.getDeleted())
                .tokenizedCards(tokenizedCars)
                .orders(orders)
                .cards(carts)
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

        List<TokenizedCarddb> tokenizedCardbs = user.getTokenizedCards().stream()
                .map(MapperTokenizedCard::mapToModel)
                .collect(Collectors.toList());

        List<Orderdb> orderdbs = user.getOrders().stream()
                .map(MapperOrder::mapToModel)
                .collect(Collectors.toList());

        List<Cartdb> cartdbs = user.getCards().stream()
                .map(MapperCart::mapToModel)
                .collect(Collectors.toList());

        Userdb userdb = new Userdb();
        userdb.setUserId(user.getUserId());
        userdb.setName(user.getName());
        userdb.setEmail(user.getEmail().getAddress());
        userdb.setPassword(user.getPassword().getValue());
        userdb.setPhoneNumber(user.getPhoneNumber());
        userdb.setIdentificationType(user.getIdentificationType());
        userdb.setIdentificationNumber(user.getIdentificationNumber());
        userdb.setShippingAddress(user.getShippingAddress());
        userdb.setBillingAddress(user.getBillingAddress());
        userdb.setAdmin(user.getAdmin());
        userdb.setDeleted(user.getDeleted());
        userdb.setTokenizedCards(tokenizedCardbs);
        userdb.setCarts(cartdbs);
        userdb.setOrders(orderdbs);
        return userdb;
    }

}
