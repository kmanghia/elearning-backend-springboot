package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "order_index", nullable = false)
	private Integer orderIndex;

	@Column(name = "video_url")
	private String videoUrl; // S3 URL

	@Column(name = "duration_seconds")
	private Integer durationSeconds;

	@Column(name = "is_preview", nullable = false)
	private Boolean isPreview = false;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Relationships
	@OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
	private Quiz quiz;

	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
	private java.util.Set<Progress> progressRecords = new java.util.HashSet<>();

	// Constructors
	public Lesson() {
	}

	public Lesson(Long id, Course course, String title, String description, Integer orderIndex,
			String videoUrl, Integer durationSeconds, Boolean isPreview, LocalDateTime createdAt,
			LocalDateTime updatedAt, Quiz quiz, java.util.Set<Progress> progressRecords) {
		this.id = id;
		this.course = course;
		this.title = title;
		this.description = description;
		this.orderIndex = orderIndex;
		this.videoUrl = videoUrl;
		this.durationSeconds = durationSeconds;
		this.isPreview = isPreview;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.quiz = quiz;
		this.progressRecords = progressRecords;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(Integer durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	public Boolean getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(Boolean isPreview) {
		this.isPreview = isPreview;
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

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public java.util.Set<Progress> getProgressRecords() {
		return progressRecords;
	}

	public void setProgressRecords(java.util.Set<Progress> progressRecords) {
		this.progressRecords = progressRecords;
	}
}
