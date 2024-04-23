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


        return User.builder()
                .userId(userdb.getUserId())
                .name(userdb.getName())
                .email(userdb.getEmail())
                .password(userdb.getPassword())
                .phoneNumber(userdb.getPhoneNumber())
                .identificationType(userdb.getIdentificationType())
                .identificationNumber(userdb.getIdentificationNumber())
                .shippingAddress(userdb.getShippingAddress())
                .billingAddress(userdb.getBillingAddress())
                .isAdmin(userdb.getIsAdmin())
                .isDeleted(userdb.getIsDeleted())
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

        return Userdb.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .identificationType(user.getIdentificationType())
                .identificationNumber(user.getIdentificationNumber())
                .shippingAddress(user.getShippingAddress())
                .billingAddress(user.getBillingAddress())
                .isAdmin(user.getIsAdmin())
                .isDeleted(user.getIsDeleted())
                .build();
    }
}
