package com.example.demo.dto.request;

import com.example.demo.model.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

	// Constructors
	public CreatePostRequest() {
	}

	public CreatePostRequest(Long courseId, String title, String content, Post.PostType type) {
		this.courseId = courseId;
		this.title = title;
		this.content = content;
		this.type = type;
	}

	// Getters and Setters
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

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

	public Post.PostType getType() {
		return type;
	}

	public void setType(Post.PostType type) {
		this.type = type;
	}
}
