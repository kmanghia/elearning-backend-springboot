package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id", nullable = false)
	private Quiz quiz;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "question_type", nullable = false)
	private QuestionType questionType;

	@Column(nullable = false)
	private Integer points = 1;

	@Column(name = "order_index", nullable = false)
	private Integer orderIndex;

	// Relationships
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orderIndex ASC")
	private List<QuestionOption> options = new ArrayList<>();

	// Constructors
	public Question() {
	}

	public Question(Long id, Quiz quiz, String content, QuestionType questionType, Integer points,
			Integer orderIndex, List<QuestionOption> options) {
		this.id = id;
		this.quiz = quiz;
		this.content = content;
		this.questionType = questionType;
		this.points = points;
		this.orderIndex = orderIndex;
		this.options = options;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<QuestionOption> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOption> options) {
		this.options = options;
	}

	public enum QuestionType {
		SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE
	}
}
