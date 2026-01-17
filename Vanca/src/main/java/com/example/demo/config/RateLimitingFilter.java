package com.example.demo.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {

	private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String requestURI = httpRequest.getRequestURI();
		
		// Apply rate limiting only to specific endpoints
		if (shouldApplyRateLimit(requestURI)) {
			String key = getClientKey(httpRequest);
			Bucket bucket = resolveBucket(key, requestURI);
			
			if (bucket.tryConsume(1)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				httpResponse.getWriter().write("{\"error\":\"Too many requests. Please try again later.\"}");
				httpResponse.setContentType("application/json");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean shouldApplyRateLimit(String uri) {
		// Apply strict rate limiting to auth endpoints
		if (uri.startsWith("/api/auth/")) {
			return true;
		}
		// Apply general rate limiting to all public APIs
		if (uri.startsWith("/api/")) {
			return true;
		}
		return false;
	}

	private Bucket resolveBucket(String key, String uri) {
		return cache.computeIfAbsent(key, k -> createNewBucket(uri));
	}

	private Bucket createNewBucket(String uri) {
		Bandwidth limit;
		
		// Stricter limits for authentication endpoints (5 requests per minute)
		if (uri.startsWith("/api/auth/")) {
			limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
		} 
		// General API limit (100 requests per minute)
		else {
			limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1)));
		}
		
		return Bucket.builder()
				.addLimit(limit)
				.build();
	}

	private String getClientKey(HttpServletRequest request) {
		// Use IP address as the key
		String clientIP = request.getRemoteAddr();
		
		// If behind a proxy, try to get the real IP
		String xForwardedFor = request.getHeader("X-Forwarded-For");
		if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
			clientIP = xForwardedFor.split(",")[0].trim();
		}
		
		return clientIP;
	}
}
