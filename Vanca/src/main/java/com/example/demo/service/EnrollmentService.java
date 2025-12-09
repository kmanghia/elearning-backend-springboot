package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;

	@Transactional
	public Enrollment enrollInCourse(Long courseId, Long studentId) {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		if (course.getStatus() != Course.Status.PUBLISHED) {
			throw new BadRequestException("Course is not published");
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

		return enrollmentRepository.save(enrollment);
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
}

