package com.ecommerce.prototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "application.properties")
public class PrototypeApplication {

	public static void main(String[] args) {

		SpringApplication.run(PrototypeApplication.class, args);
	}

}
