package com.example.demo.controller;

import com.example.demo.service.LocalFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Controller for serving uploaded files from local storage
 * SECURITY: Requires authentication and validates file paths
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final LocalFileStorageService fileStorageService;

    public FileController(LocalFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Serve files from local storage
     * SECURITY: Requires authentication to prevent unauthorized access
     */
    @GetMapping("/**")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> serveFile(HttpServletRequest request) {
        try {
            // Extract file path from request URI
            String requestUri = request.getRequestURI();
            if (requestUri == null || !requestUri.startsWith("/api/files/")) {
                logger.warn("Invalid request URI: {}", requestUri);
                return ResponseEntity.badRequest().build();
            }

            String fileKey = requestUri.substring("/api/files/".length());

            // SECURITY: Validate file key to prevent path traversal
            if (!fileStorageService.isValidFileKey(fileKey)) {
                logger.warn("Invalid file key detected (path traversal attempt): {}", fileKey);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Path filePath = fileStorageService.getFilePath(fileKey);

            // Check file exists
            if (!Files.exists(filePath)) {
                logger.debug("File not found: {}", fileKey);
                return ResponseEntity.notFound().build();
            }

            // SECURITY: Ensure file is within allowed directory
            if (!fileStorageService.isFileInAllowedDirectory(filePath)) {
                logger.warn("Attempt to access file outside allowed directory: {}", filePath);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Resource resource = new FileSystemResource(filePath);

            // Determine content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            // Build response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(Files.size(filePath));

            // Enable inline display for videos/images
            if (contentType.startsWith("video/") || contentType.startsWith("image/")) {
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"");
            } else {
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName() + "\"");
            }

            logger.debug("Serving file: {} with content-type: {}", fileKey, contentType);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            logger.error("Error serving file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
