package com.ecommerce.prototype.endpoint.rest.request;

import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.Payment;
import com.ecommerce.prototype.infrastructure.persistence.provider.UserProvider;
import lombok.AllArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@AllArgsConstructor
public class PaymentRequest {

	private Integer tokenizedCardId;
	private Integer cartId;
	private Integer userId;
	private String paymentMethod;
	private String currency;


	public Payment toPayment() {


		return Payment.builder()
				.withTokenId(this.tokenizedCardId)
				.withCartId(this.cartId)
				.withBuyerId(userId)
				.withPaymentMethod(paymentMethod)
				.withCurrency(this.currency)
				.withPaymentDate(new Date())
				.withPaymentStatus("Pending")
				.build();
	}
}

