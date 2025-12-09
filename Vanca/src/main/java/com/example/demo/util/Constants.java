package com.example.demo.util;

public class Constants {
	
	// JWT
	public static final String JWT_SECRET = "mySecretKey"; // Should be in environment variable
	public static final long JWT_EXPIRATION = 86400000; // 24 hours
	
	// AWS S3
	public static final String S3_BUCKET_NAME = "lms-videos"; // Should be in config
	public static final int PRESIGNED_URL_EXPIRATION_HOURS = 1;
	
	// Pagination
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final int MAX_PAGE_SIZE = 100;
	
	// Quiz
	public static final int DEFAULT_PASSING_SCORE = 60;
	public static final int DEFAULT_MAX_ATTEMPTS = 3;
	
	private Constants() {
		// Utility class
	}
}

