package com.ecommerce.prototype.infrastructure.persistence.modeldb;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cartdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    private String status;
    @ElementCollection
    private List<Integer> productsQuantity;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Productdb> products;
    @ManyToOne
    private Userdb user;
}
