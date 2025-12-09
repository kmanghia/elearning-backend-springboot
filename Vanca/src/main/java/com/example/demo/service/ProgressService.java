package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Lesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

	private final ProgressRepository progressRepository;
	private final LessonRepository lessonRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;

	/**
	 * Update lesson progress
	 */
	@Transactional
	public Progress updateLessonProgress(Long lessonId, Long studentId, Integer watchedDurationSeconds) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

		Progress progress = progressRepository.findByStudentIdAndLessonId(studentId, lessonId)
			.orElseGet(() -> {
				Progress newProgress = new Progress();
				User student = new User();
				student.setId(studentId);
				newProgress.setStudent(student);
				newProgress.setLesson(lesson);
				return newProgress;
			});

		progress.setWatchedDurationSeconds(watchedDurationSeconds);
		progress.setLastWatchedAt(LocalDateTime.now());

		// Calculate completion percentage
		if (lesson.getDurationSeconds() != null && lesson.getDurationSeconds() > 0) {
			int percentage = Math.min(100, 
				(watchedDurationSeconds * 100) / lesson.getDurationSeconds());
			progress.setCompletionPercentage(percentage);
			progress.setIsCompleted(percentage >= 80); // 80% watched = completed
		}

		progress = progressRepository.save(progress);

		// Update enrollment progress if lesson is completed
		if (progress.getIsCompleted()) {
			updateEnrollmentProgress(studentId, lesson.getCourse().getId());
		}

		return progress;
	}

	/**
	 * Get course progress for a student
	 */
	public CourseProgressDTO getCourseProgress(Long courseId, Long studentId) {
		List<Progress> progressList = progressRepository
			.findByStudentIdAndCourseId(studentId, courseId);

		int totalLessons = courseRepository.findById(courseId)
			.map(course -> course.getLessons() != null ? course.getLessons().size() : 0)
			.orElse(0);

		long completedLessons = progressList.stream()
			.filter(p -> p.getIsCompleted() != null && p.getIsCompleted())
			.count();

		int courseProgress = totalLessons > 0 
			? (int) ((completedLessons * 100) / totalLessons) 
			: 0;

		return new CourseProgressDTO(
			courseId,
			totalLessons,
			(int) completedLessons,
			courseProgress,
			progressList
		);
	}

	/**
	 * Update enrollment progress
	 */
	@Transactional
	private void updateEnrollmentProgress(Long studentId, Long courseId) {
		Enrollment enrollment = enrollmentRepository
			.findByStudentIdAndCourseId(studentId, courseId)
			.orElse(null);

		if (enrollment != null) {
			CourseProgressDTO courseProgress = getCourseProgress(courseId, studentId);
			enrollment.setProgress(courseProgress.getCourseProgress());
			enrollmentRepository.save(enrollment);
		}
	}

	// DTO for course progress
	public static class CourseProgressDTO {
		private Long courseId;
		private int totalLessons;
		private int completedLessons;
		private int courseProgress; // Percentage
		private List<Progress> lessonProgress;

		public CourseProgressDTO(Long courseId, int totalLessons, int completedLessons, 
			int courseProgress, List<Progress> lessonProgress) {
			this.courseId = courseId;
			this.totalLessons = totalLessons;
			this.completedLessons = completedLessons;
			this.courseProgress = courseProgress;
			this.lessonProgress = lessonProgress;
		}

		// Getters
		public Long getCourseId() { return courseId; }
		public int getTotalLessons() { return totalLessons; }
		public int getCompletedLessons() { return completedLessons; }
		public int getCourseProgress() { return courseProgress; }
		public List<Progress> getLessonProgress() { return lessonProgress != null ? lessonProgress : List.of(); }
	}
}

