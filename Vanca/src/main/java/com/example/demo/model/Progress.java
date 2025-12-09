package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "progress", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"student_id", "lesson_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id", nullable = false)
	private Lesson lesson;

	@Column(name = "watched_duration_seconds", nullable = false)
	private Integer watchedDurationSeconds = 0;

	@Column(name = "is_completed", nullable = false)
	private Boolean isCompleted = false;

	@Column(name = "completion_percentage", nullable = false)
	private Integer completionPercentage = 0; // 0-100

	@UpdateTimestamp
	@Column(name = "last_watched_at")
	private LocalDateTime lastWatchedAt;
}

