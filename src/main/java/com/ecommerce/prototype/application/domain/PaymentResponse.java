package com.ecommerce.prototype.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder(setterPrefix = "with")
@Getter
public class PaymentResponse {

	private String id;
	private String status;
	private String state;
	private String error;
	private String message;
	private Order order;
	private String externalId;
	private String externalState;
	private String creationDate;
}
