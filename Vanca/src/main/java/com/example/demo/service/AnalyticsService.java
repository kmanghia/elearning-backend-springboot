package com.example.demo.service;

import com.example.demo.dto.response.StudentAnalyticsResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
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
	private final CourseRepository courseRepository;

	public StudentAnalyticsResponse getStudentAnalytics(Long studentId) {
		// Verify user exists
		User student = userRepository.findById(studentId)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		// Get all enrollments
		List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
		
		// Calculate overall stats
		int totalEnrolled = enrollments.size();
		int completedCourses = (int) enrollments.stream()
			.filter(e -> e.getProgress() != null && e.getProgress() >= 100.0)
			.count();
		double overallCompletionRate = totalEnrolled > 0 
			? (completedCourses * 100.0 / totalEnrolled) 
			: 0.0;

		// Get quiz performance
		List<QuizAttempt> quizAttempts = quizAttemptRepository.findByStudentId(studentId);
		int totalQuizzes = quizAttempts.size();
		double averageQuizScore = quizAttempts.stream()
			.mapToDouble(QuizAttempt::getScore)
			.average()
			.orElse(0.0);
		int passedQuizzes = (int) quizAttempts.stream()
			.filter(QuizAttempt::getPassed)
			.count();
		int failedQuizzes = totalQuizzes - passedQuizzes;

		// Get total lessons completed
		List<Progress> allProgress = progressRepository.findByStudentId(studentId);
		int totalLessonsCompleted = (int) allProgress.stream()
			.filter(Progress::getIsCompleted)
			.count();

		// Build course progress summaries
		List<StudentAnalyticsResponse.CourseProgressSummary> courseProgressList = 
			enrollments.stream()
				.map(enrollment -> buildCourseProgressSummary(enrollment, studentId))
				.collect(Collectors.toList());

		// Build learning streak (simplified - just count for now)
		Map<String, Integer> learningStreak = new HashMap<>();
		learningStreak.put("activeDaysThisWeek", calculateActiveDays(allProgress, 7));
		learningStreak.put("activeDaysThisMonth", calculateActiveDays(allProgress, 30));

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
		List<Progress> courseProgress = progressRepository.findByCourseIdAndStudentId(
			course.getId(), studentId);
		
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

	private int calculateActiveDays(List<Progress> progressList, int daysBack) {
		// Count unique days where user had progress activity
		Set<String> activeDates = progressList.stream()
			.filter(p -> p.getUpdatedAt() != null)
			.map(p -> p.getUpdatedAt().toLocalDate().toString())
			.collect(Collectors.toSet());
		
		return activeDates.size();
	}
}
