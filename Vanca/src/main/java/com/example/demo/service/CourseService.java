package com.example.demo.service;

import com.example.demo.dto.request.CreateCourseRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;

	public List<CourseResponse> getAllPublishedCourses() {
		return courseRepository.findPublishedCourses(Course.Status.PUBLISHED)
			.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}

	public CourseResponse getCourseById(Long id) {
		Course course = courseRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		return mapToResponse(course);
	}

	public List<CourseResponse> getCoursesByInstructor(Long instructorId) {
		User instructor = userRepository.findById(instructorId)
			.orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
		return courseRepository.findByInstructor(instructor)
			.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}

	@Transactional
	public CourseResponse createCourse(CreateCourseRequest request, Long instructorId) {
		User instructor = userRepository.findById(instructorId)
			.orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

		Course course = new Course();
		course.setTitle(request.getTitle());
		course.setDescription(request.getDescription());
		course.setInstructor(instructor);
		course.setPrice(request.getPrice());
		course.setThumbnailUrl(request.getThumbnailUrl());
		course.setStatus(request.getStatus());

		course = courseRepository.save(course);
		return mapToResponse(course);
	}

	@Transactional
	public CourseResponse updateCourse(Long courseId, CreateCourseRequest request, Long userId) {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		// Check if user is the instructor or admin
		if (!course.getInstructor().getId().equals(userId)) {
			User user = userRepository.findById(userId).orElseThrow();
			if (user.getRole() != User.Role.ADMIN) {
				throw new UnauthorizedException("You don't have permission to update this course");
			}
		}

		if (request.getTitle() != null) {
			course.setTitle(request.getTitle());
		}
		if (request.getDescription() != null) {
			course.setDescription(request.getDescription());
		}
		if (request.getPrice() != null) {
			course.setPrice(request.getPrice());
		}
		if (request.getThumbnailUrl() != null) {
			course.setThumbnailUrl(request.getThumbnailUrl());
		}
		if (request.getStatus() != null) {
			course.setStatus(request.getStatus());
		}

		course = courseRepository.save(course);
		return mapToResponse(course);
	}

	@Transactional
	public void deleteCourse(Long courseId, Long userId) {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		User user = userRepository.findById(userId).orElseThrow();
		if (!course.getInstructor().getId().equals(userId) && user.getRole() != User.Role.ADMIN) {
			throw new UnauthorizedException("You don't have permission to delete this course");
		}

		courseRepository.delete(course);
	}

	private CourseResponse mapToResponse(Course course) {
		return CourseResponse.builder()
			.id(course.getId())
			.title(course.getTitle())
			.description(course.getDescription())
			.instructorId(course.getInstructor().getId())
			.instructorName(course.getInstructor().getFullName())
			.price(course.getPrice())
			.thumbnailUrl(course.getThumbnailUrl())
			.status(course.getStatus())
			.createdAt(course.getCreatedAt())
			.updatedAt(course.getUpdatedAt())
			.lessonCount(course.getLessons() != null ? course.getLessons().size() : 0)
			.enrolledStudentCount(course.getEnrolledStudents() != null ? 
				course.getEnrolledStudents().size() : 0)
			.build();
	}
}

