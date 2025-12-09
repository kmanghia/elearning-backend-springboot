package com.example.demo.repository;

import com.example.demo.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
	Optional<Progress> findByStudentIdAndLessonId(Long studentId, Long lessonId);
	
	@Query("SELECT p FROM Progress p WHERE p.student.id = :studentId AND p.lesson.course.id = :courseId")
	List<Progress> findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}

