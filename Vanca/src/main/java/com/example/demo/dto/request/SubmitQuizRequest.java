package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class SubmitQuizRequest {
	@Valid
	@NotEmpty(message = "Answers list cannot be empty")
	private List<QuizAnswerSubmission> answers;

	@Data
	public static class QuizAnswerSubmission {
		@NotNull(message = "Question ID is required")
		private Long questionId;

		private Set<Long> selectedOptionIds = new HashSet<>();
	}
}
