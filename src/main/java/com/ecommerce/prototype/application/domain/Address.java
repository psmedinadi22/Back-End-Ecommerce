package com.ecommerce.prototype.application.domain;

import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class Address {

	private String street;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String phone;
}
