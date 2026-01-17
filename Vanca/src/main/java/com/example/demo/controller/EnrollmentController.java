package com.example.demo.controller;

import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

	private final EnrollmentService enrollmentService;

	@PostMapping("/courses/{courseId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<EnrollmentResponse> enrollInCourse(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		EnrollmentResponse enrollment = enrollmentService.enrollInCourse(courseId, userPrincipal.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
	}

	@DeleteMapping("/courses/{courseId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Void> unenrollFromCourse(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		enrollmentService.unenrollFromCourse(courseId, userPrincipal.getId());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/courses/{courseId}/check")
	@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<Boolean> checkEnrollment(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		boolean isEnrolled = enrollmentService.isEnrolled(courseId, userPrincipal.getId());
		return ResponseEntity.ok(isEnrolled);
	}

	@GetMapping("/my-enrollments")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Page<EnrollmentResponse>> getMyEnrollments(
		Pageable pageable,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		Page<EnrollmentResponse> enrollments = enrollmentService.getMyEnrollments(userPrincipal.getId(), pageable);
		return ResponseEntity.ok(enrollments);
	}
}

