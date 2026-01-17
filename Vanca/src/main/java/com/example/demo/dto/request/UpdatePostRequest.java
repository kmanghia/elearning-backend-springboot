package com.example.demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {
	
	@Size(min = 5, max = 500, message = "Title must be between 5 and 500 characters")
	private String title;
	
	@Size(min = 10, message = "Content must be at least 10 characters")
	private String content;
	
	private Boolean isResolved;
}
