package com.example.demo.dto.response;

import java.math.BigDecimal;

public class PaymentIntentResponse {

	private String clientSecret;
	private String paymentIntentId;
	private BigDecimal amount;
	private String currency;
	private Long courseId;
	private String courseName;

	public PaymentIntentResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String clientSecret;
		private String paymentIntentId;
		private BigDecimal amount;
		private String currency;
		private Long courseId;
		private String courseName;

		public Builder clientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		public Builder paymentIntentId(String paymentIntentId) {
			this.paymentIntentId = paymentIntentId;
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

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseName(String courseName) {
			this.courseName = courseName;
			return this;
		}

		public PaymentIntentResponse build() {
			PaymentIntentResponse response = new PaymentIntentResponse();
			response.setClientSecret(clientSecret);
			response.setPaymentIntentId(paymentIntentId);
			response.setAmount(amount);
			response.setCurrency(currency);
			response.setCourseId(courseId);
			response.setCourseName(courseName);
			return response;
		}
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getPaymentIntentId() {
		return paymentIntentId;
	}

	public void setPaymentIntentId(String paymentIntentId) {
		this.paymentIntentId = paymentIntentId;
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
}
