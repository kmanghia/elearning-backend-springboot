package com.example.demo.dto.response;

import java.time.LocalDateTime;

public class EnrollmentResponse {
	private Long id;
	private Long courseId;
	private String courseName;
	private Long studentId;
	private String studentName;
	private LocalDateTime enrolledAt;
	private Integer progress; // Progress percentage (0-100)

	public EnrollmentResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long courseId;
		private String courseName;
		private Long studentId;
		private String studentName;
		private LocalDateTime enrolledAt;
		private Integer progress;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseName(String courseName) {
			this.courseName = courseName;
			return this;
		}

		public Builder studentId(Long studentId) {
			this.studentId = studentId;
			return this;
		}

		public Builder studentName(String studentName) {
			this.studentName = studentName;
			return this;
		}

		public Builder enrolledAt(LocalDateTime enrolledAt) {
			this.enrolledAt = enrolledAt;
			return this;
		}

		public Builder progress(Integer progress) {
			this.progress = progress;
			return this;
		}

		public EnrollmentResponse build() {
			EnrollmentResponse response = new EnrollmentResponse();
			response.setId(id);
			response.setCourseId(courseId);
			response.setCourseName(courseName);
			response.setStudentId(studentId);
			response.setStudentName(studentName);
			response.setEnrolledAt(enrolledAt);
			response.setProgress(progress);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public LocalDateTime getEnrolledAt() {
		return enrolledAt;
	}

	public void setEnrolledAt(LocalDateTime enrolledAt) {
		this.enrolledAt = enrolledAt;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
}
