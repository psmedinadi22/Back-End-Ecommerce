package com.ecommerce.prototype.endpoint.rest.request;

import com.ecommerce.prototype.application.domain.Payment;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
public class PaymentRequest {

	private Integer tokenizedCardId;
	private Integer cartId;
	private Integer userId; // Buyer
	private String paymentMethod;
	private String referenceCode;
	private String txValue;
	private String currency;


	public Payment toPayment() {

		return Payment.builder()
					  .withTokenId(this.tokenizedCardId)
					  .withAmount(Integer.parseInt(this.txValue))
					  .withPaymentDate(new Date())
					  .withPaymentStatus("Init")
					  .build();
	}

}
