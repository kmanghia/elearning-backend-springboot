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
@Table(name = "comments",
	indexes = {
		@Index(name = "idx_comment_post_id", columnList = "post_id"),
		@Index(name = "idx_comment_author_id", columnList = "author_id"),
		@Index(name = "idx_comment_parent_id", columnList = "parent_comment_id"),
		@Index(name = "idx_comment_created_at", columnList = "created_at")
	}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
