package com.ecommerce.prototype.infrastructure.client.payu.provider;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.PaymentResponse;
import com.ecommerce.prototype.application.usecase.repository.ExternalPlatformRepository;
import com.ecommerce.prototype.infrastructure.client.PayuConfig;
import com.ecommerce.prototype.infrastructure.client.payu.mappers.RequestMapper;
import com.ecommerce.prototype.infrastructure.client.payu.request.PaymentAPIRequest;
import com.ecommerce.prototype.infrastructure.client.payu.response.PayUResponse;
import com.ecommerce.prototype.infrastructure.persistence.provider.UserProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PayUProvider implements ExternalPlatformRepository {

	private PayuConfig payuConfig;
	private HttpClient client;
	private static final Logger logger = LoggerFactory.getLogger(PayUProvider.class);


	@Override
	public Optional<PaymentResponse> doPayment(Order order) {

		PaymentAPIRequest apiRequest = RequestMapper.buildPaymentRequest(order, payuConfig.apiKey, payuConfig.apiLogin, "VISA", payuConfig.merchantId, payuConfig.accountId, "COP");
		ResponseEntity<String> responseEntity = doPostPayU(apiRequest);

		logger.info(String.valueOf(responseEntity));

		if ( responseEntity.getStatusCode() == HttpStatus.OK ) {

			ObjectMapper objectMapper = new ObjectMapper();
			String responseBody = responseEntity.getBody();
			try {
				var payuResponse = objectMapper.readValue(responseBody, PayUResponse.class);

				// APPROVED, DECLINED, ERROR, PENDING

				long timestamp = Long.parseLong(payuResponse.getTransactionResponse().getOperationDate());
				Date date = new Date(timestamp);

				return Optional.of(PaymentResponse.builder()
												  .withStatus("SUCCESS")
						                          .withState(payuResponse.getTransactionResponse().getState().equals("APPROVED") ? "ENTREGAR_PRODUCTO" : "")
												  .withExternalId(payuResponse.getTransactionResponse().getTransactionId())
												  .withExternalState(payuResponse.getTransactionResponse().getState())
												  .withCreationDate(date)
												  .withMessage(payuResponse.getTransactionResponse().getResponseMessage())
												  .withOrder(order)
												  .build());

			} catch (JsonProcessingException e) {
				return Optional.of(buildResponseFromError(responseEntity.getStatusCode().toString(),
														  e.getMessage(), order));
			}
		} else {
			return Optional.of(buildResponseFromError(responseEntity.getStatusCode().toString(),
													  "Connection error", order));

		}
	}

	private PaymentResponse buildResponseFromError(String httpStatus, String message, Order order) {

		return PaymentResponse.builder()
										  .withStatus("REJECTED")
										  .withError(httpStatus)
										  .withMessage(message)
										  .withOrder(order)
										  .build();
	}

	private ResponseEntity<String> doPostPayU(PaymentAPIRequest apiRequest) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return client.doPost(apiRequest, headers, payuConfig.apiUrl);
	}
}
