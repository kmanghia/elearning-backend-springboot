package com.example.demo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateReviewRequest {

	@NotNull(message = "Course ID is required")
	private Long courseId;

	@NotNull(message = "Rating is required")
	@Min(value = 1, message = "Rating must be at least 1")
	@Max(value = 5, message = "Rating must be at most 5")
	private Integer rating;

	private String comment;

	// Constructors
	public CreateReviewRequest() {
	}

	public CreateReviewRequest(Long courseId, Integer rating, String comment) {
		this.courseId = courseId;
		this.rating = rating;
		this.comment = comment;
	}

	// Getters and Setters
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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
}
