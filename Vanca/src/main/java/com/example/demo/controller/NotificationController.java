package com.example.demo.controller;

import com.example.demo.dto.response.NotificationResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification", description = "User notification management APIs")
public class NotificationController {

	private final NotificationService notificationService;
	private final JwtTokenProvider tokenProvider;

	public NotificationController(NotificationService notificationService, JwtTokenProvider tokenProvider) {
		this.notificationService = notificationService;
		this.tokenProvider = tokenProvider;
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get all notifications", description = "Get all notifications for the logged-in user with pagination")
	public ResponseEntity<Page<NotificationResponse>> getAllNotifications(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			HttpServletRequest request) {

		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<NotificationResponse> notifications = notificationService.getUserNotifications(userId, pageable);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/unread")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get unread notifications", description = "Get only unread notifications for the logged-in user with pagination")
	public ResponseEntity<Page<NotificationResponse>> getUnreadNotifications(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			HttpServletRequest request) {

		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<NotificationResponse> notifications = notificationService.getUnreadNotifications(userId, pageable);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/unread/count")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Get unread count", description = "Get count of unread notifications for the logged-in user")
	public ResponseEntity<Long> getUnreadCount(HttpServletRequest request) {
		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);

		Long count = notificationService.getUnreadCount(userId);
		return ResponseEntity.ok(count);
	}

	@PutMapping("/{id}/read")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Mark notification as read", description = "Mark a specific notification as read")
	public ResponseEntity<NotificationResponse> markAsRead(
			@PathVariable Long id,
			HttpServletRequest request) {

		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);

		NotificationResponse notification = notificationService.markAsRead(id, userId);
		return ResponseEntity.ok(notification);
	}

	@PutMapping("/mark-all-read")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Mark all as read", description = "Mark all notifications as read for the logged-in user")
	public ResponseEntity<Void> markAllAsRead(HttpServletRequest request) {
		String token = tokenProvider.getTokenFromRequest(request);
		Long userId = tokenProvider.getUserIdFromToken(token);

		notificationService.markAllAsRead(userId);
		return ResponseEntity.noContent().build();
	}
}
