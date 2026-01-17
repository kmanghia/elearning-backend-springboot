package com.example.demo.dto.response;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class CategoryResponse {

	private Long id;
	private String name;
	@Nullable
	private String description;
	private String slug;
	@Nullable
	private Long parentId;
	@Nullable
	private String parentName;
	@Nullable
	private String iconUrl;
	private Long subcategoryCount;
	private Long courseCount;
	private LocalDateTime createdAt;

	// Constructors
	public CategoryResponse() {
	}

	// Builder
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String name;
		private String description;
		private String slug;
		private Long parentId;
		private String parentName;
		private String iconUrl;
		private Long subcategoryCount;
		private Long courseCount;
		private LocalDateTime createdAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder slug(String slug) {
			this.slug = slug;
			return this;
		}

		public Builder parentId(Long parentId) {
			this.parentId = parentId;
			return this;
		}

		public Builder parentName(String parentName) {
			this.parentName = parentName;
			return this;
		}

		public Builder iconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
			return this;
		}

		public Builder subcategoryCount(Long subcategoryCount) {
			this.subcategoryCount = subcategoryCount;
			return this;
		}

		public Builder courseCount(Long courseCount) {
			this.courseCount = courseCount;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public CategoryResponse build() {
			CategoryResponse response = new CategoryResponse();
			response.setId(id);
			response.setName(name);
			response.setDescription(description);
			response.setSlug(slug);
			response.setParentId(parentId);
			response.setParentName(parentName);
			response.setIconUrl(iconUrl);
			response.setSubcategoryCount(subcategoryCount);
			response.setCourseCount(courseCount);
			response.setCreatedAt(createdAt);
			return response;
		}
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Long getSubcategoryCount() {
		return subcategoryCount;
	}

	public void setSubcategoryCount(Long subcategoryCount) {
		this.subcategoryCount = subcategoryCount;
	}

	public Long getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(Long courseCount) {
		this.courseCount = courseCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
