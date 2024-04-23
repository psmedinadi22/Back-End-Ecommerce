package com.ecommerce.prototype.application.domain;


import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class User {

    private Integer userId;
    private String name;
    private Email email;
    private Password password;
    private String phoneNumber;
    private String identificationType;
    private String identificationNumber;
    private Address shippingAddress;
    private Address billingAddress;
    private Boolean isAdmin;
    private Boolean isDeleted;


    public User(Integer userId, String name, Email email, Password password, String phoneNumber, String identificationType, String identificationNumber, Address shippingAddress, Address billingAddress, Boolean isAdmin, Boolean isDeleted) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.isAdmin = assignUserRoles(email);
        this.isDeleted = isDeleted;
        validateUserData(name, phoneNumber, identificationType, identificationNumber);
    }



    /**
     * Validates user data.
     *
     * @param name              The name of the user.
     * @param phoneNumber       The phone number of the user.
     * @param identificationType The type of identification of the user.
     * @param identificationNumber The identification number of the user.
     * @throws IllegalArgumentException if any of the parameters are null or empty.
     */
    public static void validateUserData(String name, String phoneNumber, String identificationType, String identificationNumber) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (identificationType == null || identificationType.isEmpty()) {
            throw new IllegalArgumentException("Identification type cannot be null or empty");
        }
        if (identificationNumber == null) {
            throw new IllegalArgumentException("Identification number cannot be null");
        }
    }

    /**
     * Assigns user roles based on the email address.
     *
     * @param email The email address of the user.
     * @return true if the email address contains "admin", false otherwise.
     */
    public static Boolean assignUserRoles(Email email) {
        return email.getAddress().toLowerCase().contains("admin");
    }


}

