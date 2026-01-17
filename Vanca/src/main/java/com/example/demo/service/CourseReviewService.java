package com.example.demo.service;

import com.example.demo.dto.request.CreateReviewRequest;
import com.example.demo.dto.request.UpdateReviewRequest;
import com.example.demo.dto.response.CourseRatingResponse;
import com.example.demo.dto.response.ReviewResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Course;
import com.example.demo.model.CourseReview;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseReviewRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseReviewService {
	
	private final CourseReviewRepository courseReviewRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	
	@Transactional
	public ReviewResponse createReview(Long userId, CreateReviewRequest request) {
		User student = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Course course = courseRepository.findById(request.getCourseId())
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));
		
		// Check if student is enrolled in the course
		if (!enrollmentRepository.existsByStudentIdAndCourseId(userId, request.getCourseId())) {
			throw new IllegalStateException("You must be enrolled in the course to leave a review");
		}
		
		// Check if student already reviewed this course
		if (courseReviewRepository.existsByStudentIdAndCourseId(userId, request.getCourseId())) {
			throw new IllegalStateException("You have already reviewed this course. Use update instead.");
		}
		
		// Create review
		CourseReview review = new CourseReview();
		review.setStudent(student);
		review.setCourse(course);
		review.setRating(request.getRating());
		review.setComment(request.getComment());
		
		CourseReview savedReview = courseReviewRepository.save(review);
		
		return mapToResponse(savedReview);
	}
	
	@Transactional
	public ReviewResponse updateReview(Long reviewId, Long userId, UpdateReviewRequest request) {
		CourseReview review = courseReviewRepository.findById(reviewId)
			.orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		
		// Check if user is the owner of the review
		if (!review.getStudent().getId().equals(userId)) {
			throw new UnauthorizedException("You can only update your own reviews");
		}
		
		review.setRating(request.getRating());
		review.setComment(request.getComment());
		review.setUpdatedAt(LocalDateTime.now());
		
		CourseReview updatedReview = courseReviewRepository.save(review);
		
		return mapToResponse(updatedReview);
	}
	
	@Transactional
	public void deleteReview(Long reviewId, Long userId) {
		CourseReview review = courseReviewRepository.findById(reviewId)
			.orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		
		// Check if user is the owner of the review
		if (!review.getStudent().getId().equals(userId)) {
			throw new UnauthorizedException("You can only delete your own reviews");
		}
		
		courseReviewRepository.delete(review);
	}
	
	public List<ReviewResponse> getCourseReviews(Long courseId) {
		List<CourseReview> reviews = courseReviewRepository.findByCourseId(courseId);
		return reviews.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	public CourseRatingResponse getCourseRating(Long courseId) {
		// Verify course exists
		courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));
		
		Double averageRating = courseReviewRepository.getAverageRatingByCourseId(courseId);
		Long totalReviews = courseReviewRepository.getReviewCountByCourseId(courseId);
		
		// Get all reviews to calculate distribution
		List<CourseReview> reviews = courseReviewRepository.findByCourseId(courseId);
		Map<Integer, Long> ratingDistribution = new HashMap<>();
		
		// Initialize distribution with 0 for all ratings
		for (int i = 1; i <= 5; i++) {
			ratingDistribution.put(i, 0L);
		}
		
		// Count reviews by rating
		reviews.forEach(review -> {
			Integer rating = review.getRating();
			ratingDistribution.put(rating, ratingDistribution.get(rating) + 1);
		});
		
		return CourseRatingResponse.builder()
			.courseId(courseId)
			.averageRating(averageRating != null ? averageRating : 0.0)
			.totalReviews(totalReviews != null ? totalReviews : 0L)
			.ratingDistribution(ratingDistribution)
			.build();
	}
	
	public ReviewResponse getUserReview(Long userId, Long courseId) {
		CourseReview review = courseReviewRepository.findByStudentIdAndCourseId(userId, courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		
		return mapToResponse(review);
	}
	
	private ReviewResponse mapToResponse(CourseReview review) {
		return ReviewResponse.builder()
			.id(review.getId())
			.studentId(review.getStudent().getId())
			.studentName(review.getStudent().getFullName())
			.courseId(review.getCourse().getId())
			.courseTitle(review.getCourse().getTitle())
			.rating(review.getRating())
			.comment(review.getComment())
			.createdAt(review.getCreatedAt())
			.updatedAt(review.getUpdatedAt())
			.build();
	}
}
