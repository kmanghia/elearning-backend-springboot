package com.example.demo.dto.request;

import com.example.demo.model.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class CreateCourseRequest {
	@NotBlank(message = "Title is required")
	private String title;

	private String description;

	@NotNull(message = "Price is required")
	@PositiveOrZero(message = "Price must be positive or zero")
	private BigDecimal price;

	private String thumbnailUrl;

	private Long categoryId;

	private Course.Status status = Course.Status.DRAFT;

	// Constructors
	public CreateCourseRequest() {
	}

	public CreateCourseRequest(String title, String description, BigDecimal price, String thumbnailUrl,
			Long categoryId, Course.Status status) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.thumbnailUrl = thumbnailUrl;
		this.categoryId = categoryId;
		this.status = status;
	}

	// Getters and Setters
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Course.Status getStatus() {
		return status;
	}

	public void setStatus(Course.Status status) {
		this.status = status;
	}
}
