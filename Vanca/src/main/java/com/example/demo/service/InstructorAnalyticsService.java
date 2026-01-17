package com.example.demo.service;

import com.example.demo.dto.response.CoursePerformanceDTO;
import com.example.demo.dto.response.InstructorDashboardResponse;
import com.example.demo.dto.response.StudentEngagementMetrics;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorAnalyticsService {
    
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ProgressRepository progressRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    
    /**
     * Get comprehensive dashboard for instructor
     */
    public InstructorDashboardResponse getInstructorDashboard(Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        
        List<Course> instructorCourses = courseRepository.findByInstructor(instructor);
        List<Course> publishedCourses = instructorCourses.stream()
                .filter(c -> c.getStatus() == Course.Status.PUBLISHED)
                .collect(Collectors.toList());
        
        // Overall statistics
        Set<Long> allStudentIds = instructorCourses.stream()
                .flatMap(c -> c.getEnrolledStudents().stream())
                .map(User::getId)
                .collect(Collectors.toSet());
        
        int totalEnrollments = instructorCourses.stream()
                .mapToInt(c -> c.getEnrolledStudents() != null ? c.getEnrolledStudents().size() : 0)
                .sum();
        
        // Calculate overall average rating
        List<Double> allRatings = instructorCourses.stream()
                .map(c -> courseReviewRepository.getAverageByCourseId(c.getId()).orElse(null))
                .filter(rating -> rating != null && rating > 0)
                .collect(Collectors.toList());
        
        Double overallAvgRating = allRatings.isEmpty() ? 0.0 :
                allRatings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        
        int totalReviews = instructorCourses.stream()
                .mapToInt(c -> courseReviewRepository.countByCourseId(c.getId()).intValue())
                .sum();
        
        // Engagement metrics
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        
        // Count active students (those with progress in last 7 days)
        int activeStudents = (int) instructorCourses.stream()
                .flatMap(c -> c.getEnrolledStudents().stream())
                .map(User::getId)
                .distinct()
                .filter(studentId -> hasRecentActivity(studentId, instructorCourses, oneWeekAgo))
                .count();
        
        // Calculate average completion rate
        double avgCompletionRate = instructorCourses.stream()
                .mapToDouble(this::calculateCourseCompletionRate)
                .average()
                .orElse(0.0);
        
        // Total quiz attempts
        int totalQuizAttempts = (int) quizAttemptRepository.findAll().stream()
                .filter(attempt -> instructorCourses.stream()
                        .anyMatch(course -> course.getId().equals(attempt.getQuiz().getLesson().getCourse().getId())))
                .count();
        
        // Get course performances
        List<CoursePerformanceDTO> coursePerformances = instructorCourses.stream()
                .map(this::getCoursePerformance)
                .collect(Collectors.toList());
        
        // Recent activity
        int newEnrollmentsThisWeek = (int) enrollmentRepository.findAll().stream()
                .filter(e -> e.getEnrolledAt().isAfter(oneWeekAgo))
                .filter(e -> instructorCourses.stream()
                        .anyMatch(c -> c.getId().equals(e.getCourse().getId())))
                .count();
        
        int newReviewsThisWeek = (int) courseReviewRepository.findAll().stream()
                .filter(r -> r.getCreatedAt().isAfter(oneWeekAgo))
                .filter(r -> instructorCourses.stream()
                        .anyMatch(c -> c.getId().equals(r.getCourse().getId())))
                .count();
        
        return InstructorDashboardResponse.builder()
                .instructorId(instructorId)
                .instructorName(instructor.getFullName())
                .totalCourses(instructorCourses.size())
                .publishedCourses(publishedCourses.size())
                .totalStudents(allStudentIds.size())
                .totalEnrollments(totalEnrollments)
                .overallAverageRating(overallAvgRating)
                .totalReviews(totalReviews)
                .activeStudents(activeStudents)
                .averageCompletionRate(avgCompletionRate)
                .totalQuizAttempts(totalQuizAttempts)
                .coursePerformances(coursePerformances)
                .newEnrollmentsThisWeek(newEnrollmentsThisWeek)
                .newReviewsThisWeek(newReviewsThisWeek)
                .build();
    }
    
    /**
     * Get performance metrics for a specific course
     */
    public CoursePerformanceDTO getCoursePerformance(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return getCoursePerformance(course);
    }
    
    private CoursePerformanceDTO getCoursePerformance(Course course) {
        int totalEnrollments = course.getEnrolledStudents() != null ? 
                course.getEnrolledStudents().size() : 0;
        
        // Calculate completed enrollments
        int completedEnrollments = (int) course.getEnrolledStudents().stream()
                .filter(student -> isCourseCompleted(student.getId(), course.getId()))
                .count();
        
        double completionRate = totalEnrollments > 0 ? 
                (completedEnrollments * 100.0 / totalEnrollments) : 0.0;
        
        Double avgRating = courseReviewRepository.getAverageByCourseId(course.getId()).orElse(0.0);
        int totalReviews = courseReviewRepository.countByCourseId(course.getId()).intValue();
        
        int totalLessons = lessonRepository.countByCourseId(course.getId()).intValue();
        int totalQuizzes = quizRepository.countByCourseId(course.getId()).intValue();
        
        // Calculate average quiz score for this course
        double avgQuizScore = quizAttemptRepository.findAll().stream()
                .filter(attempt -> attempt.getQuiz().getLesson().getCourse().getId().equals(course.getId()))
                .filter(attempt -> attempt.getScore() != null)
                .mapToDouble(QuizAttempt::getScore)
                .average()
                .orElse(0.0);
        
        // Calculate average progress percentage
        double avgProgress = course.getEnrolledStudents().stream()
                .mapToDouble(student -> calculateStudentProgress(student.getId(), course.getId()))
                .average()
                .orElse(0.0);
        
        return CoursePerformanceDTO.builder()
                .courseId(course.getId())
                .courseName(course.getTitle())
                .totalEnrollments(totalEnrollments)
                .completedEnrollments(completedEnrollments)
                .completionRate(completionRate)
                .averageRating(avgRating)
                .totalReviews(totalReviews)
                .totalLessons(totalLessons)
                .totalQuizzes(totalQuizzes)
                .averageQuizScore(avgQuizScore)
                .averageProgressPercentage(avgProgress)
                .build();
    }
    
    /**
     * Get student engagement metrics for a course
     */
    public StudentEngagementMetrics getStudentEngagement(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        int totalStudents = course.getEnrolledStudents() != null ? 
                course.getEnrolledStudents().size() : 0;
        
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        
        // Active students in last 7 days
        int activeStudents = (int) course.getEnrolledStudents().stream()
                .filter(student -> {
                    List<Progress> recentProgress = progressRepository
                            .findByStudentIdAndCourseId(student.getId(), courseId);
                    return recentProgress.stream()
                            .anyMatch(p -> p.getLastWatchedAt() != null && p.getLastWatchedAt().isAfter(oneWeekAgo));
                })
                .count();
        
        // Completed students
        int completedStudents = (int) course.getEnrolledStudents().stream()
                .filter(student -> isCourseCompleted(student.getId(), courseId))
                .count();
        
        double completionRate = totalStudents > 0 ? 
                (completedStudents * 100.0 / totalStudents) : 0.0;
        
        // Average progress
        double avgProgress = course.getEnrolledStudents().stream()
                .mapToDouble(student -> calculateStudentProgress(student.getId(), courseId))
                .average()
                .orElse(0.0);
        
        // Quiz metrics
        int totalQuizAttempts = (int) quizAttemptRepository.findAll().stream()
                .filter(attempt -> attempt.getQuiz().getCourse().getId().equals(courseId))
                .count();
        
        double avgQuizScore = quizAttemptRepository.findAll().stream()
                .filter(attempt -> attempt.getQuiz().getCourse().getId().equals(courseId))
                .filter(attempt -> attempt.getScore() != null)
                .mapToDouble(QuizAttempt::getScore)
                .average()
                .orElse(0.0);
        
        return StudentEngagementMetrics.builder()
                .courseId(courseId)
                .courseName(course.getTitle())
                .totalStudents(totalStudents)
                .activeStudents(activeStudents)
                .completedStudents(completedStudents)
                .completionRate(completionRate)
                .averageProgress(avgProgress)
                .totalQuizAttempts(totalQuizAttempts)
                .averageQuizScore(avgQuizScore)
                .build();
    }
    
    // Helper methods
    
    private boolean hasRecentActivity(Long studentId, List<Course> courses, LocalDateTime since) {
        return courses.stream()
                .flatMap(c -> progressRepository.findByStudentIdAndCourseId(studentId, c.getId()).stream())
                .anyMatch(p -> p.getLastAccessedAt().isAfter(since));
    }
    
    private double calculateCourseCompletionRate(Course course) {
        int totalStudents = course.getEnrolledStudents() != null ? 
                course.getEnrolledStudents().size() : 0;
        
        if (totalStudents == 0) return 0.0;
        
        long completedCount = course.getEnrolledStudents().stream()
                .filter(student -> isCourseCompleted(student.getId(), course.getId()))
                .count();
        
        return (completedCount * 100.0) / totalStudents;
    }
    
    private boolean isCourseCompleted(Long studentId, Long courseId) {
        List<Progress> progressList = progressRepository.findByStudentIdAndCourseId(studentId, courseId);
        int completedLessons = (int) progressList.stream()
                .filter(Progress::isCompleted)
                .count();
        
        int totalLessons = lessonRepository.countByCourseId(courseId).intValue();
        
        return totalLessons > 0 && completedLessons == totalLessons;
    }
    
    private double calculateStudentProgress(Long studentId, Long courseId) {
        List<Progress> progressList = progressRepository.findByStudentIdAndCourseId(studentId, courseId);
        int completedLessons = (int) progressList.stream()
                .filter(Progress::isCompleted)
                .count();
        
        int totalLessons = lessonRepository.countByCourseId(courseId).intValue();
        
        return totalLessons > 0 ? (completedLessons * 100.0 / totalLessons) : 0.0;
    }
}
