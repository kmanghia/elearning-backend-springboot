package com.example.demo.controller;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.UpdateProfileRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok(userService.getUserById(userPrincipal.getId()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PutMapping("/me/profile")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserResponse> updateProfile(
		@Valid @RequestBody UpdateProfileRequest request,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok(userService.updateProfile(
			userPrincipal.getId(), 
			request.getFullName(), 
			request.getAvatarUrl()));
	}

	@PutMapping("/me/password")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> changePassword(
		@Valid @RequestBody ChangePasswordRequest request,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		userService.changePassword(
			userPrincipal.getId(),
			request.getCurrentPassword(),
			request.getNewPassword());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/disable")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> disableUser(@PathVariable Long id) {
		userService.disableUser(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/enable")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> enableUser(@PathVariable Long id) {
		userService.enableUser(id);
		return ResponseEntity.noContent().build();
	}
}
