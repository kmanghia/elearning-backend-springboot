package com.example.demo.service;

import com.example.demo.dto.request.CreateCertificateRequest;
import com.example.demo.dto.response.CertificateResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Certificate;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Notification.NotificationType;
import com.example.demo.model.User;
import com.example.demo.repository.CertificateRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
	
	private final CertificateRepository certificateRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final AwsS3Service awsS3Service;
	private final NotificationService notificationService;
	
	@Transactional
	public CertificateResponse generateCertificate(CreateCertificateRequest request) {
		User student = userRepository.findById(request.getStudentId())
			.orElseThrow(() -> new ResourceNotFoundException("Student not found"));
		
		Course course = courseRepository.findById(request.getCourseId())
			.orElseThrow(() -> new ResourceNotFoundException("Course not found"));
		
		// Check if student has completed the course
		Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(
			request.getStudentId(), request.getCourseId())
			.orElseThrow(() -> new IllegalStateException("Student is not enrolled in this course"));
		
		if (enrollment.getProgress() == null || enrollment.getProgress() < 100) {
			throw new IllegalStateException("Student has not completed the course yet");
		}
		
		// Check if certificate already exists
		if (certificateRepository.existsByStudentIdAndCourseId(
			request.getStudentId(), request.getCourseId())) {
			throw new IllegalStateException("Certificate already exists for this course");
		}
		
		// Generate unique certificate code
		String certificateCode = generateUniqueCertificateCode();
		
		// TODO: Generate PDF certificate and upload to S3
		// For now, we'll use a placeholder URL
		String pdfUrl = "https://certificates.elearning.com/" + certificateCode + ".pdf";
		
		// Create certificate
		Certificate certificate = new Certificate();
		certificate.setStudent(student);
		certificate.setCourse(course);
		certificate.setCertificateCode(certificateCode);
		certificate.setPdfUrl(pdfUrl);
		certificate.setCompletionDate(LocalDateTime.now());
		
		Certificate savedCertificate = certificateRepository.save(certificate);
		
		// Send notification to student
		notificationService.createNotification(
			request.getStudentId(),
			"Certificate Issued",
			String.format("Congratulations! You have earned a certificate for completing '%s'", course.getTitle()),
			NotificationType.CERTIFICATE_ISSUED,
			"CERTIFICATE",
			savedCertificate.getId()
		);
		
		return mapToResponse(savedCertificate);
	}
	
	public CertificateResponse verifyCertificate(String certificateCode) {
		Certificate certificate = certificateRepository.findByCertificateCode(certificateCode)
			.orElseThrow(() -> new ResourceNotFoundException("Certificate not found"));
		
		return mapToResponse(certificate);
	}
	
	public List<CertificateResponse> getUserCertificates(Long userId) {
		List<Certificate> certificates = certificateRepository.findByStudentId(userId);
		return certificates.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	public CertificateResponse getCertificateByCourse(Long userId, Long courseId) {
		Certificate certificate = certificateRepository.findByStudentIdAndCourseId(userId, courseId)
			.orElseThrow(() -> new ResourceNotFoundException("Certificate not found for this course"));
		
		return mapToResponse(certificate);
	}
	
	public CertificateResponse getCertificateById(Long certificateId) {
		Certificate certificate = certificateRepository.findById(certificateId)
			.orElseThrow(() -> new ResourceNotFoundException("Certificate not found"));
		
		return mapToResponse(certificate);
	}
	
	public byte[] downloadCertificatePDF(Long certificateId) {
		Certificate certificate = certificateRepository.findById(certificateId)
			.orElseThrow(() -> new ResourceNotFoundException("Certificate not found"));
		
		try {
			String verificationUrl = "https://elearning.com/verify/" + certificate.getCertificateCode();
			
			return com.example.demo.util.CertificateTemplate.generateCertificatePDF(
				certificate.getCertificateCode(),
				certificate.getStudent().getFullName(),
				certificate.getCourse().getTitle(),
				certificate.getIssuedAt().format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
				verificationUrl
			);
		} catch (Exception e) {
			throw new RuntimeException("Failed to generate PDF certificate", e);
		}
	}
	
	/**
	 * Bug #9 Fix: Generate unique certificate code with retry limit
	 * Note: This still has a small race condition window, but significantly reduced.
	 * For production, consider adding a unique constraint on certificate_code in DB.
	 */
	private String generateUniqueCertificateCode() {
		int maxAttempts = 10;
		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			String code = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
			
			if (!certificateRepository.findByCertificateCode(code).isPresent()) {
				return code;
			}
		}
		
		throw new IllegalStateException("Failed to generate unique certificate code after 10 attempts");
	}
	
	private CertificateResponse mapToResponse(Certificate certificate) {
		return CertificateResponse.builder()
			.id(certificate.getId())
			.studentId(certificate.getStudent().getId())
			.studentName(certificate.getStudent().getFullName())
			.courseId(certificate.getCourse().getId())
			.courseTitle(certificate.getCourse().getTitle())
			.certificateCode(certificate.getCertificateCode())
			.pdfUrl(certificate.getPdfUrl())
			.issuedAt(certificate.getIssuedAt())
			.completionDate(certificate.getCompletionDate())
			.build();
	}
}
