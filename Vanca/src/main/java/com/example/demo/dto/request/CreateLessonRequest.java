package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
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
}
