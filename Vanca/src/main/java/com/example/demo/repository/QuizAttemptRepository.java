package com.example.demo.repository;

import com.example.demo.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
	List<QuizAttempt> findByStudentIdAndQuizId(Long studentId, Long quizId);
	
	@Query("SELECT COUNT(a) FROM QuizAttempt a WHERE a.student.id = :studentId AND a.quiz.id = :quizId")
	Long countAttemptsByStudentAndQuiz(@Param("studentId") Long studentId, @Param("quizId") Long quizId);
	
	Optional<QuizAttempt> findTopByStudentIdAndQuizIdOrderByStartedAtDesc(Long studentId, Long quizId);
}

