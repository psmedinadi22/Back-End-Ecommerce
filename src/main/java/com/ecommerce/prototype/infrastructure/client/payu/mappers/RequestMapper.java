package com.ecommerce.prototype.infrastructure.client.payu.mappers;

import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.infrastructure.client.payu.request.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public final class RequestMapper {
	private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);

	public static PaymentAPIRequest buildPaymentRequest(Order order, String apiKey, String apiLogin, String paymentMethod, String merchantId, String accountId, String currency) {

		return PaymentAPIRequest.builder()
								.language("es")
								.command("SUBMIT_TRANSACTION")
								.merchant(buildMerchant(apiKey, apiLogin))
								.transaction(buildTransaction(order, apiKey, paymentMethod, merchantId, accountId, currency))
								.test(true)
								.build();
	}

	private static Merchant buildMerchant(String apiKey, String apiLogin) {

		return Merchant.builder()
					   .apiLogin(apiLogin)
					   .apiKey(apiKey)
					   .build();
	}

	private static TransactionRequest buildTransaction(Order order, String apiKey, String paymentMethod, String merchantId, String accountId, String currency) {

		return TransactionRequest.builder()
								 .order(buildOrder(order, apiKey, merchantId, accountId, currency))
								 .payer(buildPayer(order.getBuyer()))
								 .creditCardTokenId(order.getCard().getTokenId())
								 .type("AUTHORIZATION_AND_CAPTURE")
								 .paymentMethod(paymentMethod)
								 .paymentCountry("CO")
								 .deviceSessionId("vghs6tvkcle931686k1900o6e1")
								 .ipAddress("127.0.0.1")
								 .cookie("pt1t38347bs6jc9ruv2ecpv7o2")
								 .userAgent("Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0")
								 .build();
	}


	private static OrderRequest buildOrder(Order order, String apiKey, String merchantId, String accountId, String currency) {


		String referenceCode = generateRefrenceCode();
		Integer totalAmount = (int) order.getTotalAmount();

		String signature = generateSignature(apiKey, merchantId, referenceCode, String.valueOf(totalAmount), currency);
		logger.info("Generated signature: {}", signature);

		return OrderRequest.builder()
						   .accountId(accountId)
						   .referenceCode(referenceCode)
						   .description("Payment test description")
						   .language("es")
						   .signature(signature)
						   .additionalValues(buildAdditionalValues(totalAmount))
						   .buyer(buildBuyer(order.getBuyer()))
						   .shippingAddress(buildShippingAddress(order.getBuyer()))
						   .build();
	}

	public static String generateRefrenceCode () {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentDate = dateFormat.format(new Date());
		return "EcommerceWehyah_" + currentDate;
	}

	/**
	 * Builds the additional values for the payment request.
	 *
	 * @return AdditionalValuesRequest The additional values.
	 */
	private static AdditionalValuesRequest buildAdditionalValues(Integer totalAmount) {
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
	private static AdditionalValueRequest buildAdditionalValue(Integer value, String currency) {
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
	private static BuyerRequest buildBuyer(Buyer user) {
		return BuyerRequest.builder()
						   .merchantBuyerId("3")
						   .fullName(user.getName())
						   .emailAddress(user.getEmail().getAddress())
						   .contactPhone(user.getPhoneNumber())
						   .dniNumber(user.getNumberId())
						   .shippingAddress(buildShippingAddress(user))
						   .build();
	}

	/**
	 * Builds the shipping address for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return ShippingAddressRequest The shipping address.
	 */
	private static ShippingAddressRequest buildShippingAddress(Buyer user) {
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
	private static PayerRequest buildPayer(Buyer user) {
		return PayerRequest.builder()
						   .merchantPayerId("3")
						   .fullName(user.getName())
						   .emailAddress(user.getEmail().getAddress())
						   .contactPhone(user.getPhoneNumber())
						   .dniNumber(user.getNumberId())
						   .billingAddress(buildBillingAddress(user))
						   .build();
	}

	/**
	 * Builds the billing address for the payment request.
	 *
	 * @param user The user information for the payment.
	 * @return BillingAddressRequest The billing address.
	 */
	private static BillingAddressRequest buildBillingAddress(Buyer user) {
		return BillingAddressRequest.builder()
									.street1(user.getBillingAddress().getStreet())
									.street2("00000")
									.city(user.getBillingAddress().getCity())
									.state(user.getBillingAddress().getState())
									.country("CO")
									.postalCode(user.getBillingAddress().getPostalCode())
									.phone(user.getBillingAddress().getPhone())
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
