package com.example.demo.dto.response;

public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Long userId;
	private String email;
	private String role;

	public JwtAuthenticationResponse() {
	}

	public JwtAuthenticationResponse(String accessToken, String tokenType, Long userId, String email, String role) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.userId = userId;
		this.email = email;
		this.role = role;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
