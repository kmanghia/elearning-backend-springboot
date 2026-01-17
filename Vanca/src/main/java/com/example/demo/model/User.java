package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

	public enum Role {
		ADMIN, INSTRUCTOR, STUDENT
	}
}

