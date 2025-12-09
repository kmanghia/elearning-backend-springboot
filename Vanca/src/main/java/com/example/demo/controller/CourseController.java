package com.example.demo.controller;

import com.example.demo.dto.request.CreateCourseRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

	private final CourseService courseService;

	@GetMapping
	public ResponseEntity<List<CourseResponse>> getAllPublishedCourses() {
		return ResponseEntity.ok(courseService.getAllPublishedCourses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
		return ResponseEntity.ok(courseService.getCourseById(id));
	}

	@GetMapping("/instructor/{instructorId}")
	public ResponseEntity<List<CourseResponse>> getCoursesByInstructor(@PathVariable Long instructorId) {
		return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<CourseResponse> createCourse(
		@Valid @RequestBody CreateCourseRequest request,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CourseResponse course = courseService.createCourse(request, userPrincipal.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(course);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<CourseResponse> updateCourse(
		@PathVariable Long id,
		@Valid @RequestBody CreateCourseRequest request,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok(courseService.updateCourse(id, request, userPrincipal.getId()));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<Void> deleteCourse(
		@PathVariable Long id,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		courseService.deleteCourse(id, userPrincipal.getId());
		return ResponseEntity.noContent().build();
	}
}

