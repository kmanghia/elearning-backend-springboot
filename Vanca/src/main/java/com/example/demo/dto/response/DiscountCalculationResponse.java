package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCalculationResponse {
	private BigDecimal originalPrice;
	private BigDecimal discountAmount;
	private BigDecimal finalPrice;
	private String couponCode;
	private Boolean isValid;
	private String message;
}
