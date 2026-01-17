package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuizRequest {
	@NotBlank(message = "Quiz title is required")
	private String title;

	private String description;

	@PositiveOrZero(message = "Time limit must be positive or zero")
	private Integer timeLimitMinutes;

	@NotNull(message = "Passing score is required")
	@Positive(message = "Passing score must be positive")
	private Integer passingScore = 60;

	@Positive(message = "Max attempts must be positive")
	private Integer maxAttempts;

	@Valid
	private List<CreateQuestionRequest> questions = new ArrayList<>();
}
