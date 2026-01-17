package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateCertificateRequest {

	@NotNull(message = "Course ID is required")
	private Long courseId;

	@NotNull(message = "Student ID is required")
	private Long studentId;

	// Constructors
	public CreateCertificateRequest() {
	}

	public CreateCertificateRequest(Long courseId, Long studentId) {
		this.courseId = courseId;
		this.studentId = studentId;
	}

	// Getters and Setters
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
}
