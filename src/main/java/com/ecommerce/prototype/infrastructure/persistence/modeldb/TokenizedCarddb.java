package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.ecommerce.prototype.application.domain.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name ="TokenizedCards")
public class TokenizedCarddb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String creditCardTokenId;
    private String name;
    private String payerId;
    private String identificationNumber;
    private String paymentMethod;
    private String maskedNumber;
    private String expirationDate;
    @ManyToOne
    private  Userdb user;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public TokenizedCarddb(String code, String creditCardTokenId, String name, String payerId, String identificationNumber, String paymentMethod, String maskedNumber, String expirationDate) {

        this.code = code;
        this.creditCardTokenId = creditCardTokenId;
        this.name = name;
        this.payerId = payerId;
        this.identificationNumber = identificationNumber;
        this.paymentMethod = paymentMethod;
        this.maskedNumber = maskedNumber;
        this.expirationDate = expirationDate;
    }

    public Card toCard() {

        return Card.builder()
                   .withNumber(this.maskedNumber)
                   .withName(this.name)
                   .withExpirationDate(this.expirationDate)
                   .withPayerId(Integer.parseInt(this.payerId))
                   .withIdentificationNumber(this.identificationNumber)
                   .withPaymentMethod(this.paymentMethod)
                   .withTokenId(this.creditCardTokenId)
                   .build();
    }

}
