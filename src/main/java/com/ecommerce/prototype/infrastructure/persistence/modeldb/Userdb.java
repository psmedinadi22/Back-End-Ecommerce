package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.Address;
import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.Password;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder()
@Table(name = "users")
public class Userdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private Email email;
    private Password password;
    private String phoneNumber;
    private String identificationType;
    private String identificationNumber;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postal_code")),
            @AttributeOverride(name = "phone", column = @Column(name = "shipping_phone"))
    })
    private Address shippingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postal_code")),
            @AttributeOverride(name = "phone", column = @Column(name = "billing_phone"))
    })
    private Address billingAddress;
    private Boolean isAdmin;
    private Boolean isDeleted=false;

    public Userdb(String name, Email email, Password password, String phoneNumber, String identificationType, String identificationNumber, Address shippingAddress, Address billingAddress, Boolean isAdmin) {
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
                    .withEmail(this.email)
                    .withName(this.name)
                    .withPassword(this.password)
                    .withIsDeleted(this.isDeleted)
                    .withNumberId(this.identificationNumber)
                    .withTypeId(this.identificationType)
                    .withShippingAddress(this.shippingAddress)
                    .withBillingAddress(this.billingAddress)
                    .withPhoneNumber(this.phoneNumber)
                    .build();
    }
}
