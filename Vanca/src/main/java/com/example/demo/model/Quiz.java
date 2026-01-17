package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id", nullable = false, unique = true)
	private Lesson lesson;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "time_limit_minutes")
	private Integer timeLimitMinutes;

	@Column(name = "passing_score", nullable = false)
	private Integer passingScore = 60; // Percentage

	@Column(name = "max_attempts")
	private Integer maxAttempts;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Relationships
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orderIndex ASC")
	private List<Question> questions = new ArrayList<>();

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<QuizAttempt> attempts = new ArrayList<>();

	// Constructors
	public Quiz() {
	}

	public Quiz(Long id, Lesson lesson, String title, String description, Integer timeLimitMinutes,
			Integer passingScore, Integer maxAttempts, LocalDateTime createdAt, LocalDateTime updatedAt,
			List<Question> questions, List<QuizAttempt> attempts) {
		this.id = id;
		this.lesson = lesson;
		this.title = title;
		this.description = description;
		this.timeLimitMinutes = timeLimitMinutes;
		this.passingScore = passingScore;
		this.maxAttempts = maxAttempts;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.questions = questions;
		this.attempts = attempts;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public Integer getPassingScore() {
		return passingScore;
	}

	public void setPassingScore(Integer passingScore) {
		this.passingScore = passingScore;
	}

	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<QuizAttempt> getAttempts() {
		return attempts;
	}

	public void setAttempts(List<QuizAttempt> attempts) {
		this.attempts = attempts;
	}
}
