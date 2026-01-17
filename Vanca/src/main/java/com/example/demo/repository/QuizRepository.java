package com.example.demo.repository;

import com.example.demo.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	Optional<Quiz> findByLessonId(Long lessonId);
	
	@org.springframework.data.jpa.repository.Query("SELECT COUNT(q) FROM Quiz q WHERE q.lesson.course.id = :courseId")
	Long countByCourseId(@org.springframework.data.repository.query.Param("courseId") Long courseId);
}

