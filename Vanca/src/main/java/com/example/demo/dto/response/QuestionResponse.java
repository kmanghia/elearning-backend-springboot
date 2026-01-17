package com.example.demo.dto.response;

import com.example.demo.model.Question;

import java.util.List;

public class QuestionResponse {
	private Long id;
	private String content;
	private Question.QuestionType questionType;
	private Integer points;
	private Integer orderIndex;
	private List<QuestionOptionResponse> options;

	public QuestionResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String content;
		private Question.QuestionType questionType;
		private Integer points;
		private Integer orderIndex;
		private List<QuestionOptionResponse> options;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder questionType(Question.QuestionType questionType) {
			this.questionType = questionType;
			return this;
		}

		public Builder points(Integer points) {
			this.points = points;
			return this;
		}

		public Builder orderIndex(Integer orderIndex) {
			this.orderIndex = orderIndex;
			return this;
		}

		public Builder options(List<QuestionOptionResponse> options) {
			this.options = options;
			return this;
		}

		public QuestionResponse build() {
			QuestionResponse response = new QuestionResponse();
			response.setId(id);
			response.setContent(content);
			response.setQuestionType(questionType);
			response.setPoints(points);
			response.setOrderIndex(orderIndex);
			response.setOptions(options);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Question.QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Question.QuestionType questionType) {
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

	public List<QuestionOptionResponse> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOptionResponse> options) {
		this.options = options;
	}
}
