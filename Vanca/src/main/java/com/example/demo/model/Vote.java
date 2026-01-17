package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "user_id", "post_id" }),
		@UniqueConstraint(columnNames = { "user_id", "comment_id" })
}, indexes = {
		@Index(name = "idx_vote_user_id", columnList = "user_id"),
		@Index(name = "idx_vote_post_id", columnList = "post_id"),
		@Index(name = "idx_vote_comment_id", columnList = "comment_id")
})
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private VoteType voteType;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// Constructors
	public Vote() {
	}

	public Vote(Long id, User user, Post post, Comment comment, VoteType voteType, LocalDateTime createdAt) {
		this.id = id;
		this.user = user;
		this.post = post;
		this.comment = comment;
		this.voteType = voteType;
		this.createdAt = createdAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public enum VoteType {
		UPVOTE,
		DOWNVOTE
	}
}
