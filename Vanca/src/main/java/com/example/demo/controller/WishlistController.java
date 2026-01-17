package com.example.demo.controller;

import com.example.demo.dto.response.WishlistResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Course wishlist/favorites management APIs")
public class WishlistController {

	private final WishlistService wishlistService;


	public WishlistController(WishlistService wishlistService) {
		this.wishlistService = wishlistService;
	}

	@PostMapping("/courses/{courseId}")
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Add course to wishlist", description = "Add a course to user's wishlist/favorites")
	public ResponseEntity<WishlistResponse> addToWishlist(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		WishlistResponse response = wishlistService.addToWishlist(userPrincipal.getId(), courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/courses/{courseId}")
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Remove from wishlist", description = "Remove a course from user's wishlist")
	public ResponseEntity<Void> removeFromWishlist(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		wishlistService.removeFromWishlist(userPrincipal.getId(), courseId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get my wishlist", description = "Get all courses in user's wishlist with pagination")
	public ResponseEntity<Page<WishlistResponse>> getMyWishlist(
		Pageable pageable,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		Page<WishlistResponse> wishlist = wishlistService.getMyWishlist(userPrincipal.getId(), pageable);
		return ResponseEntity.ok(wishlist);
	}

	@GetMapping("/courses/{courseId}/check")
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Check if in wishlist", description = "Check if a course is in user's wishlist")
	public ResponseEntity<Boolean> checkWishlist(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		boolean inWishlist = wishlistService.isInWishlist(userPrincipal.getId(), courseId);
		return ResponseEntity.ok(inWishlist);
	}
}
