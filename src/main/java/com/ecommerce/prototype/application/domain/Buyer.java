package com.ecommerce.prototype.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "with")
public class Buyer {

	private Integer id;
	private String name;
	private Email email;
	private Password password;
	private String phoneNumber;
	private String typeId;
	private String numberId;
	private Boolean isDeleted;

}
