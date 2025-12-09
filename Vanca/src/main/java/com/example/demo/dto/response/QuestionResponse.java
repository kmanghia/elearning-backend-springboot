package com.example.demo.dto.response;

import com.example.demo.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
	private Long id;
	private String content;
	private Question.QuestionType questionType;
	private Integer points;
	private Integer orderIndex;
	private List<QuestionOptionResponse> options;
}

