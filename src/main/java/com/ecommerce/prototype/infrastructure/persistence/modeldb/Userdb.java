package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.Address;
import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.Password;
import com.ecommerce.prototype.application.domain.UserBillingAddress;
import com.ecommerce.prototype.application.domain.UserShippingAddress;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Table(name = "users")
public class Userdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String identificationType;
    private String identificationNumber;
    @Embedded
    private UserShippingAddress shippingAddress;
    @Embedded
    private UserBillingAddress billingAddress;
    private Boolean isAdmin;
    private Boolean isDeleted=false;

    public Userdb(String name, String email, String password, String phoneNumber, String identificationType, String identificationNumber, UserShippingAddress shippingAddress, UserBillingAddress billingAddress, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.isAdmin = isAdmin;
    }


    public Buyer toBuyer() {

        return Buyer.builder()
                    .withId(this.userId)
                    .withEmail(new Email(this.email))
                    .withName(this.name)
                    .withPassword(new Password(this.password))
                    .withIsDeleted(this.isDeleted)
                    .withNumberId(this.identificationNumber)
                    .withTypeId(this.identificationType)
                    .withShippingAddress(toAddress(this.shippingAddress))
                    .withBillingAddress(toAddress(this.billingAddress))
                    .withPhoneNumber(this.phoneNumber)
                    .build();
    }

    private Address toAddress(UserShippingAddress userShippingAddress) {

        return Address.builder()
                      .withCity(userShippingAddress.getCity())
                      .withCountry(userShippingAddress.getCountry())
                      .withPhone(userShippingAddress.getPhone())
                      .withStreet(userShippingAddress.getStreet())
                      .withState(userShippingAddress.getState())
                      .withPostalCode(userShippingAddress.getPostalCode())
                      .build();
    }

    private Address toAddress(UserBillingAddress userBillingAddress) {

        return Address.builder()
                      .withCity(userBillingAddress.getBillingCity())
                      .withCountry(userBillingAddress.getBillingCountry())
                      .withPhone(userBillingAddress.getBillingPhone())
                      .withStreet(userBillingAddress.getBillingStreet())
                      .withState(userBillingAddress.getBillingState())
                      .withPostalCode(userBillingAddress.getBillingPostalCode())
                      .build();
    }


}
