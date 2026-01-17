package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.JwtAuthenticationResponse;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;


	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<JwtAuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
		JwtAuthenticationResponse response = authService.register(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
		JwtAuthenticationResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}
}

