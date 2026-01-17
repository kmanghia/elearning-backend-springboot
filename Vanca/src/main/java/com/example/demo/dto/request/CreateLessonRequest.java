package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateLessonRequest {
	@NotBlank(message = "Title is required")
	private String title;

	private String description;

	private String videoUrl;

	@PositiveOrZero(message = "Duration must be positive or zero")
	private Integer durationSeconds;

	private Boolean isPreview = false;

	@PositiveOrZero(message = "Order index must be positive or zero")
	private Integer orderIndex;

	// Constructors
	public CreateLessonRequest() {
	}

	public CreateLessonRequest(String title, String description, String videoUrl, Integer durationSeconds,
			Boolean isPreview, Integer orderIndex) {
		this.title = title;
		this.description = description;
		this.videoUrl = videoUrl;
		this.durationSeconds = durationSeconds;
		this.isPreview = isPreview;
		this.orderIndex = orderIndex;
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}
