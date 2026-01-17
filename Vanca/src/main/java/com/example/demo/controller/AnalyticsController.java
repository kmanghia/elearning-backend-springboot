package com.example.demo.controller;

import com.example.demo.dto.response.StudentAnalyticsResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Student learning analytics and metrics APIs")
public class AnalyticsController {

	private final AnalyticsService analyticsService;

	@GetMapping("/my-analytics")
	@PreAuthorize("hasRole('STUDENT')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get my analytics", description = "Get comprehensive learning analytics for logged-in student")
	public ResponseEntity<StudentAnalyticsResponse> getMyAnalytics(
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		StudentAnalyticsResponse analytics = analyticsService.getStudentAnalytics(userPrincipal.getId());
		return ResponseEntity.ok(analytics);
	}

	@GetMapping("/students/{studentId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get student analytics", description = "Get learning analytics for a specific student (admin/instructor only)")
	public ResponseEntity<StudentAnalyticsResponse> getStudentAnalytics(@PathVariable Long studentId) {
		StudentAnalyticsResponse analytics = analyticsService.getStudentAnalytics(studentId);
		return ResponseEntity.ok(analytics);
	}
}
