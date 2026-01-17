package com.example.demo.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateCommentRequest {

	@Size(min = 5, message = "Content must be at least 5 characters")
	private String content;

	// Constructors
	public UpdateCommentRequest() {
	}

	public UpdateCommentRequest(String content) {
		this.content = content;
	}

	// Getters and Setters
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
