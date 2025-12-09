package com.example.demo.service;

import com.example.demo.dto.response.LessonResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

	private final LessonRepository lessonRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final AwsS3Service awsS3Service;

	public List<LessonResponse> getLessonsByCourse(Long courseId) {
		return lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId)
			.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}

	public LessonResponse getLessonById(Long id) {
		Lesson lesson = lessonRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
		return mapToResponse(lesson);
	}

	public String getVideoStreamingUrl(Long lessonId, Long userId) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

		// Check if user is enrolled or is instructor/admin
		Course course = lesson.getCourse();
		User user = userRepository.findById(userId).orElseThrow();
		
		boolean isEnrolled = course.getEnrolledStudents().stream()
			.anyMatch(s -> s.getId().equals(userId));
		boolean isInstructor = course.getInstructor().getId().equals(userId);
		boolean isAdmin = user.getRole() == User.Role.ADMIN;

		if (!isEnrolled && !isInstructor && !isAdmin) {
			throw new UnauthorizedException("You must be enrolled in the course to view this lesson");
		}

		if (lesson.getVideoUrl() == null || lesson.getVideoUrl().isEmpty()) {
			throw new ResourceNotFoundException("Video not found for this lesson");
		}

		String videoUrl = lesson.getVideoUrl();
		
		// Check if it's an S3 URL (s3:// or amazonaws.com)
		if (isS3Url(videoUrl)) {
			// Use AWS S3 pre-signed URL if AWS is configured
			try {
				String videoKey = extractS3Key(videoUrl);
				return awsS3Service.generatePresignedUrl(videoKey);
			} catch (RuntimeException e) {
				// If AWS is not configured, return original URL
				// This allows the app to work without AWS (for development/testing)
				// In production, you should configure AWS credentials
				return videoUrl;
			}
		}
		
		// For other URLs (YouTube, Vimeo, direct links, local files), return as-is
		return videoUrl;
	}

	@Transactional
	public LessonResponse createLesson(Long courseId, Lesson lesson, Long instructorId) {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		if (!course.getInstructor().getId().equals(instructorId)) {
			User user = userRepository.findById(instructorId).orElseThrow();
			if (user.getRole() != User.Role.ADMIN) {
				throw new UnauthorizedException("You don't have permission to create lessons for this course");
			}
		}

		lesson.setCourse(course);
		if (lesson.getOrderIndex() == null) {
			List<Lesson> existingLessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
			lesson.setOrderIndex(existingLessons.size() + 1);
		}

		lesson = lessonRepository.save(lesson);
		return mapToResponse(lesson);
	}

	@Transactional
	public LessonResponse updateLesson(Long lessonId, Lesson lessonDetails, Long userId) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

		Course course = lesson.getCourse();
		User user = userRepository.findById(userId).orElseThrow();

		if (!course.getInstructor().getId().equals(userId) && user.getRole() != User.Role.ADMIN) {
			throw new UnauthorizedException("You don't have permission to update this lesson");
		}

		if (lessonDetails.getTitle() != null) {
			lesson.setTitle(lessonDetails.getTitle());
		}
		if (lessonDetails.getDescription() != null) {
			lesson.setDescription(lessonDetails.getDescription());
		}
		if (lessonDetails.getVideoUrl() != null) {
			lesson.setVideoUrl(lessonDetails.getVideoUrl());
		}
		if (lessonDetails.getDurationSeconds() != null) {
			lesson.setDurationSeconds(lessonDetails.getDurationSeconds());
		}
		if (lessonDetails.getIsPreview() != null) {
			lesson.setIsPreview(lessonDetails.getIsPreview());
		}

		lesson = lessonRepository.save(lesson);
		return mapToResponse(lesson);
	}

	@Transactional
	public void deleteLesson(Long lessonId, Long userId) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

		Course course = lesson.getCourse();
		User user = userRepository.findById(userId).orElseThrow();

		if (!course.getInstructor().getId().equals(userId) && user.getRole() != User.Role.ADMIN) {
			throw new UnauthorizedException("You don't have permission to delete this lesson");
		}

		lessonRepository.delete(lesson);
	}

	private LessonResponse mapToResponse(Lesson lesson) {
		return LessonResponse.builder()
			.id(lesson.getId())
			.courseId(lesson.getCourse().getId())
			.title(lesson.getTitle())
			.description(lesson.getDescription())
			.orderIndex(lesson.getOrderIndex())
			.videoUrl(lesson.getVideoUrl())
			.durationSeconds(lesson.getDurationSeconds())
			.isPreview(lesson.getIsPreview())
			.createdAt(lesson.getCreatedAt())
			.updatedAt(lesson.getUpdatedAt())
			.hasQuiz(lesson.getQuiz() != null)
			.build();
	}

	/**
	 * Check if URL is an AWS S3 URL
	 */
	private boolean isS3Url(String videoUrl) {
		return videoUrl.startsWith("s3://") || 
		       videoUrl.contains("amazonaws.com") ||
		       videoUrl.contains("s3.amazonaws.com");
	}

	/**
	 * Extract S3 key from S3 URL
	 */
	private String extractS3Key(String videoUrl) {
		// Handle different URL formats
		if (videoUrl.startsWith("s3://")) {
			// Format: s3://bucket/key
			return videoUrl.substring(videoUrl.indexOf("/", 5) + 1);
		} else if (videoUrl.contains("amazonaws.com/")) {
			// Format: https://bucket.s3.region.amazonaws.com/key
			return videoUrl.substring(videoUrl.indexOf("amazonaws.com/") + 14);
		}
		// Assume it's already a key
		return videoUrl;
	}
}

