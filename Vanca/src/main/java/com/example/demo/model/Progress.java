package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "progress", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "lesson_id" })
})
public class Progress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id", nullable = false)
	private Lesson lesson;

	@Column(name = "watched_duration_seconds", nullable = false)
	private Integer watchedDurationSeconds = 0;

	@Column(name = "is_completed", nullable = false)
	private Boolean isCompleted = false;

	@Column(name = "completion_percentage", nullable = false)
	private Integer completionPercentage = 0; // 0-100

	@UpdateTimestamp
	@Column(name = "last_watched_at")
	private LocalDateTime lastWatchedAt;

	// Constructors
	public Progress() {
	}

	public Progress(Long id, User student, Lesson lesson, Integer watchedDurationSeconds, Boolean isCompleted,
			Integer completionPercentage, LocalDateTime lastWatchedAt) {
		this.id = id;
		this.student = student;
		this.lesson = lesson;
		this.watchedDurationSeconds = watchedDurationSeconds;
		this.isCompleted = isCompleted;
		this.completionPercentage = completionPercentage;
		this.lastWatchedAt = lastWatchedAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public Integer getWatchedDurationSeconds() {
		return watchedDurationSeconds;
	}

	public void setWatchedDurationSeconds(Integer watchedDurationSeconds) {
		this.watchedDurationSeconds = watchedDurationSeconds;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Integer getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(Integer completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public LocalDateTime getLastWatchedAt() {
		return lastWatchedAt;
	}

	public void setLastWatchedAt(LocalDateTime lastWatchedAt) {
		this.lastWatchedAt = lastWatchedAt;
	}
}
