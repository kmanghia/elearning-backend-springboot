package com.example.demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WishlistResponse {
	private Long id;
	private Long courseId;
	private String courseTitle;
	private String courseDescription;
	private String courseThumbnailUrl;
	private BigDecimal coursePrice;
	private String instructorName;
	private LocalDateTime addedAt;
	private Boolean isEnrolled;

	public WishlistResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long courseId;
		private String courseTitle;
		private String courseDescription;
		private String courseThumbnailUrl;
		private BigDecimal coursePrice;
		private String instructorName;
		private LocalDateTime addedAt;
		private Boolean isEnrolled;

		public Builder id(Long id) {
			this.id = id;
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

		public Builder courseDescription(String courseDescription) {
			this.courseDescription = courseDescription;
			return this;
		}

		public Builder courseThumbnailUrl(String courseThumbnailUrl) {
			this.courseThumbnailUrl = courseThumbnailUrl;
			return this;
		}

		public Builder coursePrice(BigDecimal coursePrice) {
			this.coursePrice = coursePrice;
			return this;
		}

		public Builder instructorName(String instructorName) {
			this.instructorName = instructorName;
			return this;
		}

		public Builder addedAt(LocalDateTime addedAt) {
			this.addedAt = addedAt;
			return this;
		}

		public Builder isEnrolled(Boolean isEnrolled) {
			this.isEnrolled = isEnrolled;
			return this;
		}

		public WishlistResponse build() {
			WishlistResponse response = new WishlistResponse();
			response.setId(id);
			response.setCourseId(courseId);
			response.setCourseTitle(courseTitle);
			response.setCourseDescription(courseDescription);
			response.setCourseThumbnailUrl(courseThumbnailUrl);
			response.setCoursePrice(coursePrice);
			response.setInstructorName(instructorName);
			response.setAddedAt(addedAt);
			response.setIsEnrolled(isEnrolled);
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

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseThumbnailUrl() {
		return courseThumbnailUrl;
	}

	public void setCourseThumbnailUrl(String courseThumbnailUrl) {
		this.courseThumbnailUrl = courseThumbnailUrl;
	}

	public BigDecimal getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(BigDecimal coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public Boolean getIsEnrolled() {
		return isEnrolled;
	}

	public void setIsEnrolled(Boolean isEnrolled) {
		this.isEnrolled = isEnrolled;
	}
}
