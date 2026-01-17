package com.example.demo.controller;

import com.example.demo.dto.request.CreateCommentRequest;
import com.example.demo.dto.request.CreatePostRequest;
import com.example.demo.dto.request.CreateVoteRequest;
import com.example.demo.dto.request.UpdateCommentRequest;
import com.example.demo.dto.request.UpdatePostRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.service.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
@RequiredArgsConstructor
@Tag(name = "Forum", description = "Discussion Forum Management APIs")
public class ForumController {

	private final ForumService forumService;

	// ==================== POST ENDPOINTS ====================

	@PostMapping("/posts")
	@Operation(summary = "Create a new post", description = "Create a new discussion post in a course")
	public ResponseEntity<PostResponse> createPost(
			@Valid @RequestBody CreatePostRequest request,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		PostResponse response = forumService.createPost(request, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/posts/{postId}")
	@Operation(summary = "Update a post", description = "Update an existing post (author only)")
	public ResponseEntity<PostResponse> updatePost(
			@PathVariable Long postId,
			@Valid @RequestBody UpdatePostRequest request,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		PostResponse response = forumService.updatePost(postId, request, userId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/posts/{postId}")
	@Operation(summary = "Delete a post", description = "Delete a post (author or instructor only)")
	public ResponseEntity<Void> deletePost(
			@PathVariable Long postId,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		forumService.deletePost(postId, userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/posts/{postId}")
	@Operation(summary = "Get post by ID", description = "Retrieve detailed information about a specific post")
	public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
		PostResponse response = forumService.getPostById(postId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/courses/{courseId}/posts")
	@Operation(summary = "Get posts by course", description = "Retrieve all posts for a specific course")
	public ResponseEntity<List<PostResponse>> getPostsByCourse(
			@PathVariable Long courseId,
			@RequestParam(required = false, defaultValue = "recent") String sortBy) {
		List<PostResponse> responses = forumService.getPostsByCourse(courseId, sortBy);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/users/{userId}/posts")
	@Operation(summary = "Get posts by user", description = "Retrieve all posts created by a specific user")
	public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable Long userId) {
		List<PostResponse> responses = forumService.getPostsByUser(userId);
		return ResponseEntity.ok(responses);
	}

	@PatchMapping("/posts/{postId}/pin")
	@Operation(summary = "Pin/Unpin a post", description = "Toggle pin status (instructor only)")
	public ResponseEntity<PostResponse> togglePinPost(
			@PathVariable Long postId,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		PostResponse response = forumService.togglePinPost(postId, userId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/posts/{postId}/resolve")
	@Operation(summary = "Resolve/Unresolve a post", description = "Toggle resolve status (author only)")
	public ResponseEntity<PostResponse> toggleResolvePost(
			@PathVariable Long postId,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		PostResponse response = forumService.toggleResolvePost(postId, userId);
		return ResponseEntity.ok(response);
	}

	// ==================== COMMENT ENDPOINTS ====================

	@PostMapping("/comments")
	@Operation(summary = "Create a comment", description = "Create a new comment or reply on a post")
	public ResponseEntity<CommentResponse> createComment(
			@Valid @RequestBody CreateCommentRequest request,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		CommentResponse response = forumService.createComment(request, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/comments/{commentId}")
	@Operation(summary = "Update a comment", description = "Update an existing comment (author only)")
	public ResponseEntity<CommentResponse> updateComment(
			@PathVariable Long commentId,
			@Valid @RequestBody UpdateCommentRequest request,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		CommentResponse response = forumService.updateComment(commentId, request, userId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/comments/{commentId}")
	@Operation(summary = "Delete a comment", description = "Delete a comment (author only)")
	public ResponseEntity<Void> deleteComment(
			@PathVariable Long commentId,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		forumService.deleteComment(commentId, userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/posts/{postId}/comments")
	@Operation(summary = "Get comments by post", description = "Retrieve all top-level comments for a specific post")
	public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
		List<CommentResponse> responses = forumService.getCommentsByPost(postId);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/comments/{commentId}/replies")
	@Operation(summary = "Get replies to a comment", description = "Retrieve all replies to a specific comment")
	public ResponseEntity<List<CommentResponse>> getRepliesByComment(@PathVariable Long commentId) {
		List<CommentResponse> responses = forumService.getRepliesByComment(commentId);
		return ResponseEntity.ok(responses);
	}

	@PatchMapping("/comments/{commentId}/accept")
	@Operation(summary = "Mark as accepted answer", description = "Mark a comment as the accepted answer (post author only)")
	public ResponseEntity<CommentResponse> markAsAcceptedAnswer(
			@PathVariable Long commentId,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		CommentResponse response = forumService.markAsAcceptedAnswer(commentId, userId);
		return ResponseEntity.ok(response);
	}

	// ==================== VOTE ENDPOINTS ====================

	@PostMapping("/posts/{postId}/vote")
	@Operation(summary = "Vote on a post", description = "Upvote or downvote a post")
	public ResponseEntity<Void> votePost(
			@PathVariable Long postId,
			@Valid @RequestBody CreateVoteRequest request,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		forumService.votePost(postId, request, userId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/comments/{commentId}/vote")
	@Operation(summary = "Vote on a comment", description = "Upvote or downvote a comment")
	public ResponseEntity<Void> voteComment(
			@PathVariable Long commentId,
			@Valid @RequestBody CreateVoteRequest request,
			Authentication authentication) {
		Long userId = Long.parseLong(authentication.getName());
		forumService.voteComment(commentId, request, userId);
		return ResponseEntity.ok().build();
	}
}
