package com.example.demo.service;

import com.example.demo.dto.response.WishlistResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.model.Wishlist;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

	private final WishlistRepository wishlistRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;


	public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
		this.wishlistRepository = wishlistRepository;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.enrollmentRepository = enrollmentRepository;
	}

	@Transactional
	public WishlistResponse addToWishlist(Long userId, Long courseId) {
		// Verify user exists
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Verify course exists
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		// Check if already in wishlist
		if (wishlistRepository.existsByUserIdAndCourseId(userId, courseId)) {
			throw new BadRequestException("Course already in wishlist");
		}

		// Check if already enrolled
		if (enrollmentRepository.existsByStudentIdAndCourseId(userId, courseId)) {
			throw new BadRequestException("Cannot add enrolled course to wishlist");
		}

		// Create wishlist entry
		Wishlist wishlist = new Wishlist();
		wishlist.setUser(user);
		wishlist.setCourse(course);
		
		Wishlist savedWishlist = wishlistRepository.save(wishlist);
		return mapToResponse(savedWishlist);
	}

	@Transactional
	public void removeFromWishlist(Long userId, Long courseId) {
		// Verify wishlist item exists
		if (!wishlistRepository.existsByUserIdAndCourseId(userId, courseId)) {
			throw new ResourceNotFoundException("Course not in wishlist");
		}

		wishlistRepository.deleteByUserIdAndCourseId(userId, courseId);
	}

	public Page<WishlistResponse> getMyWishlist(Long userId, Pageable pageable) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Page<Wishlist> wishlists = wishlistRepository.findByUserId(userId, pageable);
		return wishlists.map(this::mapToResponse);
	}

	public boolean isInWishlist(Long userId, Long courseId) {
		return wishlistRepository.existsByUserIdAndCourseId(userId, courseId);
	}

	public Long getWishlistCountForCourse(Long courseId) {
		return wishlistRepository.countByCourseId(courseId);
	}

	private WishlistResponse mapToResponse(Wishlist wishlist) {
		Course course = wishlist.getCourse();
		boolean isEnrolled = enrollmentRepository.existsByStudentIdAndCourseId(
			wishlist.getUser().getId(), course.getId());

		return WishlistResponse.builder()
			.id(wishlist.getId())
			.courseId(course.getId())
			.courseTitle(course.getTitle())
			.courseDescription(course.getDescription())
			.courseThumbnailUrl(course.getThumbnailUrl())
			.coursePrice(course.getPrice())
			.instructorName(course.getInstructor().getFullName())
			.addedAt(wishlist.getAddedAt())
			.isEnrolled(isEnrolled)
			.build();
	}
}
