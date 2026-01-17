package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateProgressRequest {
	@NotNull(message = "Watched duration is required")
	@PositiveOrZero(message = "Watched duration must be positive or zero")
	private Integer watchedDurationSeconds;

	// Constructors
	public UpdateProgressRequest() {
	}

	public UpdateProgressRequest(Integer watchedDurationSeconds) {
		this.watchedDurationSeconds = watchedDurationSeconds;
	}

	// Getters and Setters
	public Integer getWatchedDurationSeconds() {
		return watchedDurationSeconds;
	}

	public void setWatchedDurationSeconds(Integer watchedDurationSeconds) {
		this.watchedDurationSeconds = watchedDurationSeconds;
	}
}
