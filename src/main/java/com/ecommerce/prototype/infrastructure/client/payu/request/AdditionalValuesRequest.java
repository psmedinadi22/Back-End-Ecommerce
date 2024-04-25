package com.ecommerce.prototype.infrastructure.client.payu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalValuesRequest {

    @JsonProperty("TX_VALUE")
    private AdditionalValueRequest TX_VALUE;
}
