package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificates", indexes = {
	@Index(name = "idx_certificate_student_id", columnList = "student_id"),
	@Index(name = "idx_certificate_course_id", columnList = "course_id"),
	@Index(name = "idx_certificate_code", columnList = "certificate_code", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
