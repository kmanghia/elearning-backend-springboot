package com.example.demo.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class AwsS3Service {

	private final S3Client s3Client;
	private final S3Presigner s3Presigner;
	private final String bucketName;
	private final Region region;

	public AwsS3Service() {
		// TODO: Load from application.properties
		String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
		String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
		this.bucketName = System.getenv("AWS_S3_BUCKET_NAME") != null 
			? System.getenv("AWS_S3_BUCKET_NAME") 
			: "lms-videos";
		this.region = Region.US_EAST_1;

		if (accessKey != null && secretKey != null) {
			AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
			this.s3Client = S3Client.builder()
				.region(region)
				.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.build();
			this.s3Presigner = S3Presigner.builder()
				.region(region)
				.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.build();
		} else {
			// Fallback for development
			this.s3Client = null;
			this.s3Presigner = null;
		}
	}

	/**
	 * Generate pre-signed URL for video streaming
	 * @param videoKey S3 object key
	 * @return Pre-signed URL valid for 1 hour, or public URL if AWS not configured
	 */
	public String generatePresignedUrl(String videoKey) {
		if (s3Presigner == null || s3Client == null) {
			// AWS not configured - return public URL format (may not work if bucket is private)
			// In production, you should configure AWS credentials
			throw new RuntimeException("AWS S3 is not configured. Please set AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY environment variables.");
		}

		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
			.bucket(bucketName)
			.key(videoKey)
			.build();

		PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(
			presignerBuilder -> presignerBuilder
				.signatureDuration(Duration.ofHours(1))
				.getObjectRequest(getObjectRequest)
		);

		return presignedRequest.url().toString();
	}

	/**
	 * Upload video file to S3
	 * @param fileKey S3 object key
	 * @param fileContent File content as byte array
	 * @return S3 URL
	 */
	public String uploadVideo(String fileKey, byte[] fileContent) {
		if (s3Client == null) {
			throw new RuntimeException("AWS S3 client not configured");
		}

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(fileKey)
			.contentType("video/mp4")
			.build();

		s3Client.putObject(putObjectRequest, 
			software.amazon.awssdk.core.sync.RequestBody.fromBytes(fileContent));

		return "s3://" + bucketName + "/" + fileKey;
	}

	/**
	 * Generate unique file key for video
	 * @param courseId Course ID
	 * @param lessonId Lesson ID
	 * @param originalFileName Original file name
	 * @return Unique S3 key
	 */
	public String generateVideoKey(Long courseId, Long lessonId, String originalFileName) {
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		return String.format("courses/%d/lessons/%d/%s%s", 
			courseId, lessonId, UUID.randomUUID(), extension);
	}
}

