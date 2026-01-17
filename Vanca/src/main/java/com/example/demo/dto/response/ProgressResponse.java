package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ProgressResponse {
	private Long courseId;
	private String courseTitle;
	private Integer totalLessons;
	private Integer completedLessons;
	private Integer courseProgress; // Percentage
	private LocalDateTime lastAccessedAt;
	private List<LessonProgressResponse> lessonProgress;

	public ProgressResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long courseId;
		private String courseTitle;
		private Integer totalLessons;
		private Integer completedLessons;
		private Integer courseProgress;
		private LocalDateTime lastAccessedAt;
		private List<LessonProgressResponse> lessonProgress;

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
			return this;
		}

		public Builder totalLessons(Integer totalLessons) {
			this.totalLessons = totalLessons;
			return this;
		}

		public Builder completedLessons(Integer completedLessons) {
			this.completedLessons = completedLessons;
			return this;
		}

		public Builder courseProgress(Integer courseProgress) {
			this.courseProgress = courseProgress;
			return this;
		}

		public Builder lastAccessedAt(LocalDateTime lastAccessedAt) {
			this.lastAccessedAt = lastAccessedAt;
			return this;
		}

		public Builder lessonProgress(List<LessonProgressResponse> lessonProgress) {
			this.lessonProgress = lessonProgress;
			return this;
		}

		public ProgressResponse build() {
			ProgressResponse response = new ProgressResponse();
			response.setCourseId(courseId);
			response.setCourseTitle(courseTitle);
			response.setTotalLessons(totalLessons);
			response.setCompletedLessons(completedLessons);
			response.setCourseProgress(courseProgress);
			response.setLastAccessedAt(lastAccessedAt);
			response.setLessonProgress(lessonProgress);
			return response;
		}
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public Integer getTotalLessons() {
		return totalLessons;
	}

	public void setTotalLessons(Integer totalLessons) {
		this.totalLessons = totalLessons;
	}

	public Integer getCompletedLessons() {
		return completedLessons;
	}

	public void setCompletedLessons(Integer completedLessons) {
		this.completedLessons = completedLessons;
	}

	public Integer getCourseProgress() {
		return courseProgress;
	}

	public void setCourseProgress(Integer courseProgress) {
		this.courseProgress = courseProgress;
	}

	public LocalDateTime getLastAccessedAt() {
		return lastAccessedAt;
	}

	public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
		this.lastAccessedAt = lastAccessedAt;
	}

	public List<LessonProgressResponse> getLessonProgress() {
		return lessonProgress;
	}

	public void setLessonProgress(List<LessonProgressResponse> lessonProgress) {
		this.lessonProgress = lessonProgress;
	}
}
