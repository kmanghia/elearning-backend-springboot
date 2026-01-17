package com.example.demo.dto.response;

import com.example.demo.model.Progress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressResponse {
	private Long courseId;
	private Integer totalLessons;
	private Integer completedLessons;
	private Integer courseProgress; // Percentage (0-100)
	private List<LessonProgressResponse> lessonProgress;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LessonProgressResponse {
		private Long lessonId;
		private String lessonTitle;
		private Integer watchedDurationSeconds;
		private Integer completionPercentage;
		private Boolean isCompleted;
	}
}
