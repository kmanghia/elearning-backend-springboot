package com.example.demo.repository;

import com.example.demo.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	List<Certificate> findByStudentId(Long studentId);
	Optional<Certificate> findByStudentIdAndCourseId(Long studentId, Long courseId);
	Optional<Certificate> findByCertificateCode(String certificateCode);
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
