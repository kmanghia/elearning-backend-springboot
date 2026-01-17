package com.example.demo.dto.response;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

	private Long id;
	private String stripePaymentIntentId;
	private BigDecimal amount;
	private String currency;
	private String status;
	private Long courseId;
	private String courseName;
	private Long studentId;
	private String studentName;
	private LocalDateTime createdAt;
	@Nullable
	private Long enrollmentId;

	public PaymentResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String stripePaymentIntentId;
		private BigDecimal amount;
		private String currency;
		private String status;
		private Long courseId;
		private String courseName;
		private Long studentId;
		private String studentName;
		private LocalDateTime createdAt;
		private Long enrollmentId;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder stripePaymentIntentId(String stripePaymentIntentId) {
			this.stripePaymentIntentId = stripePaymentIntentId;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseName(String courseName) {
			this.courseName = courseName;
			return this;
		}

		public Builder studentId(Long studentId) {
			this.studentId = studentId;
			return this;
		}

		public Builder studentName(String studentName) {
			this.studentName = studentName;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder enrollmentId(Long enrollmentId) {
			this.enrollmentId = enrollmentId;
			return this;
		}

		public PaymentResponse build() {
			PaymentResponse response = new PaymentResponse();
			response.setId(id);
			response.setStripePaymentIntentId(stripePaymentIntentId);
			response.setAmount(amount);
			response.setCurrency(currency);
			response.setStatus(status);
			response.setCourseId(courseId);
			response.setCourseName(courseName);
			response.setStudentId(studentId);
			response.setStudentName(studentName);
			response.setCreatedAt(createdAt);
			response.setEnrollmentId(enrollmentId);
			return response;
		}
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
}
