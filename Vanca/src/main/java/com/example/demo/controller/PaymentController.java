package com.example.demo.controller;

import com.example.demo.dto.request.CreatePaymentIntentRequest;
import com.example.demo.dto.response.PaymentIntentResponse;
import com.example.demo.dto.response.PaymentResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.PaymentService;
import com.example.demo.service.StripePaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	private final PaymentService paymentService;
	private final StripePaymentService stripePaymentService;

	public PaymentController(PaymentService paymentService, StripePaymentService stripePaymentService) {
		this.paymentService = paymentService;
		this.stripePaymentService = stripePaymentService;
	}

	/**
	 * Create a payment intent for a course
	 */
	@PostMapping("/create-intent/{courseId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
			@PathVariable Long courseId,
			@RequestBody(required = false) CreatePaymentIntentRequest request,
			@AuthenticationPrincipal UserPrincipal userPrincipal) {

		PaymentIntentResponse response = paymentService.createPaymentIntent(courseId, userPrincipal.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Stripe webhook endpoint
	 * This endpoint is called by Stripe to notify us of payment events
	 */
	@PostMapping("/webhook")
	public ResponseEntity<String> handleWebhook(
			@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String signature) {

		try {
			Event event = stripePaymentService.constructWebhookEvent(payload, signature);

			log.info("Received Stripe webhook event: {}", event.getType());

			// Handle the event
			switch (event.getType()) {
				case "payment_intent.succeeded":
					PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
							.getObject().orElse(null);
					if (paymentIntent != null) {
						paymentService.handlePaymentSuccess(paymentIntent.getId());
					}
					break;

				case "payment_intent.payment_failed":
					PaymentIntent failedIntent = (PaymentIntent) event.getDataObjectDeserializer()
							.getObject().orElse(null);
					if (failedIntent != null) {
						paymentService.handlePaymentFailure(failedIntent.getId());
					}
					break;

				case "payment_intent.canceled":
					PaymentIntent canceledIntent = (PaymentIntent) event.getDataObjectDeserializer()
							.getObject().orElse(null);
					if (canceledIntent != null) {
						log.info("Payment intent canceled: {}", canceledIntent.getId());
					}
					break;

				default:
					log.info("Unhandled event type: {}", event.getType());
			}

			return ResponseEntity.ok("Webhook received");

		} catch (SignatureVerificationException e) {
			log.error("Invalid webhook signature: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
		} catch (StripeException e) {
			log.error("Stripe error processing webhook: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook processing failed");
		} catch (Exception e) {
			log.error("Error processing webhook: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook processing failed");
		}
	}

	/**
	 * Get authenticated user's payment history
	 */
	@GetMapping("/my-payments")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Page<PaymentResponse>> getMyPayments(
			Pageable pageable,
			@AuthenticationPrincipal UserPrincipal userPrincipal) {

		Page<PaymentResponse> payments = paymentService.getPaymentHistory(userPrincipal.getId(), pageable);
		return ResponseEntity.ok(payments);
	}

	/**
	 * Get payment details by payment intent ID
	 */
	@GetMapping("/{paymentIntentId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<PaymentResponse> getPaymentByIntentId(
			@PathVariable String paymentIntentId,
			@AuthenticationPrincipal UserPrincipal userPrincipal) {

		var payment = paymentService.getPaymentByIntentId(paymentIntentId);

		// Ensure the payment belongs to the authenticated user
		if (!payment.getStudent().getId().equals(userPrincipal.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		PaymentResponse response = PaymentResponse.builder()
				.id(payment.getId())
				.stripePaymentIntentId(payment.getStripePaymentIntentId())
				.amount(payment.getAmount())
				.currency(payment.getCurrency())
				.status(payment.getStatus().name())
				.courseId(payment.getCourse().getId())
				.courseName(payment.getCourse().getTitle())
				.studentId(payment.getStudent().getId())
				.studentName(payment.getStudent().getFullName())
				.createdAt(payment.getCreatedAt())
				.enrollmentId(payment.getEnrollment() != null ? payment.getEnrollment().getId() : null)
				.build();

		return ResponseEntity.ok(response);
	}
}
