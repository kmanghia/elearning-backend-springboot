package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String code;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DiscountType discountType;

	@Column(nullable = false)
	private BigDecimal discountValue;

	@Column(name = "min_purchase_amount")
	private BigDecimal minPurchaseAmount;

	@Column(name = "max_discount_amount")
	private BigDecimal maxDiscountAmount;

	@Column(name = "max_uses")
	private Integer maxUses;

	@Column(name = "used_count", nullable = false)
	private Integer usedCount = 0;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	@Column(nullable = false)
	private Boolean active = true;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public enum DiscountType {
		PERCENTAGE,  // e.g., 20% off
		FIXED_AMOUNT // e.g., $10 off
	}
}
