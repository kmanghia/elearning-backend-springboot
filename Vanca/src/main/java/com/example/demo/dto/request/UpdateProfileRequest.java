package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {
	@NotBlank(message = "Full name is required")
	private String fullName;

	private String avatarUrl;

	// Constructors
	public UpdateProfileRequest() {
	}

	// Getters and Setters
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
