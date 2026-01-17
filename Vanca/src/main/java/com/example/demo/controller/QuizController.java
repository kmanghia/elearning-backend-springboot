package com.example.demo.controller;

import com.example.demo.dto.request.SubmitQuizRequest;
import com.example.demo.dto.response.QuizAttemptResponse;
import com.example.demo.dto.response.QuizResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

	private final QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long id) {
		var quiz = quizService.getQuiz(id);
		return ResponseEntity.ok(mapQuizToResponse(quiz));
	}

	@PostMapping("/{id}/attempt")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<QuizAttemptResponse> startQuizAttempt(
			@PathVariable Long id,
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		var attempt = quizService.startQuizAttempt(id, userPrincipal.getId());
		return ResponseEntity.ok(mapToResponse(attempt));
	}

	@PostMapping("/attempts/{attemptId}/submit")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<QuizAttemptResponse> submitQuiz(
			@PathVariable Long attemptId,
			@Valid @RequestBody SubmitQuizRequest request) {
		var attempt = quizService.submitQuiz(attemptId, request.getAnswers());
		return ResponseEntity.ok(mapToResponse(attempt));
	}

	@GetMapping("/{id}/results")
	@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<List<QuizAttemptResponse>> getQuizResults(
			@PathVariable Long id,
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		var attempts = quizService.getQuizResults(id, userPrincipal.getId());
		return ResponseEntity.ok(attempts.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList()));
	}

	private QuizAttemptResponse mapToResponse(com.example.demo.model.QuizAttempt attempt) {
		int totalQuestions = 0;
		long correctAnswers = 0;

		if (attempt.getQuiz() != null && attempt.getQuiz().getQuestions() != null) {
			totalQuestions = attempt.getQuiz().getQuestions().size();
		}

		if (attempt.getAnswers() != null) {
			correctAnswers = attempt.getAnswers().stream()
					.filter(a -> a.getIsCorrect() != null && a.getIsCorrect())
					.count();
		}

		return QuizAttemptResponse.builder()
				.id(attempt.getId())
				.quizId(attempt.getQuiz() != null ? attempt.getQuiz().getId() : null)
				.startedAt(attempt.getStartedAt())
				.submittedAt(attempt.getSubmittedAt())
				.score(attempt.getScore() != null ? attempt.getScore() : 0)
				.isPassed(attempt.getIsPassed() != null ? attempt.getIsPassed() : false)
				.totalQuestions(totalQuestions)
				.correctAnswers((int) correctAnswers)
				.build();
	}

	private QuizResponse mapQuizToResponse(com.example.demo.model.Quiz quiz) {
		return QuizResponse.builder()
				.id(quiz.getId())
				.lessonId(quiz.getLesson() != null ? quiz.getLesson().getId() : null)
				.title(quiz.getTitle())
				.description(quiz.getDescription())
				.timeLimitMinutes(quiz.getTimeLimitMinutes())
				.passingScore(quiz.getPassingScore())
				.maxAttempts(quiz.getMaxAttempts())
				.createdAt(quiz.getCreatedAt())
				.updatedAt(quiz.getUpdatedAt())
				.questions(quiz.getQuestions() != null ? quiz.getQuestions().stream()
						.map(this::mapQuestionToResponse)
						.collect(Collectors.toList()) : null)
				.build();
	}

	private com.example.demo.dto.response.QuestionResponse mapQuestionToResponse(
			com.example.demo.model.Question question) {
		return com.example.demo.dto.response.QuestionResponse.builder()
				.id(question.getId())
				.content(question.getContent())
				.questionType(question.getQuestionType())
				.points(question.getPoints())
				.orderIndex(question.getOrderIndex())
				.options(question.getOptions() != null ? question.getOptions().stream()
						.map(this::mapOptionToResponse)
						.collect(Collectors.toList()) : null)
				.build();
	}

	private com.example.demo.dto.response.QuestionOptionResponse mapOptionToResponse(
			com.example.demo.model.QuestionOption option) {
		return com.example.demo.dto.response.QuestionOptionResponse.builder()
				.id(option.getId())
				.content(option.getContent())
				.orderIndex(option.getOrderIndex())
				.build();
	}
}
