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
public class QuizResponse {
	private Long id;
	private Long lessonId;
	private String title;
	private String description;
	private Integer timeLimitMinutes;
	private Integer passingScore;
	private Integer maxAttempts;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<QuestionResponse> questions;
}

