package com.example.demo.dto.request;

import com.example.demo.model.Vote;
import jakarta.validation.constraints.NotNull;

public class CreateVoteRequest {

	private Long postId;
	private Long commentId;

	@NotNull(message = "Vote type is required")
	private Vote.VoteType voteType;

	// Constructors
	public CreateVoteRequest() {
	}

	public CreateVoteRequest(Long postId, Long commentId, Vote.VoteType voteType) {
		this.postId = postId;
		this.commentId = commentId;
		this.voteType = voteType;
	}

	// Getters and Setters
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Vote.VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(Vote.VoteType voteType) {
		this.voteType = voteType;
	}
}
