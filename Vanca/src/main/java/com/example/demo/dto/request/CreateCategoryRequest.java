package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
	
	@NotBlank(message = "Category name is required")
	private String name;
	
	private String description;
	
	@NotBlank(message = "Slug is required")
	private String slug;
	
	private Long parentId; // Optional for hierarchical categories
	
	private String iconUrl;
}
