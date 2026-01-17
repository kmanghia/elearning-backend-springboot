package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
	
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
}
