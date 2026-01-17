package com.example.demo.controller;

import com.example.demo.dto.response.RecommendationResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Recommendations", description = "Course recommendation endpoints")
public class RecommendationController {
    
    private final RecommendationService recommendationService;


	public RecommendationController(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}
    
    @GetMapping("/personalized")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Get personalized course recommendations for the authenticated user")
    public ResponseEntity<RecommendationResponse> getPersonalizedRecommendations(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "10") int limit) {
        RecommendationResponse recommendations = 
                recommendationService.getPersonalizedRecommendations(userPrincipal.getId(), limit);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/popular")
    @Operation(summary = "Get popular courses based on enrollment count and ratings")
    public ResponseEntity<RecommendationResponse> getPopularCourses(
            @RequestParam(defaultValue = "10") int limit) {
        RecommendationResponse recommendations = 
                recommendationService.getPopularCourses(limit);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/similar/{courseId}")
    @Operation(summary = "Get courses similar to a specific course")
    public ResponseEntity<RecommendationResponse> getSimilarCourses(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "10") int limit) {
        RecommendationResponse recommendations = 
                recommendationService.getSimilarCourses(courseId, limit);
        return ResponseEntity.ok(recommendations);
    }
}
