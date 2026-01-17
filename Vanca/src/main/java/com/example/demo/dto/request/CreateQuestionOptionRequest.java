package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateQuestionOptionRequest {
	@NotBlank(message = "Option content is required")
	private String content;

	@NotNull(message = "isCorrect flag is required")
	private Boolean isCorrect;

	@PositiveOrZero(message = "Order index must be positive or zero")
	private Integer orderIndex;
}
