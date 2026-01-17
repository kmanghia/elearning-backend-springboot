package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

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

	// Constructors
	public CreateQuizRequest() {
	}

	public CreateQuizRequest(String title, String description, Integer timeLimitMinutes, Integer passingScore,
			Integer maxAttempts, List<CreateQuestionRequest> questions) {
		this.title = title;
		this.description = description;
		this.timeLimitMinutes = timeLimitMinutes;
		this.passingScore = passingScore;
		this.maxAttempts = maxAttempts;
		this.questions = questions;
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

	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public Integer getPassingScore() {
		return passingScore;
	}

	public void setPassingScore(Integer passingScore) {
		this.passingScore = passingScore;
	}

	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public List<CreateQuestionRequest> getQuestions() {
		return questions;
	}

	public void setQuestions(List<CreateQuestionRequest> questions) {
		this.questions = questions;
	}
}
