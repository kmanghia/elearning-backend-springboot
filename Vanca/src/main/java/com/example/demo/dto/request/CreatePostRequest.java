package com.example.demo.dto.request;

import com.example.demo.model.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
	
	@NotNull(message = "Course ID is required")
	private Long courseId;
	
	@NotBlank(message = "Title is required")
	@Size(min = 5, max = 500, message = "Title must be between 5 and 500 characters")
	private String title;
	
	@NotBlank(message = "Content is required")
	@Size(min = 10, message = "Content must be at least 10 characters")
	private String content;
	
	@NotNull(message = "Post type is required")
	private Post.PostType type;
}
