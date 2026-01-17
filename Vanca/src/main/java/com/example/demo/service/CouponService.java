package com.example.demo.service;

import com.example.demo.dto.request.CreateCouponRequest;
import com.example.demo.dto.response.CouponResponse;
import com.example.demo.dto.response.DiscountCalculationResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	@Transactional
	public CouponResponse createCoupon(CreateCouponRequest request) {
		// Check if code already exists
		if (couponRepository.existsByCode(request.getCode())) {
			throw new BadRequestException("Coupon code already exists");
		}

		// Validate discount value
		if (request.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
			if (request.getDiscountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
				throw new BadRequestException("Percentage discount cannot exceed 100%");
			}
		}

		Coupon coupon = new Coupon();
		coupon.setCode(request.getCode().toUpperCase());
		coupon.setDiscountType(request.getDiscountType());
		coupon.setDiscountValue(request.getDiscountValue());
		coupon.setMinPurchaseAmount(request.getMinPurchaseAmount());
		coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
		coupon.setMaxUses(request.getMaxUses());
		coupon.setExpirationDate(request.getExpirationDate());
		coupon.setActive(request.getActive() != null ? request.getActive() : true);

		Coupon savedCoupon = couponRepository.save(coupon);
		return mapToResponse(savedCoupon);
	}

	public CouponResponse getCouponByCode(String code) {
		Coupon coupon = couponRepository.findByCode(code.toUpperCase())
			.orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
		return mapToResponse(coupon);
	}

	public List<CouponResponse> getAllCoupons() {
		return couponRepository.findAll().stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}

	@Transactional
	public CouponResponse updateCoupon(Long id, CreateCouponRequest request) {
		Coupon coupon = couponRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

		// Check if new code conflicts with existing
		if (!coupon.getCode().equalsIgnoreCase(request.getCode())) {
			if (couponRepository.existsByCode(request.getCode())) {
				throw new BadRequestException("Coupon code already exists");
			}
			coupon.setCode(request.getCode().toUpperCase());
		}

		coupon.setDiscountType(request.getDiscountType());
		coupon.setDiscountValue(request.getDiscountValue());
		coupon.setMinPurchaseAmount(request.getMinPurchaseAmount());
		coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
		coupon.setMaxUses(request.getMaxUses());
		coupon.setExpirationDate(request.getExpirationDate());
		coupon.setActive(request.getActive());

		Coupon updatedCoupon = couponRepository.save(coupon);
		return mapToResponse(updatedCoupon);
	}

	@Transactional
	public void deleteCoupon(Long id) {
		Coupon coupon = couponRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
		couponRepository.delete(coupon);
	}

	public DiscountCalculationResponse calculateDiscount(String code, BigDecimal originalPrice) {
		Coupon coupon = couponRepository.findByCodeAndActiveTrue(code.toUpperCase())
			.orElse(null);

		if (coupon == null) {
			return DiscountCalculationResponse.builder()
				.originalPrice(originalPrice)
				.discountAmount(BigDecimal.ZERO)
				.finalPrice(originalPrice)
				.couponCode(code)
				.isValid(false)
				.message("Coupon code is invalid or inactive")
				.build();
		}

		// Validate expiration
		if (coupon.getExpirationDate() != null && LocalDateTime.now().isAfter(coupon.getExpirationDate())) {
			return DiscountCalculationResponse.builder()
				.originalPrice(originalPrice)
				.discountAmount(BigDecimal.ZERO)
				.finalPrice(originalPrice)
				.couponCode(code)
				.isValid(false)
				.message("Coupon has expired")
				.build();
		}

		// Validate max uses
		if (coupon.getMaxUses() != null && coupon.getUsedCount() >= coupon.getMaxUses()) {
			return DiscountCalculationResponse.builder()
				.originalPrice(originalPrice)
				.discountAmount(BigDecimal.ZERO)
				.finalPrice(originalPrice)
				.couponCode(code)
				.isValid(false)
				.message("Coupon usage limit reached")
				.build();
		}

		// Validate minimum purchase
		if (coupon.getMinPurchaseAmount() != null && originalPrice.compareTo(coupon.getMinPurchaseAmount()) < 0) {
			return DiscountCalculationResponse.builder()
				.originalPrice(originalPrice)
				.discountAmount(BigDecimal.ZERO)
				.finalPrice(originalPrice)
				.couponCode(code)
				.isValid(false)
				.message("Minimum purchase amount not met")
				.build();
		}

		// Calculate discount
		BigDecimal discountAmount;
		if (coupon.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
			discountAmount = originalPrice.multiply(coupon.getDiscountValue())
				.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		} else {
			discountAmount = coupon.getDiscountValue();
		}

		// Apply max discount cap if set
		if (coupon.getMaxDiscountAmount() != null && discountAmount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
			discountAmount = coupon.getMaxDiscountAmount();
		}

		// Ensure discount doesn't exceed original price
		if (discountAmount.compareTo(originalPrice) > 0) {
			discountAmount = originalPrice;
		}

		BigDecimal finalPrice = originalPrice.subtract(discountAmount);

		return DiscountCalculationResponse.builder()
			.originalPrice(originalPrice)
			.discountAmount(discountAmount)
			.finalPrice(finalPrice)
			.couponCode(code)
			.isValid(true)
			.message("Discount applied successfully")
			.build();
	}

	@Transactional
	public void incrementUsage(String code) {
		Coupon coupon = couponRepository.findByCode(code.toUpperCase())
			.orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
		coupon.setUsedCount(coupon.getUsedCount() + 1);
		couponRepository.save(coupon);
	}

	private CouponResponse mapToResponse(Coupon coupon) {
		return CouponResponse.builder()
			.id(coupon.getId())
			.code(coupon.getCode())
			.discountType(coupon.getDiscountType())
			.discountValue(coupon.getDiscountValue())
			.minPurchaseAmount(coupon.getMinPurchaseAmount())
			.maxDiscountAmount(coupon.getMaxDiscountAmount())
			.maxUses(coupon.getMaxUses())
			.usedCount(coupon.getUsedCount())
			.expirationDate(coupon.getExpirationDate())
			.active(coupon.getActive())
			.createdAt(coupon.getCreatedAt())
			.build();
	}
}
