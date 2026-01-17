package com.example.demo.repository;

import com.example.demo.model.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
	List<CourseReview> findByCourseId(Long courseId);
	Optional<CourseReview> findByStudentIdAndCourseId(Long studentId, Long courseId);
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
	
	@Query("SELECT AVG(r.rating) FROM CourseReview r WHERE r.course.id = :courseId")
	Double getAverageRatingByCourseId(Long courseId);
	
	@Query("SELECT COUNT(r) FROM CourseReview r WHERE r.course.id = :courseId")
	Long getReviewCountByCourseId(Long courseId);
	
	// Convenience methods for analytics
	default java.util.Optional<Double> getAverageByCourseId(Long courseId) {
		Double avg = getAverageRatingByCourseId(courseId);
		return avg != null ? java.util.Optional.of(avg) : java.util.Optional.empty();
	}
	
	default Long countByCourseId(Long courseId) {
		return getReviewCountByCourseId(courseId);
	}
}
