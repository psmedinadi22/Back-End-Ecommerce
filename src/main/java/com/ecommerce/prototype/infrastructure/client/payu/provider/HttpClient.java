package com.ecommerce.prototype.infrastructure.client.payu.provider;

import com.ecommerce.prototype.infrastructure.client.payu.request.PaymentAPIRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

	ResponseEntity<String> doPost(Object request, HttpHeaders headers, String url ) {

		HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
		return new RestTemplate().exchange(
				url,
				HttpMethod.POST,
				requestEntity,
				String.class);
	}

}
