package com.example.demo.service;

import com.example.demo.dto.response.CourseProgressResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

	private final ProgressRepository progressRepository;
	private final LessonRepository lessonRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;

	/**
	 * Update lesson progress
	 */
	@Transactional
	public Progress updateLessonProgress(Long lessonId, Long studentId, Integer watchedDurationSeconds) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

		Progress progress = progressRepository.findByStudentIdAndLessonId(studentId, lessonId)
			.orElseGet(() -> {
				// Bug #11 Fix: Load User from repository instead of creating transient object
				User student = userRepository.findById(studentId)
					.orElseThrow(() -> new ResourceNotFoundException("Student not found"));
				
				Progress newProgress = new Progress();
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
	public CourseProgressResponse getCourseProgress(Long courseId, Long studentId) {
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

		// Map Progress entities to LessonProgressResponse DTOs
		List<CourseProgressResponse.LessonProgressResponse> lessonProgressDtos = progressList.stream()
			.map(p -> CourseProgressResponse.LessonProgressResponse.builder()
				.lessonId(p.getLesson().getId())
				.lessonTitle(p.getLesson().getTitle())
				.watchedDurationSeconds(p.getWatchedDurationSeconds())
				.completionPercentage(p.getCompletionPercentage())
				.isCompleted(p.getIsCompleted())
				.build())
			.collect(Collectors.toList());

		return CourseProgressResponse.builder()
			.courseId(courseId)
			.totalLessons(totalLessons)
			.completedLessons((int) completedLessons)
			.courseProgress(courseProgress)
			.lessonProgress(lessonProgressDtos)
			.build();
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
			CourseProgressResponse courseProgress = getCourseProgress(courseId, studentId);
			enrollment.setProgress(courseProgress.getCourseProgress());
			enrollmentRepository.save(enrollment);
		}
	}
}

