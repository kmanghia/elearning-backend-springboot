package com.example.demo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quiz_answers")
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
	@JoinTable(name = "answer_selected_options", joinColumns = @JoinColumn(name = "answer_id"), inverseJoinColumns = @JoinColumn(name = "option_id"))
	private Set<QuestionOption> selectedOptions = new HashSet<>();

	@Column(name = "is_correct", nullable = false)
	private Boolean isCorrect = false;

	// Constructors
	public QuizAnswer() {
	}

	public QuizAnswer(Long id, QuizAttempt attempt, Question question, Set<QuestionOption> selectedOptions,
			Boolean isCorrect) {
		this.id = id;
		this.attempt = attempt;
		this.question = question;
		this.selectedOptions = selectedOptions;
		this.isCorrect = isCorrect;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuizAttempt getAttempt() {
		return attempt;
	}

	public void setAttempt(QuizAttempt attempt) {
		this.attempt = attempt;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Set<QuestionOption> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(Set<QuestionOption> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
}
