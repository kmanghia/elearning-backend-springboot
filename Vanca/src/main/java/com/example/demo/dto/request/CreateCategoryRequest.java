package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoryRequest {

	@NotBlank(message = "Category name is required")
	private String name;

	private String description;

	@NotBlank(message = "Slug is required")
	private String slug;

	private Long parentId; // Optional for hierarchical categories

	private String iconUrl;

	// Constructors
	public CreateCategoryRequest() {
	}

	public CreateCategoryRequest(String name, String description, String slug, Long parentId, String iconUrl) {
		this.name = name;
		this.description = description;
		this.slug = slug;
		this.parentId = parentId;
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
