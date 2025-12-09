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
public class QuizAttemptResponse {
	private Long id;
	private Long quizId;
	private LocalDateTime startedAt;
	private LocalDateTime submittedAt;
	private Integer score;
	private Boolean isPassed;
	private Integer totalQuestions;
	private Integer correctAnswers;
}

