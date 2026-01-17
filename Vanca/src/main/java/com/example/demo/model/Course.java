package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@SQLDelete(sql = "UPDATE courses SET deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted = false")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instructor_id", nullable = false)
	private User instructor;

	@Column(nullable = false)
	private BigDecimal price = BigDecimal.ZERO;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.DRAFT;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private Boolean deleted = false;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	// Relationships
	@ManyToMany
	@JoinTable(name = "course_enrollments", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<User> enrolledStudents = new HashSet<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orderIndex ASC")
	private List<Lesson> lessons = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Set<Enrollment> enrollments = new HashSet<>();

	// Constructors
	public Course() {
	}

	public Course(Long id, String title, String description, User instructor, BigDecimal price,
			String thumbnailUrl, Status status, Category category, LocalDateTime createdAt,
			LocalDateTime updatedAt, Boolean deleted, LocalDateTime deletedAt,
			Set<User> enrolledStudents, List<Lesson> lessons, Set<Enrollment> enrollments) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.instructor = instructor;
		this.price = price;
		this.thumbnailUrl = thumbnailUrl;
		this.status = status;
		this.category = category;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.enrolledStudents = enrolledStudents;
		this.lessons = lessons;
		this.enrollments = enrollments;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public Set<User> getEnrolledStudents() {
		return enrolledStudents;
	}

	public void setEnrolledStudents(Set<User> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public enum Status {
		DRAFT, PUBLISHED, ARCHIVED
	}
}
