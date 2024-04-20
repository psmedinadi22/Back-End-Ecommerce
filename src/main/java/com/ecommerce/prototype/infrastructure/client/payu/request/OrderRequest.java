package com.ecommerce.prototype.infrastructure.client.payu.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private String accountId;
    private String referenceCode;
    private String description;
    private String language;
    private String signature;
    private AdditionalValuesRequest additionalValues;
    private BuyerRequest buyer;
    private ShippingAddressRequest shippingAddress;
}
