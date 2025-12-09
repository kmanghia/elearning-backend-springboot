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
@Table(name = "quiz_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}

