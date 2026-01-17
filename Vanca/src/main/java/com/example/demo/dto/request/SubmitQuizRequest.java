package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubmitQuizRequest {
	@Valid
	@NotEmpty(message = "Answers list cannot be empty")
	private List<QuizAnswerSubmission> answers;

	// Constructors
	public SubmitQuizRequest() {
	}

	public SubmitQuizRequest(List<QuizAnswerSubmission> answers) {
		this.answers = answers;
	}

	// Getters and Setters
	public List<QuizAnswerSubmission> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuizAnswerSubmission> answers) {
		this.answers = answers;
	}

	// Inner class
	public static class QuizAnswerSubmission {
		@NotNull(message = "Question ID is required")
		private Long questionId;

		private Set<Long> selectedOptionIds = new HashSet<>();

		// Constructors
		public QuizAnswerSubmission() {
		}

		public QuizAnswerSubmission(Long questionId, Set<Long> selectedOptionIds) {
			this.questionId = questionId;
			this.selectedOptionIds = selectedOptionIds;
		}

		// Getters and Setters
		public Long getQuestionId() {
			return questionId;
		}

		public void setQuestionId(Long questionId) {
			this.questionId = questionId;
		}

		public Set<Long> getSelectedOptionIds() {
			return selectedOptionIds;
		}

		public void setSelectedOptionIds(Set<Long> selectedOptionIds) {
			this.selectedOptionIds = selectedOptionIds;
		}
	}
}
