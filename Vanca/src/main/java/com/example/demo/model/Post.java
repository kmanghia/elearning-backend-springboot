package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts",
	indexes = {
		@Index(name = "idx_post_course_id", columnList = "course_id"),
		@Index(name = "idx_post_author_id", columnList = "author_id"),
		@Index(name = "idx_post_created_at", columnList = "created_at")
	}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
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
	private Integer voteCount = 0;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vote> votes = new ArrayList<>();

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public enum PostType {
		QUESTION,
		DISCUSSION,
		ANNOUNCEMENT
	}
}
