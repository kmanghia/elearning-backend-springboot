package com.example.demo.controller;

import com.example.demo.dto.response.LessonResponse;
import com.example.demo.model.Lesson;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

	private final LessonService lessonService;

	@GetMapping("/course/{courseId}")
	public ResponseEntity<List<LessonResponse>> getLessonsByCourse(@PathVariable Long courseId) {
		return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
		return ResponseEntity.ok(lessonService.getLessonById(id));
	}

	@GetMapping("/{id}/video-url")
	@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<Map<String, String>> getVideoStreamingUrl(
		@PathVariable Long id,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		String videoUrl = lessonService.getVideoStreamingUrl(id, userPrincipal.getId());
		return ResponseEntity.ok(Map.of("videoUrl", videoUrl));
	}

	@PostMapping("/course/{courseId}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<LessonResponse> createLesson(
		@PathVariable Long courseId,
		@RequestBody Lesson lesson,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		LessonResponse created = lessonService.createLesson(courseId, lesson, userPrincipal.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<LessonResponse> updateLesson(
		@PathVariable Long id,
		@RequestBody Lesson lesson,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok(lessonService.updateLesson(id, lesson, userPrincipal.getId()));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<Void> deleteLesson(
		@PathVariable Long id,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		lessonService.deleteLesson(id, userPrincipal.getId());
		return ResponseEntity.noContent().build();
	}
}

