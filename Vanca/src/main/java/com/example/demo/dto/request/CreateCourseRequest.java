package com.example.demo.dto.request;

import com.example.demo.model.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
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
}

