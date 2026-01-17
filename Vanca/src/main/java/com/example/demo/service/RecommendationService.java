package com.example.demo.service;

import com.example.demo.dto.response.RecommendationResponse;
import com.example.demo.dto.response.RecommendationResponse.CourseRecommendation;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseReviewRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

        private final CourseRepository courseRepository;
        private final CourseReviewRepository courseReviewRepository;

        public RecommendationService(CourseRepository courseRepository, CourseReviewRepository courseReviewRepository) {
                this.courseRepository = courseRepository;
                this.courseReviewRepository = courseReviewRepository;
        }

        /**
         * Get personalized course recommendations for a user
         * Uses collaborative filtering + category-based suggestions
         */
        public RecommendationResponse getPersonalizedRecommendations(Long userId, int limit) {
                // Get user's enrolled courses
                List<Course> enrolledCourses = courseRepository.findEnrolledCoursesByStudentId(userId);

                if (enrolledCourses.isEmpty()) {
                        // New user - return popular courses
                        return getPopularCourses(limit);
                }

                // Get categories user is interested in
                Set<Long> userCategoryIds = enrolledCourses.stream()
                                .map(c -> c.getCategory() != null ? c.getCategory().getId() : null)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet());

                // Get all published courses
                List<Course> allCourses = courseRepository.findPublishedCourses(Course.Status.PUBLISHED);

                // Filter out already enrolled courses
                Set<Long> enrolledCourseIds = enrolledCourses.stream()
                                .map(Course::getId)
                                .collect(Collectors.toSet());

                List<CourseRecommendation> recommendations = allCourses.stream()
                                .filter(course -> !enrolledCourseIds.contains(course.getId()))
                                .map(course -> {
                                        double score = calculateRelevanceScore(course, userCategoryIds,
                                                        enrolledCourses);
                                        String reason = generateReason(course, userCategoryIds);
                                        return mapToCourseRecommendation(course, reason, score);
                                })
                                .filter(rec -> rec.getRelevanceScore() > 0.0)
                                .sorted((a, b) -> Double.compare(b.getRelevanceScore(), a.getRelevanceScore()))
                                .limit(limit)
                                .collect(Collectors.toList());

                return RecommendationResponse.builder()
                                .recommendations(recommendations)
                                .recommendationType("PERSONALIZED")
                                .totalResults(recommendations.size())
                                .build();
        }

        /**
         * Get popular courses based on enrollment count and ratings
         */
        public RecommendationResponse getPopularCourses(int limit) {
                List<Course> allCourses = courseRepository.findPublishedCourses(Course.Status.PUBLISHED);

                List<CourseRecommendation> popularCourses = allCourses.stream()
                                .map(course -> {
                                        int enrollmentCount = course.getEnrolledStudents() != null
                                                        ? course.getEnrolledStudents().size()
                                                        : 0;
                                        double avgRating = courseReviewRepository.getAverageByCourseId(course.getId())
                                                        .orElse(0.0);

                                        // Popularity score: weighted combination of enrollments and ratings
                                        double popularityScore = (enrollmentCount * 0.7) + (avgRating * 10 * 0.3);

                                        String reason = "Popular course with " + enrollmentCount + " students";
                                        if (avgRating > 0) {
                                                reason += String.format(" and %.1f★ rating", avgRating);
                                        }

                                        CourseRecommendation rec = mapToCourseRecommendation(course, reason,
                                                        popularityScore);
                                        rec.setEnrollmentCount(enrollmentCount);
                                        rec.setAverageRating(avgRating);
                                        return rec;
                                })
                                .sorted((a, b) -> Double.compare(b.getRelevanceScore(), a.getRelevanceScore()))
                                .limit(limit)
                                .collect(Collectors.toList());

                return RecommendationResponse.builder()
                                .recommendations(popularCourses)
                                .recommendationType("POPULAR")
                                .totalResults(popularCourses.size())
                                .build();
        }

        /**
         * Get courses similar to a given course (same category)
         */
        public RecommendationResponse getSimilarCourses(Long courseId, int limit) {
                Course targetCourse = courseRepository.findById(courseId)
                                .orElseThrow(() -> new RuntimeException("Course not found"));

                if (targetCourse.getCategory() == null) {
                        return RecommendationResponse.builder()
                                        .recommendations(Collections.emptyList())
                                        .recommendationType("SIMILAR")
                                        .totalResults(0)
                                        .build();
                }

                List<Course> similarCourses = courseRepository.findByCategoryId(targetCourse.getCategory().getId());

                List<CourseRecommendation> recommendations = similarCourses.stream()
                                .filter(course -> !course.getId().equals(courseId))
                                .filter(course -> course.getStatus() == Course.Status.PUBLISHED)
                                .map(course -> {
                                        double avgRating = courseReviewRepository.getAverageByCourseId(course.getId())
                                                        .orElse(0.0);
                                        int enrollmentCount = course.getEnrolledStudents() != null
                                                        ? course.getEnrolledStudents().size()
                                                        : 0;

                                        String reason = "Similar to " + targetCourse.getTitle() +
                                                        " (same category: " + targetCourse.getCategory().getName()
                                                        + ")";

                                        CourseRecommendation rec = mapToCourseRecommendation(course, reason, avgRating);
                                        rec.setEnrollmentCount(enrollmentCount);
                                        rec.setAverageRating(avgRating);
                                        return rec;
                                })
                                .sorted((a, b) -> Double.compare(
                                                b.getAverageRating() != null ? b.getAverageRating() : 0.0,
                                                a.getAverageRating() != null ? a.getAverageRating() : 0.0))
                                .limit(limit)
                                .collect(Collectors.toList());

                return RecommendationResponse.builder()
                                .recommendations(recommendations)
                                .recommendationType("SIMILAR")
                                .totalResults(recommendations.size())
                                .build();
        }

        /**
         * Calculate relevance score for a course based on user preferences
         */
        private double calculateRelevanceScore(Course course, Set<Long> userCategoryIds,
                        List<Course> enrolledCourses) {
                double score = 0.0;

                // Category match (highest weight)
                if (course.getCategory() != null && userCategoryIds.contains(course.getCategory().getId())) {
                        score += 50.0;
                }

                // Course rating
                double avgRating = courseReviewRepository.getAverageByCourseId(course.getId()).orElse(0.0);
                score += avgRating * 8; // Max 40 points for 5-star rating

                // Enrollment count (popularity)
                int enrollmentCount = course.getEnrolledStudents() != null ? course.getEnrolledStudents().size() : 0;
                score += Math.min(enrollmentCount * 0.5, 30.0); // Max 30 points

                // Normalize to 0.0-1.0 range
                return Math.min(score / 100.0, 1.0);
        }

        /**
         * Generate human-readable reason for recommendation
         */
        private String generateReason(Course course, Set<Long> userCategoryIds) {
                if (course.getCategory() != null && userCategoryIds.contains(course.getCategory().getId())) {
                        return "Matches your interest in " + course.getCategory().getName();
                }

                double avgRating = courseReviewRepository.getAverageByCourseId(course.getId()).orElse(0.0);
                if (avgRating >= 4.0) {
                        return String.format("Highly rated course (%.1f★)", avgRating);
                }

                int enrollmentCount = course.getEnrolledStudents() != null ? course.getEnrolledStudents().size() : 0;
                if (enrollmentCount > 50) {
                        return "Popular among students";
                }

                return "Recommended for you";
        }

        /**
         * Map Course entity to CourseRecommendation DTO
         */
        private CourseRecommendation mapToCourseRecommendation(Course course, String reason, double score) {
                return CourseRecommendation.builder()
                                .courseId(course.getId())
                                .title(course.getTitle())
                                .description(course.getDescription())
                                .thumbnailUrl(course.getThumbnailUrl())
                                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName()
                                                : "Unknown")
                                .categoryName(course.getCategory() != null ? course.getCategory().getName()
                                                : "Uncategorized")
                                .reason(reason)
                                .relevanceScore(score)
                                .build();
        }
}
