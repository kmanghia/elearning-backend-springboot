package com.example.demo.dto.request;

import jakarta.validation.constraints.Size;

public class UpdatePostRequest {

	@Size(min = 5, max = 500, message = "Title must be between 5 and 500 characters")
	private String title;

	@Size(min = 10, message = "Content must be at least 10 characters")
	private String content;

	private Boolean isResolved;

	// Constructors
	public UpdatePostRequest() {
	}

	public UpdatePostRequest(String title, String content, Boolean isResolved) {
		this.title = title;
		this.content = content;
		this.isResolved = isResolved;
	}

	// Getters and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}
}
