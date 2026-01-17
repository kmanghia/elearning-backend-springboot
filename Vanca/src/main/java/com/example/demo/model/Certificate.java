package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificates", indexes = {
		@Index(name = "idx_certificate_student_id", columnList = "student_id"),
		@Index(name = "idx_certificate_course_id", columnList = "course_id"),
		@Index(name = "idx_certificate_code", columnList = "certificate_code", unique = true)
})
public class Certificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@Column(name = "certificate_code", nullable = false, unique = true)
	private String certificateCode; // Unique verification code

	@Column(name = "pdf_url")
	private String pdfUrl; // S3 URL to certificate PDF

	@CreationTimestamp
	@Column(name = "issued_at", nullable = false, updatable = false)
	private LocalDateTime issuedAt;

	@Column(name = "completion_date", nullable = false)
	private LocalDateTime completionDate;

	// Constructors
	public Certificate() {
	}

	public Certificate(Long id, User student, Course course, String certificateCode, String pdfUrl,
			LocalDateTime issuedAt, LocalDateTime completionDate) {
		this.id = id;
		this.student = student;
		this.course = course;
		this.certificateCode = certificateCode;
		this.pdfUrl = pdfUrl;
		this.issuedAt = issuedAt;
		this.completionDate = completionDate;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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
