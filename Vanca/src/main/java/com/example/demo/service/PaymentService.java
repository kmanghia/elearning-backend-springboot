package com.example.demo.service;

import com.example.demo.dto.response.PaymentIntentResponse;
import com.example.demo.dto.response.PaymentResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.model.Notification.NotificationType;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	private final PaymentRepository paymentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final StripePaymentService stripePaymentService;
	private final NotificationService notificationService;

	public PaymentService(PaymentRepository paymentRepository, CourseRepository courseRepository,
			UserRepository userRepository, EnrollmentRepository enrollmentRepository,
			StripePaymentService stripePaymentService, NotificationService notificationService) {
		this.paymentRepository = paymentRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.stripePaymentService = stripePaymentService;
		this.notificationService = notificationService;
	}

	/**
	 * Create a payment intent for a course
	 */
	@Transactional
	public PaymentIntentResponse createPaymentIntent(Long courseId, Long studentId) {
		// Validate course exists
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		// Validate course is published
		if (course.getStatus() != Course.Status.PUBLISHED) {
			throw new BadRequestException("Course is not published");
		}

		// Validate course has a price
		if (course.getPrice() == null || course.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new BadRequestException("This is a free course. No payment required.");
		}

		// Validate student exists
		User student = userRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		// Check if student is already enrolled
		if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
			throw new BadRequestException("You are already enrolled in this course");
		}

		// Check if there's already a pending/successful payment
		paymentRepository.findByCourseIdAndStudentId(courseId, studentId)
				.ifPresent(existingPayment -> {
					if (existingPayment.getStatus() == Payment.PaymentStatus.SUCCEEDED) {
						throw new BadRequestException("You have already paid for this course");
					}
					if (existingPayment.getStatus() == Payment.PaymentStatus.PENDING) {
						throw new BadRequestException("You have a pending payment for this course");
					}
				});

		// Prevent instructor from buying their own course
		if (course.getInstructor() != null && course.getInstructor().getId().equals(studentId)) {
			throw new BadRequestException("Instructors cannot purchase their own courses");
		}

		try {
			// Create Stripe PaymentIntent
			String currency = "usd";
			PaymentIntent paymentIntent = stripePaymentService.createPaymentIntent(
					course.getPrice(), currency, courseId, studentId);

			// Save payment record in our database
			Payment payment = new Payment();
			payment.setStripePaymentIntentId(paymentIntent.getId());
			payment.setAmount(course.getPrice());
			payment.setCurrency(currency);
			payment.setStatus(Payment.PaymentStatus.PENDING);
			payment.setStudent(student);
			payment.setCourse(course);

			paymentRepository.save(payment);

			log.info("Created payment intent for student: {} course: {} amount: {}",
					studentId, courseId, course.getPrice());

			return PaymentIntentResponse.builder()
					.clientSecret(paymentIntent.getClientSecret())
					.paymentIntentId(paymentIntent.getId())
					.amount(course.getPrice())
					.currency(currency)
					.courseId(courseId)
					.courseName(course.getTitle())
					.build();

		} catch (StripeException e) {
			log.error("Stripe error creating payment intent: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to create payment intent: " + e.getMessage());
		}
	}

	/**
	 * Handle successful payment - create enrollment
	 */
	@Transactional
	public void handlePaymentSuccess(String paymentIntentId) {
		Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found for intent: " + paymentIntentId));

		// Update payment status
		payment.setStatus(Payment.PaymentStatus.SUCCEEDED);
		paymentRepository.save(payment);

		// Check if enrollment already exists
		if (payment.getEnrollment() != null) {
			log.warn("Enrollment already exists for payment: {}", paymentIntentId);
			return;
		}

		// Create enrollment
		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(payment.getStudent());
		enrollment.setCourse(payment.getCourse());
		enrollment.setPayment(payment);
		enrollment.setProgress(0);

		enrollmentRepository.save(enrollment);

		log.info("Created enrollment for payment: {} student: {} course: {}",
				paymentIntentId, payment.getStudent().getId(), payment.getCourse().getId());

		// Send notification to student
		notificationService.createNotification(
				payment.getStudent().getId(),
				"Payment Successful",
				"Your payment for \"" + payment.getCourse().getTitle() + "\" was successful. You are now enrolled!",
				NotificationType.ENROLLMENT,
				"COURSE",
				payment.getCourse().getId());
	}

	/**
	 * Handle failed payment
	 */
	@Transactional
	public void handlePaymentFailure(String paymentIntentId) {
		Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found for intent: " + paymentIntentId));

		payment.setStatus(Payment.PaymentStatus.FAILED);
		paymentRepository.save(payment);

		log.info("Payment failed for intent: {} student: {} course: {}",
				paymentIntentId, payment.getStudent().getId(), payment.getCourse().getId());

		// Send notification to student
		notificationService.createNotification(
				payment.getStudent().getId(),
				"Payment Failed",
				"Your payment for \"" + payment.getCourse().getTitle() + "\" failed. Please try again.",
				NotificationType.SYSTEM,
				"COURSE",
				payment.getCourse().getId());
	}

	/**
	 * Get payment history for a student
	 */
	public Page<PaymentResponse> getPaymentHistory(Long studentId, Pageable pageable) {
		Page<Payment> payments = paymentRepository.findByStudentIdOrderByCreatedAtDesc(studentId, pageable);
		return payments.map(this::mapToResponse);
	}

	/**
	 * Get payment by intent ID
	 */
	public Payment getPaymentByIntentId(String paymentIntentId) {
		return paymentRepository.findByStripePaymentIntentId(paymentIntentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
	}

	/**
	 * Map Payment to PaymentResponse
	 */
	private PaymentResponse mapToResponse(Payment payment) {
		return PaymentResponse.builder()
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
	}
}
