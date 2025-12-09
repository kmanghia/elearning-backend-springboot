package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProgressRequest {
	@NotNull(message = "Watched duration is required")
	@PositiveOrZero(message = "Watched duration must be positive or zero")
	private Integer watchedDurationSeconds;
}

