package com.example.demo.dto.request;

import com.example.demo.model.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuestionRequest {
	@NotBlank(message = "Question content is required")
	private String content;

	@NotNull(message = "Question type is required")
	private Question.QuestionType questionType;

	@Positive(message = "Points must be positive")
	private Integer points = 1;

	@PositiveOrZero(message = "Order index must be positive or zero")
	private Integer orderIndex;

	@Valid
	private List<CreateQuestionOptionRequest> options = new ArrayList<>();
}
