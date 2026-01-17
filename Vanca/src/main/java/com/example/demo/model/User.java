package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted = false")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String fullName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(nullable = false)
	private Boolean enabled = true;

	@Column(name = "email_verified", nullable = false)
	private Boolean emailVerified = false;

	@Column(name = "verification_token")
	private String verificationToken;

	@Column(nullable = false)
	private Boolean deleted = false;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Relationships
	@ManyToMany(mappedBy = "enrolledStudents")
	private Set<Course> enrolledCourses = new HashSet<>();

	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
	private Set<Course> createdCourses = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<Enrollment> enrollments = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<QuizAttempt> quizAttempts = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<Progress> progressRecords = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<PasswordHistory> passwordHistories = new HashSet<>();

	// Constructors
	public User() {
	}

	public User(Long id, String email, String password, String fullName, Role role, String avatarUrl,
			Boolean enabled, Boolean emailVerified, String verificationToken, Boolean deleted,
			LocalDateTime deletedAt, LocalDateTime createdAt, LocalDateTime updatedAt,
			Set<Course> enrolledCourses, Set<Course> createdCourses, Set<Enrollment> enrollments,
			Set<QuizAttempt> quizAttempts, Set<Progress> progressRecords, Set<PasswordHistory> passwordHistories) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
		this.avatarUrl = avatarUrl;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enrolledCourses = enrolledCourses;
		this.createdCourses = createdCourses;
		this.enrollments = enrollments;
		this.quizAttempts = quizAttempts;
		this.progressRecords = progressRecords;
		this.passwordHistories = passwordHistories;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<Course> getEnrolledCourses() {
		return enrolledCourses;
	}

	public void setEnrolledCourses(Set<Course> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	public Set<Course> getCreatedCourses() {
		return createdCourses;
	}

	public void setCreatedCourses(Set<Course> createdCourses) {
		this.createdCourses = createdCourses;
	}

	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public Set<QuizAttempt> getQuizAttempts() {
		return quizAttempts;
	}

	public void setQuizAttempts(Set<QuizAttempt> quizAttempts) {
		this.quizAttempts = quizAttempts;
	}

	public Set<Progress> getProgressRecords() {
		return progressRecords;
	}

	public void setProgressRecords(Set<Progress> progressRecords) {
		this.progressRecords = progressRecords;
	}

	public Set<PasswordHistory> getPasswordHistories() {
		return passwordHistories;
	}

	public void setPasswordHistories(Set<PasswordHistory> passwordHistories) {
		this.passwordHistories = passwordHistories;
	}

	public enum Role {
		ADMIN, INSTRUCTOR, STUDENT
	}
}
