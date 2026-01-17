package com.example.demo.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

	private static final Logger log = LoggerFactory.getLogger(StripeConfig.class);

	@Value("${stripe.api.secret-key}")
	private String secretKey;

	@Value("${stripe.api.publishable-key}")
	private String publishableKey;

	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
		log.info("Stripe API initialized successfully");
		if (publishableKey != null && !publishableKey.isEmpty()) {
			log.info("Stripe publishable key configured: {}...",
					publishableKey.length() > 10 ? publishableKey.substring(0, 10) : "");
		}
	}

	public String getPublishableKey() {
		return publishableKey;
	}
}
