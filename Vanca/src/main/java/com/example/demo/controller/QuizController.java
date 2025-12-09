package com.example.demo.controller;

import com.example.demo.dto.response.QuizAttemptResponse;
import com.example.demo.dto.response.QuizResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

	private final QuizService quizService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long id) {
		// TODO: Implement getQuiz method in QuizService and return proper QuizResponse
		// For now, return not implemented
		return ResponseEntity.status(501).build();
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
		@RequestBody List<QuizService.QuizAnswerSubmission> answers) {
		var attempt = quizService.submitQuiz(attemptId, answers);
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
}

