package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", indexes = {
		@Index(name = "idx_notification_user_id", columnList = "user_id"),
		@Index(name = "idx_notification_is_read", columnList = "is_read"),
		@Index(name = "idx_notification_created_at", columnList = "created_at")
})
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

	// Constructors
	public Notification() {
	}

	public Notification(Long id, User user, String title, String message, NotificationType type,
			Boolean isRead, String relatedEntityType, Long relatedEntityId, LocalDateTime createdAt) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.message = message;
		this.type = type;
		this.isRead = isRead;
		this.relatedEntityType = relatedEntityType;
		this.relatedEntityId = relatedEntityId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public String getRelatedEntityType() {
		return relatedEntityType;
	}

	public void setRelatedEntityType(String relatedEntityType) {
		this.relatedEntityType = relatedEntityType;
	}

	public Long getRelatedEntityId() {
		return relatedEntityId;
	}

	public void setRelatedEntityId(Long relatedEntityId) {
		this.relatedEntityId = relatedEntityId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public enum NotificationType {
		ENROLLMENT, QUIZ_GRADED, CERTIFICATE_ISSUED, COURSE_UPDATE,
		NEW_LESSON, DISCUSSION_REPLY, ACHIEVEMENT_UNLOCKED, GENERAL, SYSTEM
	}
}
