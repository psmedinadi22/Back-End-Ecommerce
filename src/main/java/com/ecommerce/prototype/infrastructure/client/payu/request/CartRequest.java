package com.ecommerce.prototype.infrastructure.client.payu.request;

import com.ecommerce.prototype.application.domain.Product;
import com.ecommerce.prototype.application.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    private Integer userId;
}
