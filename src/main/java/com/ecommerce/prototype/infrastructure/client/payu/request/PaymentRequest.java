package com.ecommerce.prototype.infrastructure.client.payu.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private Integer tokenizedCardId;
    private Integer cartId;
    private Integer userId;
    private String paymentMethod;
    private String apiKey;
    private String apiLogin;
    private String merchantId;
    private String accountId;
    private String referenceCode;
    private String txValue;
    private String currency;

    public PaymentRequest() {
       // this.userId = userId;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDate = dateFormat.format(new Date());
        this.referenceCode = "EcommerceWehyah_" + currentDate;
    }
}
