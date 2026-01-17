package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnalyticsResponse {
	private Long studentId;
	private String studentName;
	
	// Overall stats
	private Integer totalEnrolledCourses;
	private Integer completedCourses;
	private Double overallCompletionRate;
	
	// Quiz performance
	private Integer totalQuizzes;
	private Double averageQuizScore;
	private Integer passedQuizzes;
	private Integer failedQuizzes;
	
	// Learning activity
	private Integer totalLessonsCompleted;
	private Map<String, Integer> learningStreak; // Days active in last week/month
	
	// Course progress breakdown
	private List<CourseProgressSummary> courseProgressList;
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CourseProgressSummary {
		private Long courseId;
		private String courseTitle;
		private Double completionPercentage;
		private Integer completedLessons;
		private Integer totalLessons;
		private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED
	}
}
