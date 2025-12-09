package com.example.demo.repository;

import com.example.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
	List<Lesson> findByCourseIdOrderByOrderIndexAsc(Long courseId);
	
	@Query("SELECT l FROM Lesson l WHERE l.course.id = :courseId AND l.orderIndex = :orderIndex")
	Optional<Lesson> findByCourseIdAndOrderIndex(@Param("courseId") Long courseId, @Param("orderIndex") Integer orderIndex);
}

