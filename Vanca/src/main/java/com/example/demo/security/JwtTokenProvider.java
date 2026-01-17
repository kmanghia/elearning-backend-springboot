package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret:mySecretKeyChangeThisInProduction}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}")
	private long jwtExpirationMs;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

		return Jwts.builder()
			.subject(userPrincipal.getUsername())
			.claim("userId", userPrincipal.getId())
			.claim("role", userPrincipal.getRole().name())
			.issuedAt(now)
			.expiration(expiryDate)
			.signWith(getSigningKey())
			.compact();
	}

	public String generateTokenFromUsername(String username, Long userId, String role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

		return Jwts.builder()
			.subject(username)
			.claim("userId", userId)
			.claim("role", role)
			.issuedAt(now)
			.expiration(expiryDate)
			.signWith(getSigningKey())
			.compact();
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return claims.getSubject();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return claims.get("userId", Long.class);
	}

	public String getRoleFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return claims.get("role", String.class);
	}
	
	public String getTokenFromRequest(jakarta.servlet.http.HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}

