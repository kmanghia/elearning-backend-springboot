package com.example.demo.dto.response;

import java.math.BigDecimal;

public class DiscountCalculationResponse {
	private BigDecimal originalPrice;
	private BigDecimal discountAmount;
	private BigDecimal finalPrice;
	private String couponCode;
	private Boolean isValid;
	private String message;

	public DiscountCalculationResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private BigDecimal originalPrice;
		private BigDecimal discountAmount;
		private BigDecimal finalPrice;
		private String couponCode;
		private Boolean isValid;
		private String message;

		public Builder originalPrice(BigDecimal originalPrice) {
			this.originalPrice = originalPrice;
			return this;
		}

		public Builder discountAmount(BigDecimal discountAmount) {
			this.discountAmount = discountAmount;
			return this;
		}

		public Builder finalPrice(BigDecimal finalPrice) {
			this.finalPrice = finalPrice;
			return this;
		}

		public Builder couponCode(String couponCode) {
			this.couponCode = couponCode;
			return this;
		}

		public Builder isValid(Boolean isValid) {
			this.isValid = isValid;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public DiscountCalculationResponse build() {
			DiscountCalculationResponse response = new DiscountCalculationResponse();
			response.setOriginalPrice(originalPrice);
			response.setDiscountAmount(discountAmount);
			response.setFinalPrice(finalPrice);
			response.setCouponCode(couponCode);
			response.setIsValid(isValid);
			response.setMessage(message);
			return response;
		}
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
