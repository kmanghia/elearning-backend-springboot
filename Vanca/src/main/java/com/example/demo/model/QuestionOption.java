package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "question_options")
public class QuestionOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "is_correct", nullable = false)
	private Boolean isCorrect = false;

	@Column(name = "order_index", nullable = false)
	private Integer orderIndex;

	// Constructors
	public QuestionOption() {
	}

	public QuestionOption(Long id, Question question, String content, Boolean isCorrect, Integer orderIndex) {
		this.id = id;
		this.question = question;
		this.content = content;
		this.isCorrect = isCorrect;
		this.orderIndex = orderIndex;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}
