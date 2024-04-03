package com.ecommerce.prototype.infrastructure.client.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingAddressRequest {

    private String street1;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
}
