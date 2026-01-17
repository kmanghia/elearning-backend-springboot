package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCertificateRequest {
	
	@NotNull(message = "Course ID is required")
	private Long courseId;
	
	@NotNull(message = "Student ID is required")
	private Long studentId;
}
