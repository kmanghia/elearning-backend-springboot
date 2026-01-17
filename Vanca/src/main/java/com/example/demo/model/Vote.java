package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "post_id"}),
		@UniqueConstraint(columnNames = {"user_id", "comment_id"})
	},
	indexes = {
		@Index(name = "idx_vote_user_id", columnList = "user_id"),
		@Index(name = "idx_vote_post_id", columnList = "post_id"),
		@Index(name = "idx_vote_comment_id", columnList = "comment_id")
	}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
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

	public enum VoteType {
		UPVOTE,
		DOWNVOTE
	}
}
