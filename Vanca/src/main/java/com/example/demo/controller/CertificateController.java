package com.example.demo.controller;

import com.example.demo.dto.request.CreateCertificateRequest;
import com.example.demo.dto.response.CertificateResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@Tag(name = "Certificate", description = "Certificate management APIs")
public class CertificateController {
	
	private final CertificateService certificateService;
	private final JwtTokenProvider tokenProvider;


	public CertificateController(CertificateService certificateService, JwtTokenProvider tokenProvider) {
		this.certificateService = certificateService;
		this.tokenProvider = tokenProvider;
	}
	
	@PostMapping("/generate")
	@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Generate certificate for a student", description = "Creates a certificate for a student who completed a course (ADMIN/INSTRUCTOR only)")
	public ResponseEntity<CertificateResponse> generateCertificate(
			@Valid @RequestBody CreateCertificateRequest request,
			HttpServletRequest httpRequest) {
		
		CertificateResponse certificate = certificateService.generateCertificate(request);
		return ResponseEntity.ok(certificate);
	}
	
	@GetMapping("/verify/{code}")
	@Operation(summary = "Verify certificate", description = "Public endpoint to verify a certificate by its code")
	public ResponseEntity<CertificateResponse> verifyCertificate(
			@PathVariable String code) {
		
		CertificateResponse certificate = certificateService.verifyCertificate(code);
		return ResponseEntity.ok(certificate);
	}
	
	@GetMapping("/my-certificates")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get my certificates", description = "Get all certificates for the logged-in user")
	public ResponseEntity<List<CertificateResponse>> getMyCertificates(
			HttpServletRequest request) {
		
		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);
		
		List<CertificateResponse> certificates = certificateService.getUserCertificates(userId);
		return ResponseEntity.ok(certificates);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get certificate by ID", description = "Get certificate details by ID")
	public ResponseEntity<CertificateResponse> getCertificateById(
			@PathVariable Long id) {
		
		CertificateResponse certificate = certificateService.getCertificateById(id);
		return ResponseEntity.ok(certificate);
	}
	
	@GetMapping("/course/{courseId}")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get certificate by course", description = "Get logged-in user's certificate for a specific course")
	public ResponseEntity<CertificateResponse> getCertificateByCourse(
			@PathVariable Long courseId,
			HttpServletRequest request) {
		
		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);
		
		CertificateResponse certificate = certificateService.getCertificateByCourse(userId, courseId);
		return ResponseEntity.ok(certificate);
	}
	
	@GetMapping("/{id}/download")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Download certificate as PDF", description = "Download certificate in PDF format")
	public ResponseEntity<byte[]> downloadCertificatePDF(@PathVariable Long id) {
		byte[] pdfBytes = certificateService.downloadCertificatePDF(id);
		
		return ResponseEntity.ok()
			.header("Content-Type", "application/pdf")
			.header("Content-Disposition", "attachment; filename=certificate-" + id + ".pdf")
			.body(pdfBytes);
	}
}
