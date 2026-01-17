package com.example.demo.dto.response;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class ReviewResponse {

	private Long id;
	private Long studentId;
	private String studentName;
	private Long courseId;
	private String courseTitle;
	private Integer rating;
	@Nullable
	private String comment;
	private LocalDateTime createdAt;
	@Nullable
	private LocalDateTime updatedAt;

	public ReviewResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long studentId;
		private String studentName;
		private Long courseId;
		private String courseTitle;
		private Integer rating;
		private String comment;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder studentId(Long studentId) {
			this.studentId = studentId;
			return this;
		}

		public Builder studentName(String studentName) {
			this.studentName = studentName;
			return this;
		}

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
			return this;
		}

		public Builder rating(Integer rating) {
			this.rating = rating;
			return this;
		}

		public Builder comment(String comment) {
			this.comment = comment;
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

		public ReviewResponse build() {
			ReviewResponse response = new ReviewResponse();
			response.setId(id);
			response.setStudentId(studentId);
			response.setStudentName(studentName);
			response.setCourseId(courseId);
			response.setCourseTitle(courseTitle);
			response.setRating(rating);
			response.setComment(comment);
			response.setCreatedAt(createdAt);
			response.setUpdatedAt(updatedAt);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
}
