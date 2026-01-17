package com.example.demo.service;

import com.example.demo.dto.response.StudentAnalyticsResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	private final UserRepository userRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final ProgressRepository progressRepository;
	private final QuizAttemptRepository quizAttemptRepository;

	public StudentAnalyticsResponse getStudentAnalytics(Long studentId) {
		// Verify user exists
		User student = userRepository.findById(studentId)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		// Get all enrollments (use unpaged version)
		List<Enrollment> enrollments = enrollmentRepository
			.findByStudentId(studentId, PageRequest.of(0, Integer.MAX_VALUE))
			.getContent();
		
		// Calculate overall stats
		int totalEnrolled = enrollments.size();
		int completedCourses = (int) enrollments.stream()
			.filter(e -> e.getProgress() != null && e.getProgress() >= 100.0)
			.count();
		double overallCompletionRate = totalEnrolled > 0 
			? (completedCourses * 100.0 / totalEnrolled) 
			: 0.0;

		// Get quiz performance - query by each student-quiz combination 
		// For simplicity, we'll aggregate from enrollments
		List<QuizAttempt> quizAttempts = new ArrayList<>();
		for (Enrollment enrollment : enrollments) {
			// Get quizzes for this course's lessons
			for (Lesson lesson : enrollment.getCourse().getLessons()) {
				Quiz quiz = lesson.getQuiz();
				if (quiz != null) {
					List<QuizAttempt> attempts = quizAttemptRepository
						.findByStudentIdAndQuizId(studentId, quiz.getId());
					quizAttempts.addAll(attempts);
				}
			}
		}
		
		int totalQuizzes = quizAttempts.size();
		double averageQuizScore = quizAttempts.stream()
			.mapToDouble(QuizAttempt::getScore)
			.average()
			.orElse(0.0);
		int passedQuizzes = (int) quizAttempts.stream()
			.filter(QuizAttempt::getIsPassed)
			.count();
		int failedQuizzes = totalQuizzes - passedQuizzes;

		// Get total lessons completed across all courses
		int totalLessonsCompleted = 0;
		for (Enrollment enrollment : enrollments) {
			List<Progress> courseProgress = progressRepository
				.findByStudentIdAndCourseId(studentId, enrollment.getCourse().getId());
			totalLessonsCompleted += (int) courseProgress.stream()
				.filter(Progress::getIsCompleted)
				.count();
		}

		// Build course progress summaries
		List<StudentAnalyticsResponse.CourseProgressSummary> courseProgressList = 
			enrollments.stream()
				.map(enrollment -> buildCourseProgressSummary(enrollment, studentId))
				.collect(Collectors.toList());

		// Build learning streak (simplified)
		Map<String, Integer> learningStreak = new HashMap<>();
		learningStreak.put("activeDaysThisWeek", 0); // Simplified
		learningStreak.put("activeDaysThisMonth", totalEnrolled > 0 ? 1 : 0); // Simplified

		return StudentAnalyticsResponse.builder()
			.studentId(studentId)
			.studentName(student.getFullName())
			.totalEnrolledCourses(totalEnrolled)
			.completedCourses(completedCourses)
			.overallCompletionRate(Math.round(overallCompletionRate * 100.0) / 100.0)
			.totalQuizzes(totalQuizzes)
			.averageQuizScore(Math.round(averageQuizScore * 100.0) / 100.0)
			.passedQuizzes(passedQuizzes)
			.failedQuizzes(failedQuizzes)
			.totalLessonsCompleted(totalLessonsCompleted)
			.learningStreak(learningStreak)
			.courseProgressList(courseProgressList)
			.build();
	}

	private StudentAnalyticsResponse.CourseProgressSummary buildCourseProgressSummary(
			Enrollment enrollment, Long studentId) {
		
		Course course = enrollment.getCourse();
		int totalLessons = course.getLessons().size();
		
		// Get completed lessons for this course
		List<Progress> courseProgress = progressRepository
			.findByStudentIdAndCourseId(studentId, course.getId());
		
		int completedLessons = (int) courseProgress.stream()
			.filter(Progress::getIsCompleted)
			.count();
		
		double completionPercentage = totalLessons > 0 
			? (completedLessons * 100.0 / totalLessons) 
			: 0.0;
		
		String status;
		if (completionPercentage == 0) {
			status = "NOT_STARTED";
		} else if (completionPercentage >= 100) {
			status = "COMPLETED";
		} else {
			status = "IN_PROGRESS";
		}

		return StudentAnalyticsResponse.CourseProgressSummary.builder()
			.courseId(course.getId())
			.courseTitle(course.getTitle())
			.completionPercentage(Math.round(completionPercentage * 100.0) / 100.0)
			.completedLessons(completedLessons)
			.totalLessons(totalLessons)
			.status(status)
			.build();
	}
}
