package com.example.demo.controller;

import com.example.demo.dto.request.CreateCouponRequest;
import com.example.demo.dto.response.CouponResponse;
import com.example.demo.dto.response.DiscountCalculationResponse;
import com.example.demo.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@Tag(name = "Coupon", description = "Coupon and discount management APIs")
public class CouponController {

	private final CouponService couponService;

	public CouponController(CouponService couponService) {
		this.couponService = couponService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Create coupon", description = "Create a new coupon code (admin only)")
	public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CreateCouponRequest request) {
		CouponResponse response = couponService.createCoupon(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get all coupons", description = "Get all coupons (admin only)")
	public ResponseEntity<List<CouponResponse>> getAllCoupons() {
		List<CouponResponse> coupons = couponService.getAllCoupons();
		return ResponseEntity.ok(coupons);
	}

	@GetMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get coupon by code", description = "Get coupon details by code (admin only)")
	public ResponseEntity<CouponResponse> getCouponByCode(@PathVariable String code) {
		CouponResponse response = couponService.getCouponByCode(code);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Update coupon", description = "Update coupon details (admin only)")
	public ResponseEntity<CouponResponse> updateCoupon(
			@PathVariable Long id,
			@Valid @RequestBody CreateCouponRequest request) {
		CouponResponse response = couponService.updateCoupon(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Delete coupon", description = "Delete a coupon (admin only)")
	public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
		couponService.deleteCoupon(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/validate/{code}")
	@Operation(summary = "Validate coupon", description = "Check if coupon is valid and calculate discount")
	public ResponseEntity<DiscountCalculationResponse> validateCoupon(
			@PathVariable String code,
			@RequestParam BigDecimal price) {
		DiscountCalculationResponse response = couponService.calculateDiscount(code, price);
		return ResponseEntity.ok(response);
	}
}
