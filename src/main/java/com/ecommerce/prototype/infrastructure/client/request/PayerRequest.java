package com.ecommerce.prototype.infrastructure.client.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayerRequest {

    private String merchantPayerId;
    private String fullName;
    private String emailAddress;
    private String contactPhone;
    private String dniNumber;
    private BillingAddressRequest billingAddress;
}
