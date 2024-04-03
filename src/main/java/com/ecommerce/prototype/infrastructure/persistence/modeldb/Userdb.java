package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.UserBillingAddress;
import com.ecommerce.prototype.application.domain.UserShippingAddress;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
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
    private Boolean admin;
    private Boolean deleted=false;
    @OneToMany(mappedBy = "user")
    private List<TokenizedCarddb> tokenizedCards;
    @OneToMany(mappedBy = "user")
    private List<Orderdb> orders;
    @OneToMany(mappedBy = "user")
    private List<Cartdb> carts;

    public Userdb(String name, String email, String password, String phoneNumber, String identificationType, String identificationNumber, UserShippingAddress shippingAddress, UserBillingAddress billingAddress, Boolean admin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.admin = admin;
    }


}