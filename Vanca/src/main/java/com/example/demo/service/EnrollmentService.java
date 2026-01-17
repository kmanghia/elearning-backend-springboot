package com.example.demo.service;

import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Notification.NotificationType;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;


	public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository, NotificationService notificationService) {
		this.enrollmentRepository = enrollmentRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}

	@Transactional
	public EnrollmentResponse enrollInCourse(Long courseId, Long studentId) {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		// Bug #6 Fix: Add null check for course.getStatus()
		if (course.getStatus() == null || course.getStatus() != Course.Status.PUBLISHED) {
			throw new BadRequestException("Course is not published");
		}

		// Check if course requires payment
		if (course.getPrice() != null && course.getPrice().compareTo(java.math.BigDecimal.ZERO) > 0) {
			throw new BadRequestException(
				"This is a paid course. Please complete payment via POST /api/payments/create-intent/" + courseId
			);
		}

		// Bug #7 Fix: Prevent instructor from enrolling in their own course
		if (course.getInstructor() != null && course.getInstructor().getId().equals(studentId)) {
			throw new BadRequestException("Instructors cannot enroll in their own courses");
		}

		if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
			throw new BadRequestException("Already enrolled in this course");
		}

		User student = userRepository.findById(studentId)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);
		enrollment.setProgress(0);
		
		Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
		
		// Send notification to student
		notificationService.createNotification(
			studentId,
			"Enrollment Successful",
			"You have successfully enrolled in " + course.getTitle(),
			NotificationType.ENROLLMENT,
			"COURSE",
			course.getId()
		);

		return mapToResponse(savedEnrollment);
	}

	@Transactional
	public void unenrollFromCourse(Long courseId, Long studentId) {
		Enrollment enrollment = enrollmentRepository
			.findByStudentIdAndCourseId(studentId, courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

		enrollmentRepository.delete(enrollment);
	}

	public boolean isEnrolled(Long courseId, Long studentId) {
		return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
	}

	public Page<EnrollmentResponse> getMyEnrollments(Long studentId, Pageable pageable) {
		Page<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId, pageable);
		return enrollments.map(this::mapToResponse);
	}

	private EnrollmentResponse mapToResponse(Enrollment enrollment) {
		return EnrollmentResponse.builder()
			.id(enrollment.getId())
			.courseId(enrollment.getCourse().getId())
			.courseName(enrollment.getCourse().getTitle())
			.studentId(enrollment.getStudent().getId())
			.studentName(enrollment.getStudent().getFullName())
			.enrolledAt(enrollment.getEnrolledAt())
			.progress(enrollment.getProgress())
			.build();
	}
}


