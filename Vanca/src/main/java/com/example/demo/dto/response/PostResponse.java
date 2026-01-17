package com.example.demo.dto.response;

import com.example.demo.model.Post;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class PostResponse {

	private Long id;
	private Long courseId;
	private String courseTitle;
	private Long authorId;
	private String authorName;
	private String title;
	private String content;
	private Post.PostType type;
	private Boolean isPinned;
	private Boolean isResolved;
	private Integer viewCount;
	private Long voteCount;
	private Integer commentCount;
	private LocalDateTime createdAt;
	@Nullable
	private LocalDateTime updatedAt;

	// Constructors
	public PostResponse() {
	}

	// Builder
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long courseId;
		private String courseTitle;
		private Long authorId;
		private String authorName;
		private String title;
		private String content;
		private Post.PostType type;
		private Boolean isPinned;
		private Boolean isResolved;
		private Integer viewCount;
		private Long voteCount;
		private Integer commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
			return this;
		}

		public Builder authorId(Long authorId) {
			this.authorId = authorId;
			return this;
		}

		public Builder authorName(String authorName) {
			this.authorName = authorName;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder type(Post.PostType type) {
			this.type = type;
			return this;
		}

		public Builder isPinned(Boolean isPinned) {
			this.isPinned = isPinned;
			return this;
		}

		public Builder isResolved(Boolean isResolved) {
			this.isResolved = isResolved;
			return this;
		}

		public Builder viewCount(Integer viewCount) {
			this.viewCount = viewCount;
			return this;
		}

		public Builder voteCount(Long voteCount) {
			this.voteCount = voteCount;
			return this;
		}

		public Builder commentCount(Integer commentCount) {
			this.commentCount = commentCount;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder updatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public PostResponse build() {
			PostResponse response = new PostResponse();
			response.setId(id);
			response.setCourseId(courseId);
			response.setCourseTitle(courseTitle);
			response.setAuthorId(authorId);
			response.setAuthorName(authorName);
			response.setTitle(title);
			response.setContent(content);
			response.setType(type);
			response.setIsPinned(isPinned);
			response.setIsResolved(isResolved);
			response.setViewCount(viewCount);
			response.setVoteCount(voteCount);
			response.setCommentCount(commentCount);
			response.setCreatedAt(createdAt);
			response.setUpdatedAt(updatedAt);
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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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

	public Boolean getIsPinned() {
		return isPinned;
	}

	public void setIsPinned(Boolean isPinned) {
		this.isPinned = isPinned;
	}

	public Boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
