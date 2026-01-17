package com.example.demo.dto.request;

import com.example.demo.model.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

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

	// Constructors
	public CreateQuestionRequest() {
	}

	public CreateQuestionRequest(String content, Question.QuestionType questionType, Integer points,
			Integer orderIndex, List<CreateQuestionOptionRequest> options) {
		this.content = content;
		this.questionType = questionType;
		this.points = points;
		this.orderIndex = orderIndex;
		this.options = options;
	}

	// Getters and Setters
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Question.QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Question.QuestionType questionType) {
		this.questionType = questionType;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<CreateQuestionOptionRequest> getOptions() {
		return options;
	}

	public void setOptions(List<CreateQuestionOptionRequest> options) {
		this.options = options;
	}
}
