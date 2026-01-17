package com.example.demo.dto.response;

import java.util.List;

public class CourseProgressResponse {
	private Long courseId;
	private Integer totalLessons;
	private Integer completedLessons;
	private Integer courseProgress; // Percentage (0-100)
	private List<LessonProgressResponse> lessonProgress;

	public CourseProgressResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long courseId;
		private Integer totalLessons;
		private Integer completedLessons;
		private Integer courseProgress;
		private List<LessonProgressResponse> lessonProgress;

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
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

		public Builder lessonProgress(List<LessonProgressResponse> lessonProgress) {
			this.lessonProgress = lessonProgress;
			return this;
		}

		public CourseProgressResponse build() {
			CourseProgressResponse response = new CourseProgressResponse();
			response.setCourseId(courseId);
			response.setTotalLessons(totalLessons);
			response.setCompletedLessons(completedLessons);
			response.setCourseProgress(courseProgress);
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

	public List<LessonProgressResponse> getLessonProgress() {
		return lessonProgress;
	}

	public void setLessonProgress(List<LessonProgressResponse> lessonProgress) {
		this.lessonProgress = lessonProgress;
	}

	// Nested class LessonProgressResponse removed - using standalone class instead
}
