package com.example.demo.dto.response;

import com.example.demo.model.Course;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseResponse {
	private Long id;
	private String title;
	@Nullable
	private String description;
	private Long instructorId;
	private String instructorName;
	private BigDecimal price;
	@Nullable
	private String thumbnailUrl;
	private Course.Status status;
	private LocalDateTime createdAt;
	@Nullable
	private LocalDateTime updatedAt;
	@Nullable
	private Long categoryId;
	@Nullable
	private String categoryName;
	@Nullable
	private Integer lessonCount;
	@Nullable
	private Integer enrolledStudentCount;

	public CourseResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String title;
		private String description;
		private Long instructorId;
		private String instructorName;
		private BigDecimal price;
		private String thumbnailUrl;
		private Course.Status status;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private Long categoryId;
		private String categoryName;
		private Integer lessonCount;
		private Integer enrolledStudentCount;

		public Builder id(Long id) {
			this.id = id;
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

		public Builder instructorId(Long instructorId) {
			this.instructorId = instructorId;
			return this;
		}

		public Builder instructorName(String instructorName) {
			this.instructorName = instructorName;
			return this;
		}

		public Builder price(BigDecimal price) {
			this.price = price;
			return this;
		}

		public Builder thumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
			return this;
		}

		public Builder status(Course.Status status) {
			this.status = status;
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

		public Builder categoryId(Long categoryId) {
			this.categoryId = categoryId;
			return this;
		}

		public Builder categoryName(String categoryName) {
			this.categoryName = categoryName;
			return this;
		}

		public Builder lessonCount(Integer lessonCount) {
			this.lessonCount = lessonCount;
			return this;
		}

		public Builder enrolledStudentCount(Integer enrolledStudentCount) {
			this.enrolledStudentCount = enrolledStudentCount;
			return this;
		}

		public CourseResponse build() {
			CourseResponse response = new CourseResponse();
			response.setId(id);
			response.setTitle(title);
			response.setDescription(description);
			response.setInstructorId(instructorId);
			response.setInstructorName(instructorName);
			response.setPrice(price);
			response.setThumbnailUrl(thumbnailUrl);
			response.setStatus(status);
			response.setCreatedAt(createdAt);
			response.setUpdatedAt(updatedAt);
			response.setCategoryId(categoryId);
			response.setCategoryName(categoryName);
			response.setLessonCount(lessonCount);
			response.setEnrolledStudentCount(enrolledStudentCount);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Course.Status getStatus() {
		return status;
	}

	public void setStatus(Course.Status status) {
		this.status = status;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(Integer lessonCount) {
		this.lessonCount = lessonCount;
	}

	public Integer getEnrolledStudentCount() {
		return enrolledStudentCount;
	}

	public void setEnrolledStudentCount(Integer enrolledStudentCount) {
		this.enrolledStudentCount = enrolledStudentCount;
	}
}
