package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateQuestionOptionRequest {
	@NotBlank(message = "Option content is required")
	private String content;

	@NotNull(message = "isCorrect flag is required")
	private Boolean isCorrect;

	@PositiveOrZero(message = "Order index must be positive or zero")
	private Integer orderIndex;

	// Constructors
	public CreateQuestionOptionRequest() {
	}

	public CreateQuestionOptionRequest(String content, Boolean isCorrect, Integer orderIndex) {
		this.content = content;
		this.isCorrect = isCorrect;
		this.orderIndex = orderIndex;
	}

	// Getters and Setters
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}
