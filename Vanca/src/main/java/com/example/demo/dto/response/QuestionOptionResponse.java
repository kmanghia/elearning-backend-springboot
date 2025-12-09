package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionResponse {
	private Long id;
	private String content;
	private Integer orderIndex;
	// Note: isCorrect is not included to prevent cheating
}

