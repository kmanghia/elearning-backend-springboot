package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments", indexes = {
		@Index(name = "idx_comment_post_id", columnList = "post_id"),
		@Index(name = "idx_comment_author_id", columnList = "author_id"),
		@Index(name = "idx_comment_parent_id", columnList = "parent_comment_id"),
		@Index(name = "idx_comment_created_at", columnList = "created_at")
})
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> replies = new ArrayList<>();

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "is_accepted_answer", nullable = false)
	private Boolean isAcceptedAnswer = false;

	@Column(name = "vote_count", nullable = false)
	private Long voteCount = 0L;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vote> votes = new ArrayList<>();

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Constructors
	public Comment() {
	}

	public Comment(Long id, Post post, User author, Comment parentComment, List<Comment> replies,
			String content, Boolean isAcceptedAnswer, Long voteCount, List<Vote> votes,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.post = post;
		this.author = author;
		this.parentComment = parentComment;
		this.replies = replies;
		this.content = content;
		this.isAcceptedAnswer = isAcceptedAnswer;
		this.voteCount = voteCount;
		this.votes = votes;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
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

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
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
