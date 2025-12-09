package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressResponse {
	private Long lessonId;
	private String lessonTitle;
	private Integer watchedDurationSeconds;
	private Integer totalDurationSeconds;
	private Integer completionPercentage;
	private Boolean isCompleted;
	private LocalDateTime lastWatchedAt;
}

