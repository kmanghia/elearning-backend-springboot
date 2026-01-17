package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class LocalFileStorageService {

    @Value("${file.storage.base-path:./uploads}")
    private String basePath;

    @Value("${file.storage.base-url:http://localhost:8080/api/files}")
    private String baseUrl;

    /**
     * Generate URL for file access
     * 
     * @param fileKey File key/path
     * @return Accessible URL
     */
    public String generatePresignedUrl(String fileKey) {
        // For local storage, return public URL served by FileController
        return baseUrl + "/" + fileKey;
    }

    /**
     * Upload file to local storage
     * 
     * @param fileKey     File key/path
     * @param fileContent File content as byte array
     * @return Local file URL
     */
    public String uploadVideo(String fileKey, byte[] fileContent) {
        // SECURITY: Validate file key before upload
        if (!isValidFileKey(fileKey)) {
            throw new IllegalArgumentException("Invalid file key: " + fileKey);
        }

        try {
            Path uploadPath = Paths.get(basePath);

            // Create base directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Create file path
            Path filePath = uploadPath.resolve(fileKey);

            // SECURITY: Ensure resolved path is within base directory
            if (!isFileInAllowedDirectory(filePath)) {
                throw new SecurityException("File path is outside allowed directory: " + fileKey);
            }

            // Create parent directories if needed
            Path parentDir = filePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Write file
            Files.write(filePath, fileContent,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            return "local://" + fileKey;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + fileKey, e);
        }
    }

    /**
     * Generate unique file key for video
     * 
     * @param courseId         Course ID
     * @param lessonId         Lesson ID
     * @param originalFileName Original file name
     * @return Unique file key
     */
    public String generateVideoKey(Long courseId, Long lessonId, String originalFileName) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return String.format("courses/%d/lessons/%d/%s%s",
                courseId, lessonId, UUID.randomUUID(), extension);
    }

    /**
     * Get absolute file path from file key
     * 
     * @param fileKey File key
     * @return Absolute path to file
     */
    public Path getFilePath(String fileKey) {
        return Paths.get(basePath).resolve(fileKey).normalize();
    }

    /**
     * SECURITY: Validate file key to prevent path traversal attacks
     * 
     * @param fileKey File key to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidFileKey(String fileKey) {
        if (fileKey == null || fileKey.isEmpty()) {
            return false;
        }

        // Reject if contains path traversal sequences
        if (fileKey.contains("..") || fileKey.contains("./") || fileKey.contains(".\\")) {
            return false;
        }

        // Reject absolute paths
        if (fileKey.startsWith("/") || fileKey.startsWith("\\")) {
            return false;
        }

        // Reject Windows drive letters (C:, D:, etc.)
        if (fileKey.matches("^[A-Za-z]:.*")) {
            return false;
        }

        return true;
    }

    /**
     * SECURITY: Check if resolved file path is within allowed base directory
     * 
     * @param filePath Resolved file path
     * @return true if within allowed directory, false otherwise
     */
    public boolean isFileInAllowedDirectory(Path filePath) {
        try {
            Path baseDir = Paths.get(basePath).toRealPath();
            Path resolvedPath = filePath.toRealPath();
            return resolvedPath.startsWith(baseDir);
        } catch (IOException e) {
            // If file doesn't exist yet or can't be resolved, check normalized paths
            try {
                Path baseDir = Paths.get(basePath).toAbsolutePath().normalize();
                Path normalizedPath = filePath.toAbsolutePath().normalize();
                return normalizedPath.startsWith(baseDir);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Check if file exists
     * 
     * @param fileKey File key
     * @return true if file exists
     */
    public boolean fileExists(String fileKey) {
        if (!isValidFileKey(fileKey)) {
            return false;
        }
        return Files.exists(getFilePath(fileKey));
    }

    /**
     * Delete file from local storage
     * 
     * @param fileKey File key
     */
    public void deleteFile(String fileKey) {
        if (!isValidFileKey(fileKey)) {
            throw new IllegalArgumentException("Invalid file key: " + fileKey);
        }

        try {
            Path filePath = getFilePath(fileKey);

            // SECURITY: Ensure file is within allowed directory
            if (!isFileInAllowedDirectory(filePath)) {
                throw new SecurityException("File is outside allowed directory: " + fileKey);
            }

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + fileKey, e);
        }
    }
}
