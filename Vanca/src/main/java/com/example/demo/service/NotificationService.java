package com.example.demo.service;

import com.example.demo.dto.response.NotificationResponse;
import com.example.demo.model.Notification;
import com.example.demo.model.Notification.NotificationType;
import com.example.demo.model.User;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
	
	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;


	public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public NotificationResponse createNotification(
			Long userId, String title, String message, NotificationType type,
			String relatedEntityType, Long relatedEntityId) {
		
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Notification notification = new Notification();
		notification.setUser(user);
		notification.setTitle(title);
		notification.setMessage(message);
		notification.setType(type);
		notification.setRelatedEntityType(relatedEntityType);
		notification.setRelatedEntityId(relatedEntityId);
		notification.setIsRead(false);
		
		Notification savedNotification = notificationRepository.save(notification);
		
		return mapToResponse(savedNotification);
	}
	
	public List<NotificationResponse> getUserNotifications(Long userId) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
		return notifications.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	// Paginated version
	public Page<NotificationResponse> getUserNotifications(Long userId, Pageable pageable) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);
		return notifications.map(this::mapToResponse);
	}
	
	public List<NotificationResponse> getUnreadNotifications(Long userId) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
		return notifications.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	// Paginated version
	public Page<NotificationResponse> getUnreadNotifications(Long userId, Pageable pageable) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Page<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId, pageable);
		return notifications.map(this::mapToResponse);
	}
	
	public Long getUnreadCount(Long userId) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		return notificationRepository.countByUserIdAndIsReadFalse(userId);
	}
	
	@Transactional
	public NotificationResponse markAsRead(Long notificationId, Long userId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
		
		// Verify the notification belongs to the user
		if (!notification.getUser().getId().equals(userId)) {
			throw new UnauthorizedException("You can only mark your own notifications as read");
		}
		
		notification.setIsRead(true);
		Notification updatedNotification = notificationRepository.save(notification);
		
		return mapToResponse(updatedNotification);
	}
	
	@Transactional
	public void markAllAsRead(Long userId) {
		// Verify user exists
		userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
		unreadNotifications.forEach(notification -> notification.setIsRead(true));
		notificationRepository.saveAll(unreadNotifications);
	}
	
	private NotificationResponse mapToResponse(Notification notification) {
		return NotificationResponse.builder()
			.id(notification.getId())
			.title(notification.getTitle())
			.message(notification.getMessage())
			.type(notification.getType())
			.isRead(notification.getIsRead())
			.relatedEntityType(notification.getRelatedEntityType())
			.relatedEntityId(notification.getRelatedEntityId())
			.createdAt(notification.getCreatedAt())
			.build();
	}
}
