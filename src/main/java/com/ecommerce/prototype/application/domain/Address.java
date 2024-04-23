package com.ecommerce.prototype.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "with")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private String street;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String phone;
}
