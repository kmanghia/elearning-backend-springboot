package com.example.demo.dto.response;

import java.time.LocalDateTime;

public class CertificateResponse {

	private Long id;
	private Long studentId;
	private String studentName;
	private Long courseId;
	private String courseTitle;
	private String certificateCode;
	private String pdfUrl;
	private LocalDateTime issuedAt;
	private LocalDateTime completionDate;

	// Constructors
	public CertificateResponse() {
	}

	// Builder
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Long studentId;
		private String studentName;
		private Long courseId;
		private String courseTitle;
		private String certificateCode;
		private String pdfUrl;
		private LocalDateTime issuedAt;
		private LocalDateTime completionDate;

		public Builder id(Long id) {
			this.id = id;
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

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder courseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
			return this;
		}

		public Builder certificateCode(String certificateCode) {
			this.certificateCode = certificateCode;
			return this;
		}

		public Builder pdfUrl(String pdfUrl) {
			this.pdfUrl = pdfUrl;
			return this;
		}

		public Builder issuedAt(LocalDateTime issuedAt) {
			this.issuedAt = issuedAt;
			return this;
		}

		public Builder completionDate(LocalDateTime completionDate) {
			this.completionDate = completionDate;
			return this;
		}

		public CertificateResponse build() {
			CertificateResponse response = new CertificateResponse();
			response.setId(id);
			response.setStudentId(studentId);
			response.setStudentName(studentName);
			response.setCourseId(courseId);
			response.setCourseTitle(courseTitle);
			response.setCertificateCode(certificateCode);
			response.setPdfUrl(pdfUrl);
			response.setIssuedAt(issuedAt);
			response.setCompletionDate(completionDate);
			return response;
		}
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public LocalDateTime getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(LocalDateTime issuedAt) {
		this.issuedAt = issuedAt;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}
}
