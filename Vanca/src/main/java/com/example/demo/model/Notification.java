package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String message;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationType type;

	@Column(name = "is_read", nullable = false)
	private Boolean isRead = false;

	@Column(name = "related_entity_type")
	private String relatedEntityType; // e.g., "COURSE", "QUIZ", "ENROLLMENT"

	@Column(name = "related_entity_id")
	private Long relatedEntityId;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public enum NotificationType {
		ENROLLMENT, QUIZ_GRADED, CERTIFICATE_ISSUED, COURSE_UPDATE,
		NEW_LESSON, DISCUSSION_REPLY, ACHIEVEMENT_UNLOCKED, GENERAL
	}
}
