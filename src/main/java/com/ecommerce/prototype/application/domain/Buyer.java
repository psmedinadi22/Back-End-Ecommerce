package com.ecommerce.prototype.application.domain;

import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
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
	private Address shippingAddress;
	private Address billingAddress;

	public User toUser(){
		return User.builder()
				.userId(this.getId())
				.name(this.name)
				.email(this.email)
				.password(this.password)
				.phoneNumber(this.getPhoneNumber())
				.isDeleted(this.getIsDeleted())
				.identificationType(this.getTypeId())
				.identificationNumber(this.getNumberId())
				.shippingAddress(this.shippingAddress)
				.billingAddress(this.billingAddress)
				.isAdmin(false)
				.build();
	}
}
