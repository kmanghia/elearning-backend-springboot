package com.example.demo.dto.response;

import java.time.LocalDateTime;

public class LessonProgressResponse {
	private Long lessonId;
	private String lessonTitle;
	private Integer watchedDurationSeconds;
	private Integer totalDurationSeconds;
	private Integer completionPercentage;
	private Boolean isCompleted;
	private LocalDateTime lastWatchedAt;

	public LessonProgressResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long lessonId;
		private String lessonTitle;
		private Integer watchedDurationSeconds;
		private Integer totalDurationSeconds;
		private Integer completionPercentage;
		private Boolean isCompleted;
		private LocalDateTime lastWatchedAt;

		public Builder lessonId(Long lessonId) {
			this.lessonId = lessonId;
			return this;
		}

		public Builder lessonTitle(String lessonTitle) {
			this.lessonTitle = lessonTitle;
			return this;
		}

		public Builder watchedDurationSeconds(Integer watchedDurationSeconds) {
			this.watchedDurationSeconds = watchedDurationSeconds;
			return this;
		}

		public Builder totalDurationSeconds(Integer totalDurationSeconds) {
			this.totalDurationSeconds = totalDurationSeconds;
			return this;
		}

		public Builder completionPercentage(Integer completionPercentage) {
			this.completionPercentage = completionPercentage;
			return this;
		}

		public Builder isCompleted(Boolean isCompleted) {
			this.isCompleted = isCompleted;
			return this;
		}

		public Builder lastWatchedAt(LocalDateTime lastWatchedAt) {
			this.lastWatchedAt = lastWatchedAt;
			return this;
		}

		public LessonProgressResponse build() {
			LessonProgressResponse response = new LessonProgressResponse();
			response.setLessonId(lessonId);
			response.setLessonTitle(lessonTitle);
			response.setWatchedDurationSeconds(watchedDurationSeconds);
			response.setTotalDurationSeconds(totalDurationSeconds);
			response.setCompletionPercentage(completionPercentage);
			response.setIsCompleted(isCompleted);
			response.setLastWatchedAt(lastWatchedAt);
			return response;
		}
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public String getLessonTitle() {
		return lessonTitle;
	}

	public void setLessonTitle(String lessonTitle) {
		this.lessonTitle = lessonTitle;
	}

	public Integer getWatchedDurationSeconds() {
		return watchedDurationSeconds;
	}

	public void setWatchedDurationSeconds(Integer watchedDurationSeconds) {
		this.watchedDurationSeconds = watchedDurationSeconds;
	}

	public Integer getTotalDurationSeconds() {
		return totalDurationSeconds;
	}

	public void setTotalDurationSeconds(Integer totalDurationSeconds) {
		this.totalDurationSeconds = totalDurationSeconds;
	}

	public Integer getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(Integer completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public LocalDateTime getLastWatchedAt() {
		return lastWatchedAt;
	}

	public void setLastWatchedAt(LocalDateTime lastWatchedAt) {
		this.lastWatchedAt = lastWatchedAt;
	}
}
