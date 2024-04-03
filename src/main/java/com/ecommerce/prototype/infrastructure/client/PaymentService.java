package com.ecommerce.prototype.infrastructure.client;

import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.infrastructure.client.request.*;
import com.ecommerce.prototype.infrastructure.client.response.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class PaymentService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    /**
     * Processes the payment using the provided user information, total amount, credit card token, and payment method.
     *
     * @param user The user information for the payment.
     * @param totalAmount The total amount of the payment.
     * @param creditCardTokenId The ID of the credit card token.
     * @return PaymentResponse The response of the payment process.
     * @throws JsonProcessingException If there is an error processing JSON.
     */
    public PaymentResponse processPayment(User user, int totalAmount, String creditCardTokenId, PaymentRequest paymentRequest) throws JsonProcessingException {

        PaymentAPIRequest apiRequest = buildPaymentRequest(user, totalAmount, creditCardTokenId, paymentRequest.getPaymentMethod(), paymentRequest);
        logger.info(String.valueOf(apiRequest));
        String apiRequestAsJson = objectMapper.writeValueAsString(apiRequest);
        logger.info("API Request {}:", apiRequestAsJson);

        ResponseEntity<String> responseEntity = sendPaymentRequest(apiRequest);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            logger.info("Response body: {}", responseBody);

            return objectMapper.readValue(responseBody, PaymentResponse.class);
        } else {
            //TODO: MAS INFO EN LOS ERRORES, PONER MIS PROPIAS EXCEPCIONES
            throw new RuntimeException("Error processing payment");
        }
    }

    /**
     * Sends the payment request to the payment API.
     *
     * @param apiRequest The request to be sent.
     * @return ResponseEntity<String> The response from the payment API.
     */
    private ResponseEntity<String> sendPaymentRequest(PaymentAPIRequest apiRequest) {
        logger.info("Calling PayU platform with reference: {}", apiRequest.getTransaction().getOrder().getReferenceCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentAPIRequest> requestEntity = new HttpEntity<>(apiRequest, headers);
        return new RestTemplate().exchange(
                "https://sandbox.api.payulatam.com/payments-api/4.0/service.cgi",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }


    /**
     * Builds the payment request based on user information, total amount, credit card token, and payment method.
     *
     * @param user The user information for the payment.
     * @param totalAmount The total amount of the payment.
     * @param creditCardTokenId The ID of the credit card token.
     * @param paymentMethod The payment method.
     * @return PaymentAPIRequest The payment request.
     */
    private PaymentAPIRequest buildPaymentRequest(User user, Integer totalAmount, String creditCardTokenId, String paymentMethod, PaymentRequest paymentRequest) {
        return PaymentAPIRequest.builder()
                .language("es")
                .command("SUBMIT_TRANSACTION")
                .merchant(buildMerchant(paymentRequest))
                .transaction(buildTransaction(user, totalAmount, creditCardTokenId, paymentMethod, paymentRequest))
                .test(true)
                .build();
    }

    /**
     * Builds the merchant information for the payment request.
     *
     * @return Merchant The merchant information.
     */
    private Merchant buildMerchant(PaymentRequest paymentRequest) {

        String apiLogin = paymentRequest.getApiLogin();
        String apiKey = paymentRequest.getApiKey();
        return Merchant.builder()
                .apiLogin(apiLogin)
                .apiKey(apiKey)
                .build();
    }

    /**
     * Builds the transaction information for the payment request.
     *
     * @param user The user information for the payment.
     * @param totalAmount The total amount of the payment.
     * @param creditCardTokenId The ID of the credit card token.
     * @param paymentMethod The payment method.
     * @return TransactionRequest The transaction information.
     */
    private TransactionRequest buildTransaction(User user, Integer totalAmount, String creditCardTokenId, String paymentMethod, PaymentRequest paymentRequest) {
        return TransactionRequest.builder()
                .order(buildOrder(user, totalAmount, paymentRequest))
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
    private OrderRequest buildOrder(User user, Integer totalAmount, PaymentRequest paymentRequest) {

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