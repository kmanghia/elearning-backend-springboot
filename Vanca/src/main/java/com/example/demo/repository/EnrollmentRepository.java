package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
	
	@Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
	java.util.List<Enrollment> findByStudentId(@Param("studentId") Long studentId);
}

