package com.example.demo.controller;

import com.example.demo.dto.request.CreateReviewRequest;
import com.example.demo.dto.request.UpdateReviewRequest;
import com.example.demo.dto.response.CourseRatingResponse;
import com.example.demo.dto.response.ReviewResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.CourseReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Course Review", description = "Course review and rating APIs")
public class CourseReviewController {
	
	private final CourseReviewService courseReviewService;
	private final JwtTokenProvider tokenProvider;
	
	@PostMapping
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Create course review", description = "Create a review for a course (students only, must be enrolled)")
	public ResponseEntity<ReviewResponse> createReview(
			@Valid @RequestBody CreateReviewRequest request,
			HttpServletRequest httpRequest) {
		
		String token = tokenProvider.getTokenFromRequest(httpRequest);
		Long userId = tokenProvider.getUserIdFromToken(token);
		
		ReviewResponse review = courseReviewService.createReview(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(review);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Update course review", description = "Update your own review")
	public ResponseEntity<ReviewResponse> updateReview(
			@PathVariable Long id,
			@Valid @RequestBody UpdateReviewRequest request,
			HttpServletRequest httpRequest) {
		
		String token = tokenProvider.getTokenFromRequest(httpRequest);
		Long userId = tokenProvider.getUserIdFromToken(token);
		
		ReviewResponse review = courseReviewService.updateReview(id, userId, request);
		return ResponseEntity.ok(review);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Delete course review", description = "Delete your own review")
	public ResponseEntity<Void> deleteReview(
			@PathVariable Long id,
			HttpServletRequest httpRequest) {
		
		String token = tokenProvider.getTokenFromRequest(httpRequest);
		Long userId = tokenProvider.getUserIdFromToken(token);
		
		courseReviewService.deleteReview(id, userId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/course/{courseId}")
	@Operation(summary = "Get reviews by course", description = "Get all reviews for a specific course")
	public ResponseEntity<List<ReviewResponse>> getCourseReviews(
		@PathVariable Long courseId) { 
		List<ReviewResponse> reviews = courseReviewService.getCourseReviews(courseId);
		return ResponseEntity.ok(reviews);
	}
	
	@GetMapping("/course/{courseId}/rating")
	@Operation(summary = "Get course rating", description = "Get aggregate rating statistics for a course")
	public ResponseEntity<CourseRatingResponse> getCourseRating(
			@PathVariable Long courseId) {
		
		CourseRatingResponse rating = courseReviewService.getCourseRating(courseId);
		return ResponseEntity.ok(rating);
	}
	
	@GetMapping("/my-review/{courseId}")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get my review", description = "Get logged-in user's review for a specific course")
	public ResponseEntity<ReviewResponse> getMyReview(
			@PathVariable Long courseId,
			HttpServletRequest httpRequest) {
		
		String token = tokenProvider.getTokenFromRequest(httpRequest);
		Long userId = tokenProvider.getUserIdFromToken(token);
		
		ReviewResponse review = courseReviewService.getUserReview(userId, courseId);
		return ResponseEntity.ok(review);
	}
}
