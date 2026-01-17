package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts", indexes = {
		@Index(name = "idx_post_course_id", columnList = "course_id"),
		@Index(name = "idx_post_author_id", columnList = "author_id"),
		@Index(name = "idx_post_created_at", columnList = "created_at")
})
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@Column(nullable = false, length = 500)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PostType type = PostType.QUESTION;

	@Column(name = "is_pinned", nullable = false)
	private Boolean isPinned = false;

	@Column(name = "is_resolved", nullable = false)
	private Boolean isResolved = false;

	@Column(name = "view_count", nullable = false)
	private Integer viewCount = 0;

	@Column(name = "vote_count", nullable = false)
	private Long voteCount = 0L;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vote> votes = new ArrayList<>();

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Constructors
	public Post() {
	}

	public Post(Long id, Course course, User author, String title, String content, PostType type,
			Boolean isPinned, Boolean isResolved, Integer viewCount, Long voteCount,
			List<Comment> comments, List<Vote> votes, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.course = course;
		this.author = author;
		this.title = title;
		this.content = content;
		this.type = type;
		this.isPinned = isPinned;
		this.isResolved = isResolved;
		this.viewCount = viewCount;
		this.voteCount = voteCount;
		this.comments = comments;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public PostType getType() {
		return type;
	}

	public void setType(PostType type) {
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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

	public enum PostType {
		QUESTION,
		DISCUSSION,
		ANNOUNCEMENT
	}
}
