package com.example.demo.dto.response;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class LessonResponse {
	private Long id;
	private Long courseId;
	private String title;
	@Nullable
	private String description;
	private Integer orderIndex;
	@Nullable
	private String videoUrl; // S3 URL or pre-signed URL
	@Nullable
	private Integer durationSeconds;
	private Boolean isPreview;
	private LocalDateTime createdAt;
	@Nullable
	private LocalDateTime updatedAt;
	@Nullable
	private Boolean hasQuiz;

	public LessonResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long courseId;
		private String title;
		private String description;
		private Integer orderIndex;
		private String videoUrl;
		private Integer durationSeconds;
		private Boolean isPreview;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private Boolean hasQuiz;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
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

		public Builder orderIndex(Integer orderIndex) {
			this.orderIndex = orderIndex;
			return this;
		}

		public Builder videoUrl(String videoUrl) {
			this.videoUrl = videoUrl;
			return this;
		}

		public Builder durationSeconds(Integer durationSeconds) {
			this.durationSeconds = durationSeconds;
			return this;
		}

		public Builder isPreview(Boolean isPreview) {
			this.isPreview = isPreview;
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

		public Builder hasQuiz(Boolean hasQuiz) {
			this.hasQuiz = hasQuiz;
			return this;
		}

		public LessonResponse build() {
			LessonResponse response = new LessonResponse();
			response.setId(id);
			response.setCourseId(courseId);
			response.setTitle(title);
			response.setDescription(description);
			response.setOrderIndex(orderIndex);
			response.setVideoUrl(videoUrl);
			response.setDurationSeconds(durationSeconds);
			response.setIsPreview(isPreview);
			response.setCreatedAt(createdAt);
			response.setUpdatedAt(updatedAt);
			response.setHasQuiz(hasQuiz);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(Integer durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	public Boolean getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(Boolean isPreview) {
		this.isPreview = isPreview;
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

	public Boolean getHasQuiz() {
		return hasQuiz;
	}

	public void setHasQuiz(Boolean hasQuiz) {
		this.hasQuiz = hasQuiz;
	}
}
