package com.ecommerce.prototype.application.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder(setterPrefix = "with")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {

	private String id;
	private String status;
	private String state;
	private String error;
	private String message;
	private Order order;
	private String externalId;
	private String externalState;
	private Date creationDate;
}
