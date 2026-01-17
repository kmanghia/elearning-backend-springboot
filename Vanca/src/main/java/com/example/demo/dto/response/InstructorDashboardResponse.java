package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDashboardResponse {
    private Long instructorId;
    private String instructorName;
    
    // Overall statistics
    private Integer totalCourses;
    private Integer publishedCourses;
    private Integer totalStudents; // Unique students across all courses
    private Integer totalEnrollments;
    private Double overallAverageRating;
    private Integer totalReviews;
    
    // Engagement summary
    private Integer activeStudents; // Active in last 7 days
    private Double averageCompletionRate;
    private Integer totalQuizAttempts;
    
    // Course-specific metrics
    private List<CoursePerformanceDTO> coursePerformances;
    
    // Recent activity
    private Integer newEnrollmentsThisWeek;
    private Integer newReviewsThisWeek;
}
