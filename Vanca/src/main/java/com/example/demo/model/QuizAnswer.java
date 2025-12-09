package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quiz_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attempt_id", nullable = false)
	private QuizAttempt attempt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	@ManyToMany
	@JoinTable(
		name = "answer_selected_options",
		joinColumns = @JoinColumn(name = "answer_id"),
		inverseJoinColumns = @JoinColumn(name = "option_id")
	)
	private Set<QuestionOption> selectedOptions = new HashSet<>();

	@Column(name = "is_correct", nullable = false)
	private Boolean isCorrect = false;
}

