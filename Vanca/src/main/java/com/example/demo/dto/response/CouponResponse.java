package com.example.demo.dto.response;

import com.example.demo.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
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
}
