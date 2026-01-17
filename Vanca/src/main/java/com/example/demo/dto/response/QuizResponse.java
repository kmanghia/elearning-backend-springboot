package com.example.demo.dto.response;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public class QuizResponse {
	private Long id;
	private Long lessonId;
	private String title;
	@Nullable
	private String description;
	@Nullable
	private Integer timeLimitMinutes;
	@Nullable
	private Integer passingScore;
	@Nullable
	private Integer maxAttempts;
	private LocalDateTime createdAt;
	@Nullable
	private LocalDateTime updatedAt;
	@Nullable
	private List<QuestionResponse> questions;

	public QuizResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
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

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder lessonId(Long lessonId) {
			this.lessonId = lessonId;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder timeLimitMinutes(Integer timeLimitMinutes) {
			this.timeLimitMinutes = timeLimitMinutes;
			return this;
		}

		public Builder passingScore(Integer passingScore) {
			this.passingScore = passingScore;
			return this;
		}

		public Builder maxAttempts(Integer maxAttempts) {
			this.maxAttempts = maxAttempts;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder updatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public Builder questions(List<QuestionResponse> questions) {
			this.questions = questions;
			return this;
		}

		public QuizResponse build() {
			QuizResponse response = new QuizResponse();
			response.setId(id);
			response.setLessonId(lessonId);
			response.setTitle(title);
			response.setDescription(description);
			response.setTimeLimitMinutes(timeLimitMinutes);
			response.setPassingScore(passingScore);
			response.setMaxAttempts(maxAttempts);
			response.setCreatedAt(createdAt);
			response.setUpdatedAt(updatedAt);
			response.setQuestions(questions);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public Integer getPassingScore() {
		return passingScore;
	}

	public void setPassingScore(Integer passingScore) {
		this.passingScore = passingScore;
	}

	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<QuestionResponse> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResponse> questions) {
		this.questions = questions;
	}
}
