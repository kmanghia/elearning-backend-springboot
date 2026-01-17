package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
	
	// Bug #2 Fix: Count enrollments efficiently without loading collection
	@Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId")
	Long countByCourseId(@Param("courseId") Long courseId);
	
	@Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
	Page<Enrollment> findByStudentId(@Param("studentId") Long studentId, Pageable pageable);
}

