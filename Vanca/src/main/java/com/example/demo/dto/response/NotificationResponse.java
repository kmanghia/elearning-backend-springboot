package com.example.demo.dto.response;

import com.example.demo.model.Notification.NotificationType;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class NotificationResponse {

	private Long id;
	private String title;
	private String message;
	private NotificationType type;
	private Boolean isRead;
	@Nullable
	private String relatedEntityType;
	@Nullable
	private Long relatedEntityId;
	private LocalDateTime createdAt;

	public NotificationResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String title;
		private String message;
		private NotificationType type;
		private Boolean isRead;
		private String relatedEntityType;
		private Long relatedEntityId;
		private LocalDateTime createdAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder type(NotificationType type) {
			this.type = type;
			return this;
		}

		public Builder isRead(Boolean isRead) {
			this.isRead = isRead;
			return this;
		}

		public Builder relatedEntityType(String relatedEntityType) {
			this.relatedEntityType = relatedEntityType;
			return this;
		}

		public Builder relatedEntityId(Long relatedEntityId) {
			this.relatedEntityId = relatedEntityId;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public NotificationResponse build() {
			NotificationResponse response = new NotificationResponse();
			response.setId(id);
			response.setTitle(title);
			response.setMessage(message);
			response.setType(type);
			response.setIsRead(isRead);
			response.setRelatedEntityType(relatedEntityType);
			response.setRelatedEntityId(relatedEntityId);
			response.setCreatedAt(createdAt);
			return response;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public String getRelatedEntityType() {
		return relatedEntityType;
	}

	public void setRelatedEntityType(String relatedEntityType) {
		this.relatedEntityType = relatedEntityType;
	}

	public Long getRelatedEntityId() {
		return relatedEntityId;
	}

	public void setRelatedEntityId(Long relatedEntityId) {
		this.relatedEntityId = relatedEntityId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
