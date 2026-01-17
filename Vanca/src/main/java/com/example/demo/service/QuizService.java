package com.example.demo.service;

import com.example.demo.dto.request.SubmitQuizRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.model.Notification.NotificationType;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

	private final QuizRepository quizRepository;
	private final QuizAttemptRepository attemptRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final NotificationService notificationService;

	/**
	 * Start a new quiz attempt
	 */
	@Transactional
	public QuizAttempt startQuizAttempt(Long quizId, Long studentId) {
		Quiz quiz = quizRepository.findById(quizId)
			.orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

		// Check if student is enrolled in the course
		if (!enrollmentRepository.existsByStudentIdAndCourseId(
			studentId, quiz.getLesson().getCourse().getId())) {
			throw new BadRequestException("Student is not enrolled in this course");
		}

		// Check max attempts
		if (quiz.getMaxAttempts() != null) {
			Long attemptCount = attemptRepository.countAttemptsByStudentAndQuiz(studentId, quizId);
			if (attemptCount >= quiz.getMaxAttempts()) {
				throw new BadRequestException("Maximum attempts reached for this quiz");
			}
		}

		QuizAttempt attempt = new QuizAttempt();
		User student = new User();
		student.setId(studentId);
		attempt.setStudent(student);
		attempt.setQuiz(quiz);
		attempt.setStartedAt(LocalDateTime.now());

		return attemptRepository.save(attempt);
	}

	/**
	 * Submit quiz and auto-grade
	 */
	@Transactional
	public QuizAttempt submitQuiz(Long attemptId, List<SubmitQuizRequest.QuizAnswerSubmission> answers) {
		QuizAttempt attempt = attemptRepository.findById(attemptId)
			.orElseThrow(() -> new ResourceNotFoundException("Quiz attempt not found"));

		if (attempt.getSubmittedAt() != null) {
			throw new BadRequestException("Quiz already submitted");
		}

		Quiz quiz = attempt.getQuiz();
		List<Question> questions = quiz.getQuestions();

		// Create answers and grade
		int totalPoints = 0;
		int earnedPoints = 0;

		for (Question question : questions) {
			totalPoints += question.getPoints() != null ? question.getPoints() : 1;

			SubmitQuizRequest.QuizAnswerSubmission submission = answers.stream()
				.filter(a -> a.getQuestionId() != null && a.getQuestionId().equals(question.getId()))
				.findFirst()
				.orElse(null);

			if (submission != null) {
				QuizAnswer answer = createAnswer(attempt, question, submission);
				if (attempt.getAnswers() == null) {
					attempt.setAnswers(new java.util.ArrayList<>());
				}
				attempt.getAnswers().add(answer);

				if (answer.getIsCorrect() != null && answer.getIsCorrect()) {
					earnedPoints += question.getPoints() != null ? question.getPoints() : 1;
				}
			}
		}

		// Calculate score
		int score = totalPoints > 0 ? (earnedPoints * 100) / totalPoints : 0;
		attempt.setScore(score);
		attempt.setIsPassed(score >= quiz.getPassingScore());
		attempt.setSubmittedAt(LocalDateTime.now());
		
		QuizAttempt savedAttempt = attemptRepository.save(attempt);
		
		// Send notification to student
		notificationService.createNotification(
			attempt.getStudent().getId(),
			"Quiz Graded",
			String.format("Your quiz '%s' has been graded. Score: %d%% (%s)",
				quiz.getTitle(), score, attempt.getIsPassed() ? "Passed" : "Failed"),
			NotificationType.QUIZ_GRADED,
			"QUIZ",
			quiz.getId()
		);

		return savedAttempt;
	}

	/**
	 * Create and grade a single answer
	 */
	private QuizAnswer createAnswer(QuizAttempt attempt, Question question, 
		SubmitQuizRequest.QuizAnswerSubmission submission) {
		QuizAnswer answer = new QuizAnswer();
		answer.setAttempt(attempt);
		answer.setQuestion(question);

		Set<QuestionOption> selectedOptions = submission.getSelectedOptionIds().stream()
			.map(optionId -> {
				if (question.getOptions() == null) {
					return null;
				}
				return question.getOptions().stream()
					.filter(opt -> opt.getId() != null && opt.getId().equals(optionId))
					.findFirst()
					.orElse(null);
			})
			.filter(opt -> opt != null)
			.collect(Collectors.toSet());

		answer.setSelectedOptions(selectedOptions);

		// Grade the answer
		boolean isCorrect = gradeAnswer(question, selectedOptions);
		answer.setIsCorrect(isCorrect);

		return answer;
	}

	/**
	 * Grade answer based on question type
	 */
	private boolean gradeAnswer(Question question, Set<QuestionOption> selectedOptions) {
		if (question.getOptions() == null || question.getOptions().isEmpty()) {
			return false;
		}

		Set<QuestionOption> correctOptions = question.getOptions().stream()
			.filter(opt -> opt.getIsCorrect() != null && opt.getIsCorrect())
			.collect(Collectors.toSet());

		if (question.getQuestionType() == null) {
			return false;
		}

		switch (question.getQuestionType()) {
			case SINGLE_CHOICE:
				return selectedOptions.size() == 1 
					&& correctOptions.size() == 1
					&& selectedOptions.equals(correctOptions);

			case MULTIPLE_CHOICE:
				return selectedOptions.size() == correctOptions.size()
					&& selectedOptions.equals(correctOptions);

			case TRUE_FALSE:
				return selectedOptions.size() == 1
					&& correctOptions.size() == 1
					&& selectedOptions.equals(correctOptions);

			default:
				return false;
		}
	}

	/**
	 * Get quiz results for a student
	 */
	public List<QuizAttempt> getQuizResults(Long quizId, Long studentId) {
		return attemptRepository.findByStudentIdAndQuizId(studentId, quizId);
	}

	/**
	 * Get quiz by ID with questions
	 */
	public Quiz getQuiz(Long quizId) {
		return quizRepository.findById(quizId)
			.orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
	}
}
