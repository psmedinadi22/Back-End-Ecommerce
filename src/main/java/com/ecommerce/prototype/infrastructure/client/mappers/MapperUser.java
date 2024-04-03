package com.ecommerce.prototype.infrastructure.client.mappers;

import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.Password;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.infrastructure.client.request.UserRequest;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;

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
                .email(new Email(userdb.getEmail()))
                .password(new Password(userdb.getPassword()))
                .phoneNumber(userdb.getPhoneNumber())
                .identificationType(userdb.getIdentificationType())
                .identificationNumber(userdb.getIdentificationNumber())
                .shippingAddress(userdb.getShippingAddress())
                .billingAddress(userdb.getBillingAddress())
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

        Userdb userdb = new Userdb();
        userdb.setUserId(user.getUserId());
        userdb.setName(user.getName());
        userdb.setEmail(user.getEmail().getAddress());
        userdb.setPassword(userdb.getPassword());
        userdb.setPhoneNumber(user.getPhoneNumber());
        userdb.setIdentificationType(user.getIdentificationType());
        userdb.setIdentificationNumber(user.getIdentificationNumber());
        userdb.setShippingAddress(user.getShippingAddress());
        userdb.setBillingAddress(user.getBillingAddress());
        return userdb;
    }

}
