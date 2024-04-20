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
//    @JsonProperty("TX_TAX")
//    private AdditionalValueRequest TX_TAX;
//    @JsonProperty("TX_TAX_RETURN_BASE")
//    private AdditionalValueRequest TX_TAX_RETURN_BASE;

}
