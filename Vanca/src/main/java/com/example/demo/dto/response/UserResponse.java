package com.example.demo.dto.response;

import com.example.demo.model.User;

import java.time.LocalDateTime;

public class UserResponse {
	private Long id;
	private String email;
	private String fullName;
	private User.Role role;
	private LocalDateTime createdAt;

	public UserResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String email;
		private String fullName;
		private User.Role role;
		private LocalDateTime createdAt;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder fullName(String fullName) {
			this.fullName = fullName;
			return this;
		}

		public Builder role(User.Role role) {
			this.role = role;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public UserResponse build() {
			UserResponse response = new UserResponse();
			response.setId(id);
			response.setEmail(email);
			response.setFullName(fullName);
			response.setRole(role);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public User.Role getRole() {
		return role;
	}

	public void setRole(User.Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
