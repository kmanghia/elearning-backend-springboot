package com.example.demo.controller;

import com.example.demo.dto.request.UpdateProgressRequest;
import com.example.demo.dto.response.CourseProgressResponse;
import com.example.demo.dto.response.LessonProgressResponse;
import com.example.demo.dto.response.ProgressResponse;
import com.example.demo.model.Progress;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

	private final ProgressService progressService;

	@GetMapping("/courses/{courseId}")
	@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<ProgressResponse> getCourseProgress(
		@PathVariable Long courseId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CourseProgressResponse courseProgress = progressService.getCourseProgress(courseId, userPrincipal.getId());
		return ResponseEntity.ok(mapToResponse(courseProgress));
	}

	@PutMapping("/lessons/{lessonId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<LessonProgressResponse> updateLessonProgress(
		@PathVariable Long lessonId,
		@RequestBody UpdateProgressRequest request,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		Progress progress = progressService.updateLessonProgress(
			lessonId, userPrincipal.getId(), request.getWatchedDurationSeconds());
		return ResponseEntity.ok(mapLessonProgressToResponse(progress));
	}

	private ProgressResponse mapToResponse(CourseProgressResponse dto) {
		List<LessonProgressResponse> lessonProgress = dto.getLessonProgress() != null
			? dto.getLessonProgress().stream()
				.map(lp -> LessonProgressResponse.builder()
					.lessonId(lp.getLessonId())
					.lessonTitle(lp.getLessonTitle())
					.watchedDurationSeconds(lp.getWatchedDurationSeconds())
					.completionPercentage(lp.getCompletionPercentage())
					.isCompleted(lp.getIsCompleted())
					.build())
				.collect(Collectors.toList())
			: List.of();

		return ProgressResponse.builder()
			.courseId(dto.getCourseId())
			.totalLessons(dto.getTotalLessons())
			.completedLessons(dto.getCompletedLessons())
			.courseProgress(dto.getCourseProgress())
			.lessonProgress(lessonProgress)
			.build();
	}

	private LessonProgressResponse mapLessonProgressToResponse(Progress progress) {
		return LessonProgressResponse.builder()
			.lessonId(progress.getLesson().getId())
			.lessonTitle(progress.getLesson().getTitle())
			.watchedDurationSeconds(progress.getWatchedDurationSeconds())
			.totalDurationSeconds(progress.getLesson().getDurationSeconds())
			.completionPercentage(progress.getCompletionPercentage())
			.isCompleted(progress.getIsCompleted())
			.lastWatchedAt(progress.getLastWatchedAt())
			.build();
	}
}

