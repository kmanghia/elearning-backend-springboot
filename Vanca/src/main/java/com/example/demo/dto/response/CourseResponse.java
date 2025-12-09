package com.example.demo.dto.response;

import com.example.demo.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
	private Long id;
	private String title;
	private String description;
	private Long instructorId;
	private String instructorName;
	private BigDecimal price;
	private String thumbnailUrl;
	private Course.Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer lessonCount;
	private Integer enrolledStudentCount;
}

