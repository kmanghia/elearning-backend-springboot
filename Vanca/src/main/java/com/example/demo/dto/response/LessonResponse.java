package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
	private Long id;
	private Long courseId;
	private String title;
	private String description;
	private Integer orderIndex;
	private String videoUrl; // S3 URL or pre-signed URL
	private Integer durationSeconds;
	private Boolean isPreview;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean hasQuiz;
}

