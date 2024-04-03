package com.ecommerce.prototype.infrastructure.client.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalValueRequest {

    private Integer value;
    private String currency;


}
