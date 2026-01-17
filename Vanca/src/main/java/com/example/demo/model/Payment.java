package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stripe_payment_intent_id", unique = true, nullable = false)
	private String stripePaymentIntentId;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false, length = 3)
	private String currency = "usd";

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status = PaymentStatus.PENDING;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
	private Enrollment enrollment;

	@Column(columnDefinition = "TEXT")
	private String metadata;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Constructors
	public Payment() {
	}

	public Payment(Long id, String stripePaymentIntentId, BigDecimal amount, String currency,
			PaymentStatus status, User student, Course course, Enrollment enrollment,
			String metadata, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.stripePaymentIntentId = stripePaymentIntentId;
		this.amount = amount;
		this.currency = currency;
		this.status = status;
		this.student = student;
		this.course = course;
		this.enrollment = enrollment;
		this.metadata = metadata;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStripePaymentIntentId() {
		return stripePaymentIntentId;
	}

	public void setStripePaymentIntentId(String stripePaymentIntentId) {
		this.stripePaymentIntentId = stripePaymentIntentId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public enum PaymentStatus {
		PENDING,
		SUCCEEDED,
		FAILED,
		CANCELED
	}
}
