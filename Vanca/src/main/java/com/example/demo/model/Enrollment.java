package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "course_id" })
})
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@CreationTimestamp
	@Column(name = "enrolled_at", nullable = false, updatable = false)
	private LocalDateTime enrolledAt;

	@Column(nullable = false)
	private Integer progress = 0; // Percentage 0-100

	@UpdateTimestamp
	@Column(name = "last_accessed_at")
	private LocalDateTime lastAccessedAt;

	// Constructors
	public Enrollment() {
	}

	public Enrollment(Long id, User student, Course course, Payment payment, LocalDateTime enrolledAt,
			Integer progress, LocalDateTime lastAccessedAt) {
		this.id = id;
		this.student = student;
		this.course = course;
		this.payment = payment;
		this.enrolledAt = enrolledAt;
		this.progress = progress;
		this.lastAccessedAt = lastAccessedAt;
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

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
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

	public LocalDateTime getLastAccessedAt() {
		return lastAccessedAt;
	}

	public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
		this.lastAccessedAt = lastAccessedAt;
	}
}
