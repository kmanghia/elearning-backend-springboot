package com.example.demo.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentService {

	private static final Logger log = LoggerFactory.getLogger(StripePaymentService.class);

	@Value("${stripe.webhook.secret}")
	private String webhookSecret;

	/**
	 * Create a Stripe PaymentIntent
	 */
	public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, Long courseId, Long studentId)
			throws StripeException {
		// Convert amount to cents (Stripe expects smallest currency unit)
		long amountInCents = amount.multiply(new BigDecimal("100")).longValue();

		Map<String, String> metadata = new HashMap<>();
		metadata.put("courseId", courseId.toString());
		metadata.put("studentId", studentId.toString());

		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
				.setAmount(amountInCents)
				.setCurrency(currency)
				.putAllMetadata(metadata)
				.setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
								.setEnabled(true)
								.build())
				.build();

		PaymentIntent paymentIntent = PaymentIntent.create(params);
		log.info("Created Stripe PaymentIntent: {} for course: {} student: {}",
				paymentIntent.getId(), courseId, studentId);

		return paymentIntent;
	}

	/**
	 * Retrieve a PaymentIntent from Stripe
	 */
	public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
		return PaymentIntent.retrieve(paymentIntentId);
	}

	/**
	 * Construct and verify Stripe webhook event
	 */
	public Event constructWebhookEvent(String payload, String signature) throws StripeException {
		if (webhookSecret == null || webhookSecret.isEmpty()) {
			log.warn("Webhook secret not configured, skipping signature verification");
			return ApiResource.GSON.fromJson(payload, Event.class);
		}

		return Webhook.constructEvent(payload, signature, webhookSecret);
	}
}
