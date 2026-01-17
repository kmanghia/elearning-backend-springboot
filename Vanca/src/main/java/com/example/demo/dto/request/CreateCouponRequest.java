package com.example.demo.dto.request;

import com.example.demo.model.Coupon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
