package com.example.demo.dto.response;

public class QuestionOptionResponse {
	private Long id;
	private String content;
	private Integer orderIndex;
	// Note: isCorrect is not included to prevent cheating

	public QuestionOptionResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String content;
		private Integer orderIndex;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder orderIndex(Integer orderIndex) {
			this.orderIndex = orderIndex;
			return this;
		}

		public QuestionOptionResponse build() {
			QuestionOptionResponse response = new QuestionOptionResponse();
			response.setId(id);
			response.setContent(content);
			response.setOrderIndex(orderIndex);
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}
