package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id", nullable = false)
	private Quiz quiz;

	@CreationTimestamp
	@Column(name = "started_at", nullable = false, updatable = false)
	private LocalDateTime startedAt;

	@Column(name = "submitted_at")
	private LocalDateTime submittedAt;

	@Column(nullable = false)
	private Integer score = 0; // Percentage

	@Column(name = "is_passed", nullable = false)
	private Boolean isPassed = false;

	// Relationships
	@OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizAnswer> answers = new ArrayList<>();

	// Constructors
	public QuizAttempt() {
	}

	public QuizAttempt(Long id, User student, Quiz quiz, LocalDateTime startedAt, LocalDateTime submittedAt,
			Integer score, Boolean isPassed, List<QuizAnswer> answers) {
		this.id = id;
		this.student = student;
		this.quiz = quiz;
		this.startedAt = startedAt;
		this.submittedAt = submittedAt;
		this.score = score;
		this.isPassed = isPassed;
		this.answers = answers;
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

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}

	public List<QuizAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuizAnswer> answers) {
		this.answers = answers;
	}
}
