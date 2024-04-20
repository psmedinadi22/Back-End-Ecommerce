package com.ecommerce.prototype.infrastructure.client.payu.mappers;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.infrastructure.client.payu.request.AdditionalValuesRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.BillingAddressRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.BuyerRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.Merchant;
import com.ecommerce.prototype.infrastructure.client.payu.request.OrderRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.PayerRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.PaymentAPIRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.PaymentRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.ShippingAddressRequest;
import com.ecommerce.prototype.infrastructure.client.payu.request.TransactionRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class RequestMapper {

	private RequestMapper() {}

	public static PaymentAPIRequest buildPaymentRequest(Order order, String apiKey, String apiLogin) {

		return PaymentAPIRequest.builder()
								.language("es")
								.command("SUBMIT_TRANSACTION")
								.merchant(buildMerchant(apiKey, apiLogin))
								.transaction(buildTransaction(order, apiKey))
								.test(true)
								.build();
	}

	private static Merchant buildMerchant(String apiKey, String apiLogin) {

		return Merchant.builder()
					   .apiLogin(apiLogin)
					   .apiKey(apiKey)
					   .build();
	}

	private static TransactionRequest buildTransaction(Order order, String apiKey) {

		return TransactionRequest.builder()
								 .order(buildOrder(order, apiKey))
								 .payer(buildPayer(user))
								 .creditCardTokenId(creditCardTokenId)
								 .type("AUTHORIZATION_AND_CAPTURE")
								 .paymentMethod(paymentMethod)
								 .paymentCountry("CO")
								 .deviceSessionId("vghs6tvkcle931686k1900o6e1")
								 .ipAddress("127.0.0.1")
								 .cookie("pt1t38347bs6jc9ruv2ecpv7o2")
								 .userAgent("Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0")
								 .build();
	}

	/**
	 * Builds the order information for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return OrderRequest The order information.
	 */
	private OrderRequest buildOrder(Order order, String apiLey) {

		String apiKey = paymentRequest.getApiKey();
		String merchantId = paymentRequest.getMerchantId();
		String accountId = paymentRequest.getAccountId();
		String referenceCode = paymentRequest.getReferenceCode();
		String currency = paymentRequest.getCurrency();

		String signature = generateSignature(apiKey, merchantId, referenceCode, String.valueOf(totalAmount), currency);
		logger.info("Generated signature: {}", signature);

		return OrderRequest.builder()
						   .accountId(accountId)
						   .referenceCode(referenceCode)
						   .description("Payment test description")
						   .language("es")
						   .signature(signature)
						   .additionalValues(buildAdditionalValues(totalAmount))
						   .buyer(buildBuyer(user))
						   .shippingAddress(buildShippingAddress(user))
						   .build();
	}

	/**
	 * Builds the additional values for the payment request.
	 *
	 * @return AdditionalValuesRequest The additional values.
	 */
	private AdditionalValuesRequest buildAdditionalValues(Integer totalAmount) {
		return AdditionalValuesRequest.builder()
									  .TX_VALUE(buildAdditionalValue(totalAmount, "COP"))
									  .build();
	}

	/**
	 * Builds a single additional value for the payment request.
	 *
	 * @param value The value of the additional value.
	 * @param currency The currency of the additional value.
	 * @return AdditionalValueRequest The additional value.
	 */
	private AdditionalValueRequest buildAdditionalValue(Integer value, String currency) {
		return AdditionalValueRequest.builder()
									 .value(value)
									 .currency(currency)
									 .build();
	}

	/**
	 * Builds the buyer information for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return BuyerRequest The buyer information.
	 */
	private BuyerRequest buildBuyer(User user) {
		return BuyerRequest.builder()
						   .merchantBuyerId("3")
						   .fullName(user.getName())
						   .emailAddress(user.getEmail().getAddress())
						   .contactPhone(user.getPhoneNumber())
						   .dniNumber(user.getIdentificationNumber())
						   .shippingAddress(buildShippingAddress(user))
						   .build();
	}

	/**
	 * Builds the shipping address for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return ShippingAddressRequest The shipping address.
	 */
	private ShippingAddressRequest buildShippingAddress(User user) {
		return ShippingAddressRequest.builder()
									 .street1(user.getShippingAddress().getStreet())
									 .street2("00000")
									 .city(user.getShippingAddress().getCity())
									 .state(user.getShippingAddress().getState())
									 .country("CO")
									 .postalCode(user.getShippingAddress().getPostalCode())
									 .phone(user.getShippingAddress().getPhone())
									 .build();
	}

	/**
	 * Builds the payer information for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return PayerRequest The payer information.
	 */
	private PayerRequest buildPayer(User user) {
		return PayerRequest.builder()
						   .merchantPayerId("3")
						   .fullName(user.getName())
						   .emailAddress(user.getEmail().getAddress())
						   .contactPhone(user.getPhoneNumber())
						   .dniNumber(user.getIdentificationNumber())
						   .billingAddress(buildBillingAddress(user))
						   .build();
	}

	/**
	 * Builds the billing address for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return BillingAddressRequest The billing address.
	 */
	private BillingAddressRequest buildBillingAddress(User user) {
		return BillingAddressRequest.builder()
									.street1(user.getBillingAddress().getBillingStreet())
									.street2("00000")
									.city(user.getBillingAddress().getBillingCity())
									.state(user.getBillingAddress().getBillingState())
									.country("CO")
									.postalCode(user.getBillingAddress().getBillingPostalCode())
									.phone(user.getBillingAddress().getBillingPhone())
									.build();
	}

	public static String generateSignature(String apiKey, String merchantId, String referenceCode, String txValue, String currency) {

		String concatenatedString = apiKey + "~" + merchantId + "~" + referenceCode + "~" + txValue + "~" + currency;
		logger.info("Generated concatenatedString: {}", concatenatedString);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(concatenatedString.getBytes());
			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}
