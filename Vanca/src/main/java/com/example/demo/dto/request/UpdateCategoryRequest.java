package com.example.demo.dto.request;

public class UpdateCategoryRequest {

	private String name;

	private String description;

	private String iconUrl;

	// Constructors
	public UpdateCategoryRequest() {
	}

	public UpdateCategoryRequest(String name, String description, String iconUrl) {
		this.name = name;
		this.description = description;
		this.iconUrl = iconUrl;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
