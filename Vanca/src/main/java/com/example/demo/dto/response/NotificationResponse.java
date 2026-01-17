package com.example.demo.dto.response;

import com.example.demo.model.Notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
	
	private Long id;
	private String title;
	private String message;
	private NotificationType type;
	private Boolean isRead;
	private String relatedEntityType;
	private Long relatedEntityId;
	private LocalDateTime createdAt;
}
