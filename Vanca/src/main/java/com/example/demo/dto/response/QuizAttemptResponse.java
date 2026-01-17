package com.example.demo.dto.response;

import java.time.LocalDateTime;

public class QuizAttemptResponse {
	private Long id;
	private Long quizId;
	private LocalDateTime startedAt;
	private LocalDateTime submittedAt;
	private Integer score;
	private Boolean isPassed;
	private Integer totalQuestions;
	private Integer correctAnswers;

	public QuizAttemptResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long quizId;
		private LocalDateTime startedAt;
		private LocalDateTime submittedAt;
		private Integer score;
		private Boolean isPassed;
		private Integer totalQuestions;
		private Integer correctAnswers;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder quizId(Long quizId) {
			this.quizId = quizId;
			return this;
		}

		public Builder startedAt(LocalDateTime startedAt) {
			this.startedAt = startedAt;
			return this;
		}

		public Builder submittedAt(LocalDateTime submittedAt) {
			this.submittedAt = submittedAt;
			return this;
		}

		public Builder score(Integer score) {
			this.score = score;
			return this;
		}

		public Builder isPassed(Boolean isPassed) {
			this.isPassed = isPassed;
			return this;
		}

		public Builder totalQuestions(Integer totalQuestions) {
			this.totalQuestions = totalQuestions;
			return this;
		}

		public Builder correctAnswers(Integer correctAnswers) {
			this.correctAnswers = correctAnswers;
			return this;
		}

		public QuizAttemptResponse build() {
			QuizAttemptResponse response = new QuizAttemptResponse();
			response.setId(id);
			response.setQuizId(quizId);
			response.setStartedAt(startedAt);
			response.setSubmittedAt(submittedAt);
			response.setScore(score);
			response.setIsPassed(isPassed);
			response.setTotalQuestions(totalQuestions);
			response.setCorrectAnswers(correctAnswers);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}

	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public Integer getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
}
