package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Long userId;
	private String email;
	private String role;
}

