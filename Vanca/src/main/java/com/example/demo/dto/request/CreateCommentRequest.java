package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
	
	@NotNull(message = "Post ID is required")
	private Long postId;
	
	@NotBlank(message = "Content is required")
	@Size(min = 5, message = "Content must be at least 5 characters")
	private String content;
	
	private Long parentCommentId; // Optional for threaded replies
}
