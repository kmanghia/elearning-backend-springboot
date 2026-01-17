package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEngagementMetrics {
    private Long courseId;
    private String courseName;
    private Integer totalStudents;
    private Integer activeStudents; // Students who made progress in last 7 days
    private Integer completedStudents;
    private Double completionRate; // Percentage
    private Double averageProgress; // Average progress percentage
    private Integer totalQuizAttempts;
    private Double averageQuizScore;
}
