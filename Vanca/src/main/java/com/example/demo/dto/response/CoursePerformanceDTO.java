package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoursePerformanceDTO {
    private Long courseId;
    private String courseName;
    private Integer totalEnrollments;
    private Integer completedEnrollments;
    private Double completionRate;
    private Double averageRating;
    private Integer totalReviews;
    private Integer totalLessons;
    private Integer totalQuizzes;
    private Double averageQuizScore;
    private Double averageProgressPercentage;
}
