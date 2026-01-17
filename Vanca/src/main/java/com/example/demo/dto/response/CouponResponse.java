package com.example.demo.dto.response;

import com.example.demo.model.Coupon;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponResponse {
	private Long id;
	private String code;
	private Coupon.DiscountType discountType;
	private BigDecimal discountValue;
	@Nullable
	private BigDecimal minPurchaseAmount;
	@Nullable
	private BigDecimal maxDiscountAmount;
	@Nullable
	private Integer maxUses;
	private Integer usedCount;
	@Nullable
	private LocalDateTime expirationDate;
	private Boolean active;
	private LocalDateTime createdAt;

	// Constructors
	public CouponResponse() {
	}

	// Builder
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String code;
		private Coupon.DiscountType discountType;
		private BigDecimal discountValue;
		private BigDecimal minPurchaseAmount;
		private BigDecimal maxDiscountAmount;
		private Integer maxUses;
		private Integer usedCount;
		private LocalDateTime expirationDate;
		private Boolean active;
		private LocalDateTime createdAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public Builder discountType(Coupon.DiscountType discountType) {
			this.discountType = discountType;
			return this;
		}

		public Builder discountValue(BigDecimal discountValue) {
			this.discountValue = discountValue;
			return this;
		}

		public Builder minPurchaseAmount(BigDecimal minPurchaseAmount) {
			this.minPurchaseAmount = minPurchaseAmount;
			return this;
		}

		public Builder maxDiscountAmount(BigDecimal maxDiscountAmount) {
			this.maxDiscountAmount = maxDiscountAmount;
			return this;
		}

		public Builder maxUses(Integer maxUses) {
			this.maxUses = maxUses;
			return this;
		}

		public Builder usedCount(Integer usedCount) {
			this.usedCount = usedCount;
			return this;
		}

		public Builder expirationDate(LocalDateTime expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public Builder active(Boolean active) {
			this.active = active;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public CouponResponse build() {
			CouponResponse response = new CouponResponse();
			response.setId(id);
			response.setCode(code);
			response.setDiscountType(discountType);
			response.setDiscountValue(discountValue);
			response.setMinPurchaseAmount(minPurchaseAmount);
			response.setMaxDiscountAmount(maxDiscountAmount);
			response.setMaxUses(maxUses);
			response.setUsedCount(usedCount);
			response.setExpirationDate(expirationDate);
			response.setActive(active);
			response.setCreatedAt(createdAt);
			return response;
		}
	}

	// Getters only (Response DTOs typically don't need setters)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
