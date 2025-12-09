package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponse {
	private Long courseId;
	private String courseTitle;
	private Integer totalLessons;
	private Integer completedLessons;
	private Integer courseProgress; // Percentage
	private LocalDateTime lastAccessedAt;
	private List<LessonProgressResponse> lessonProgress;
}

