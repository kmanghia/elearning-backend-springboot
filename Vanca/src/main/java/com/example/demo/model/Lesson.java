package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}

