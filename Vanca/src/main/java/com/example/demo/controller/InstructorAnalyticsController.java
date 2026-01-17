package com.example.demo.controller;

import com.example.demo.dto.response.CoursePerformanceDTO;
import com.example.demo.dto.response.InstructorDashboardResponse;
import com.example.demo.dto.response.StudentEngagementMetrics;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.InstructorAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor/analytics")
@Tag(name = "Instructor Analytics", description = "Analytics dashboard for instructors")
public class InstructorAnalyticsController {
    
    private final InstructorAnalyticsService analyticsService;


	public InstructorAnalyticsController(InstructorAnalyticsService analyticsService) {
		this.analyticsService = analyticsService;
	}
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @Operation(summary = "Get comprehensive instructor dashboard with all metrics")
    public ResponseEntity<InstructorDashboardResponse> getInstructorDashboard(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        InstructorDashboardResponse dashboard = 
                analyticsService.getInstructorDashboard(userPrincipal.getId());
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/courses/{courseId}/performance")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @Operation(summary = "Get performance metrics for a specific course")
    public ResponseEntity<CoursePerformanceDTO> getCoursePerformance(@PathVariable Long courseId) {
        CoursePerformanceDTO performance = analyticsService.getCoursePerformance(courseId);
        return ResponseEntity.ok(performance);
    }
    
    @GetMapping("/courses/{courseId}/engagement")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @Operation(summary = "Get student engagement metrics for a course")
    public ResponseEntity<StudentEngagementMetrics> getStudentEngagement(@PathVariable Long courseId) {
        StudentEngagementMetrics engagement = analyticsService.getStudentEngagement(courseId);
        return ResponseEntity.ok(engagement);
    }
}
