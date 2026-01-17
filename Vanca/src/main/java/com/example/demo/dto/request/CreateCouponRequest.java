package com.example.demo.dto.request;

import com.example.demo.model.Coupon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateCouponRequest {

	@NotBlank(message = "Coupon code is required")
	private String code;

	@NotNull(message = "Discount type is required")
	private Coupon.DiscountType discountType;

	@NotNull(message = "Discount value is required")
	@Positive(message = "Discount value must be positive")
	private BigDecimal discountValue;

	private BigDecimal minPurchaseAmount;

	private BigDecimal maxDiscountAmount;

	private Integer maxUses;

	private LocalDateTime expirationDate;

	private Boolean active = true;

	// Constructors
	public CreateCouponRequest() {
	}

	public CreateCouponRequest(String code, Coupon.DiscountType discountType, BigDecimal discountValue,
			BigDecimal minPurchaseAmount, BigDecimal maxDiscountAmount, Integer maxUses,
			LocalDateTime expirationDate, Boolean active) {
		this.code = code;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.minPurchaseAmount = minPurchaseAmount;
		this.maxDiscountAmount = maxDiscountAmount;
		this.maxUses = maxUses;
		this.expirationDate = expirationDate;
		this.active = active;
	}

	// Getters and Setters
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Coupon.DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Coupon.DiscountType discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public BigDecimal getMinPurchaseAmount() {
		return minPurchaseAmount;
	}

	public void setMinPurchaseAmount(BigDecimal minPurchaseAmount) {
		this.minPurchaseAmount = minPurchaseAmount;
	}

	public BigDecimal getMaxDiscountAmount() {
		return maxDiscountAmount;
	}

	public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) {
		this.maxDiscountAmount = maxDiscountAmount;
	}

	public Integer getMaxUses() {
		return maxUses;
	}

	public void setMaxUses(Integer maxUses) {
		this.maxUses = maxUses;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
