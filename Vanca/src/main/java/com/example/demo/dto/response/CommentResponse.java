package com.example.demo.dto.response;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {

	private Long id;
	private Long postId;
	private Long authorId;
	private String authorName;
	@Nullable
	private Long parentCommentId;
	private String content;
	private Boolean isAcceptedAnswer;
	private Long voteCount;
	@Nullable
	private List<CommentResponse> replies;
	private LocalDateTime createdAt;
	@Nullable
	private LocalDateTime updatedAt;

	// Constructors
	public CommentResponse() {
	}

	// Builder
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long postId;
		private Long authorId;
		private String authorName;
		private Long parentCommentId;
		private String content;
		private Boolean isAcceptedAnswer;
		private Long voteCount;
		private List<CommentResponse> replies;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder postId(Long postId) {
			this.postId = postId;
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

		public Builder parentCommentId(Long parentCommentId) {
			this.parentCommentId = parentCommentId;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder isAcceptedAnswer(Boolean isAcceptedAnswer) {
			this.isAcceptedAnswer = isAcceptedAnswer;
			return this;
		}

		public Builder voteCount(Long voteCount) {
			this.voteCount = voteCount;
			return this;
		}

		public Builder replies(List<CommentResponse> replies) {
			this.replies = replies;
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

		public CommentResponse build() {
			CommentResponse response = new CommentResponse();
			response.setId(id);
			response.setPostId(postId);
			response.setAuthorId(authorId);
			response.setAuthorName(authorName);
			response.setParentCommentId(parentCommentId);
			response.setContent(content);
			response.setIsAcceptedAnswer(isAcceptedAnswer);
			response.setVoteCount(voteCount);
			response.setReplies(replies);
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

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
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

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsAcceptedAnswer() {
		return isAcceptedAnswer;
	}

	public void setIsAcceptedAnswer(Boolean isAcceptedAnswer) {
		this.isAcceptedAnswer = isAcceptedAnswer;
	}

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}

	public List<CommentResponse> getReplies() {
		return replies;
	}

	public void setReplies(List<CommentResponse> replies) {
		this.replies = replies;
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
