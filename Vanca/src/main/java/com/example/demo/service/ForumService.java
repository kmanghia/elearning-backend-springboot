package com.example.demo.service;

import com.example.demo.dto.request.CreateCommentRequest;
import com.example.demo.dto.request.CreatePostRequest;
import com.example.demo.dto.request.CreateVoteRequest;
import com.example.demo.dto.request.UpdateCommentRequest;
import com.example.demo.dto.request.UpdatePostRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ForumService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final VoteRepository voteRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;

	// ==================== POST OPERATIONS ====================

	public PostResponse createPost(CreatePostRequest request, Long userId) {
		User author = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new RuntimeException("Course not found"));

		Post post = new Post();
		post.setCourse(course);
		post.setAuthor(author);
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());
		post.setType(request.getType());
		post.setIsPinned(false);
		post.setIsResolved(false);
		post.setViewCount(0);
		post.setVoteCount(0);

		Post savedPost = postRepository.save(post);
		return mapToPostResponse(savedPost);
	}

	public PostResponse updatePost(Long postId, UpdatePostRequest request, Long userId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		// Check if user is the author
		if (!post.getAuthor().getId().equals(userId)) {
			throw new RuntimeException("You are not authorized to update this post");
		}

		// Only update fields that are provided (not null)
		if (request.getTitle() != null && !request.getTitle().isBlank()) {
			post.setTitle(request.getTitle());
		}
		if (request.getContent() != null && !request.getContent().isBlank()) {
			post.setContent(request.getContent());
		}
		post.setUpdatedAt(LocalDateTime.now());

		Post updatedPost = postRepository.save(post);
		return mapToPostResponse(updatedPost);
	}

	public void deletePost(Long postId, Long userId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		// Check if user is the author or instructor
		boolean isAuthor = post.getAuthor().getId().equals(userId);
		boolean isInstructor = post.getCourse().getInstructor().getId().equals(userId);
		
		if (!isAuthor && !isInstructor) {
			throw new RuntimeException("You are not authorized to delete this post");
		}

		postRepository.delete(post);
	}

	public PostResponse getPostById(Long postId) {
		// Increment view count atomically (fixes race condition)
		postRepository.incrementViewCount(postId);
		
		// Fetch post with updated view count
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		return mapToPostResponse(post);
	}

	public List<PostResponse> getPostsByCourse(Long courseId, String sortBy) {
		List<Post> posts;

		if ("popular".equalsIgnoreCase(sortBy)) {
			posts = postRepository.findByCourseIdOrderByVoteCountDescCreatedAtDesc(courseId);
		} else {
			posts = postRepository.findByCourseIdOrderByCreatedAtDesc(courseId);
		}

		return posts.stream()
				.map(this::mapToPostResponse)
				.collect(Collectors.toList());
	}

	public List<PostResponse> getPostsByUser(Long userId) {
		List<Post> posts = postRepository.findByAuthorId(userId);
		return posts.stream()
				.map(this::mapToPostResponse)
				.collect(Collectors.toList());
	}

	public PostResponse togglePinPost(Long postId, Long userId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		// Only instructor can pin posts - check if user is course instructor
		Course course = post.getCourse();
		if (!course.getInstructor().getId().equals(userId)) {
			throw new RuntimeException("Only the course instructor can pin posts");
		}

		post.setIsPinned(!post.getIsPinned());
		Post updatedPost = postRepository.save(post);
		return mapToPostResponse(updatedPost);
	}

	public PostResponse toggleResolvePost(Long postId, Long userId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		// Only post author can mark as resolved
		if (!post.getAuthor().getId().equals(userId)) {
			throw new RuntimeException("Only the post author can mark it as resolved");
		}

		post.setIsResolved(!post.getIsResolved());
		Post updatedPost = postRepository.save(post);
		return mapToPostResponse(updatedPost);
	}

	// ==================== COMMENT OPERATIONS ====================

	public CommentResponse createComment(CreateCommentRequest request, Long userId) {
		User author = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Post post = postRepository.findById(request.getPostId())
				.orElseThrow(() -> new RuntimeException("Post not found"));

		Comment comment = new Comment();
		comment.setPost(post);
		comment.setAuthor(author);
		comment.setContent(request.getContent());
		comment.setIsAcceptedAnswer(false);
		comment.setVoteCount(0);

		// Handle parent comment for threaded replies
		if (request.getParentCommentId() != null) {
			Comment parentComment = commentRepository.findById(request.getParentCommentId())
					.orElseThrow(() -> new RuntimeException("Parent comment not found"));
			
			// Validate parent comment belongs to the same post
			if (!parentComment.getPost().getId().equals(post.getId())) {
				throw new RuntimeException("Parent comment must belong to the same post");
			}
			
			comment.setParentComment(parentComment);
		}

		Comment savedComment = commentRepository.save(comment);
		return mapToCommentResponse(savedComment);
	}

	public CommentResponse updateComment(Long commentId, UpdateCommentRequest request, Long userId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		// Check if user is the author
		if (!comment.getAuthor().getId().equals(userId)) {
			throw new RuntimeException("You are not authorized to update this comment");
		}

		// Only update content if provided (not null)
		if (request.getContent() != null && !request.getContent().isBlank()) {
			comment.setContent(request.getContent());
		}
		comment.setUpdatedAt(LocalDateTime.now());

		Comment updatedComment = commentRepository.save(comment);
		return mapToCommentResponse(updatedComment);
	}

	public void deleteComment(Long commentId, Long userId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		// Check if user is the author
		if (!comment.getAuthor().getId().equals(userId)) {
			throw new RuntimeException("You are not authorized to delete this comment");
		}

		// Get parent info before deletion for vote recalculation
		Comment parentComment = comment.getParentComment();
		
		commentRepository.delete(comment);
		
		// Recalculate parent comment vote count if exists (fixes denormalization bug)
		if (parentComment != null) {
			updateCommentVoteCount(parentComment);
		}
	}

	public List<CommentResponse> getCommentsByPost(Long postId) {
		List<Comment> comments = commentRepository.findTopLevelCommentsByPostId(postId);
		return comments.stream()
				.map(this::mapToCommentResponse)
				.collect(Collectors.toList());
	}

	public List<CommentResponse> getRepliesByComment(Long commentId) {
		List<Comment> replies = commentRepository.findByParentCommentId(commentId);
		return replies.stream()
				.map(this::mapToCommentResponse)
				.collect(Collectors.toList());
	}

	public CommentResponse markAsAcceptedAnswer(Long commentId, Long userId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		Post post = comment.getPost();

		// Only post author can mark answer as accepted
		if (!post.getAuthor().getId().equals(userId)) {
			throw new RuntimeException("Only the post author can mark an answer as accepted");
		}

		// Toggle behavior: if already accepted, unmark it
		if (comment.getIsAcceptedAnswer()) {
			comment.setIsAcceptedAnswer(false);
			post.setIsResolved(false);
		} else {
			// Unmark any previously accepted answer (different from this one)
			commentRepository.findAcceptedAnswerByPostId(post.getId())
					.filter(previousAnswer -> !previousAnswer.getId().equals(commentId))
					.ifPresent(previousAnswer -> {
						previousAnswer.setIsAcceptedAnswer(false);
						commentRepository.save(previousAnswer);
					});

			// Mark this comment as accepted
			comment.setIsAcceptedAnswer(true);
			
			// Mark post as resolved
			post.setIsResolved(true);
		}
		
		Comment updatedComment = commentRepository.save(comment);
		postRepository.save(post);

		return mapToCommentResponse(updatedComment);
	}

	// ==================== VOTE OPERATIONS ====================

	public void votePost(Long postId, CreateVoteRequest request, Long userId) {
		// Validate vote target FIRST (before DB queries)
		if (request.getPostId() == null && request.getCommentId() == null) {
			throw new RuntimeException("Must vote on either a post or comment");
		}
		if (request.getPostId() != null && request.getCommentId() != null) {
			throw new RuntimeException("Cannot vote on both post and comment simultaneously");
		}
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		// Check if user already voted
		voteRepository.findByUserIdAndPostId(userId, postId).ifPresentOrElse(
				existingVote -> {
					// Update vote if different, remove if same
					if (existingVote.getVoteType().equals(request.getVoteType())) {
						// Remove vote
						voteRepository.delete(existingVote);
						updatePostVoteCount(post);
					} else {
						// Change vote
						existingVote.setVoteType(request.getVoteType());
						voteRepository.save(existingVote);
						updatePostVoteCount(post);
					}
				},
				() -> {
					// Create new vote
					Vote vote = new Vote();
					vote.setUser(user);
					vote.setPost(post);
					vote.setVoteType(request.getVoteType());
					voteRepository.save(vote);
					updatePostVoteCount(post);
				}
		);
	}

	public void voteComment(Long commentId, CreateVoteRequest request, Long userId) {
		// Validate vote target FIRST (before DB queries)
		if (request.getPostId() == null && request.getCommentId() == null) {
			throw new RuntimeException("Must vote on either a post or comment");
		}
		if (request.getPostId() != null && request.getCommentId() != null) {
			throw new RuntimeException("Cannot vote on both post and comment simultaneously");
		}
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		// Check if user already voted
		voteRepository.findByUserIdAndCommentId(userId, commentId).ifPresentOrElse(
				existingVote -> {
					// Update vote if different, remove if same
					if (existingVote.getVoteType().equals(request.getVoteType())) {
						// Remove vote
						voteRepository.delete(existingVote);
						updateCommentVoteCount(comment);
					} else {
						// Change vote
						existingVote.setVoteType(request.getVoteType());
						voteRepository.save(existingVote);
						updateCommentVoteCount(comment);
					}
				},
				() -> {
					// Create new vote
					Vote vote = new Vote();
					vote.setUser(user);
					vote.setComment(comment);
					vote.setVoteType(request.getVoteType());
					voteRepository.save(vote);
					updateCommentVoteCount(comment);
				}
		);
	}

	// ==================== HELPER METHODS ====================

	private void updatePostVoteCount(Post post) {
		Long upvotes = voteRepository.countUpvotesByPostId(post.getId());
		Long downvotes = voteRepository.countDownvotesByPostId(post.getId());
		post.setVoteCount(upvotes - downvotes); // No Math.toIntExact needed - using Long now
		postRepository.save(post);
	}

	private void updateCommentVoteCount(Comment comment) {
		Long upvotes = voteRepository.countUpvotesByCommentId(comment.getId());
		Long downvotes = voteRepository.countDownvotesByCommentId(comment.getId());
		comment.setVoteCount(upvotes - downvotes); // No Math.toIntExact needed - using Long now
		commentRepository.save(comment);
	}

	private PostResponse mapToPostResponse(Post post) {
		return PostResponse.builder()
				.id(post.getId())
				.courseId(post.getCourse().getId())
				.courseTitle(post.getCourse().getTitle())
				.authorId(post.getAuthor().getId())
				.authorName(post.getAuthor().getFullName())
				.title(post.getTitle())
				.content(post.getContent())
				.type(post.getType())
				.isPinned(post.getIsPinned())
				.isResolved(post.getIsResolved())
				.viewCount(post.getViewCount())
				.voteCount(post.getVoteCount())
				.commentCount(commentRepository.countByPostId(post.getId()).intValue())
				.createdAt(post.getCreatedAt())
				.updatedAt(post.getUpdatedAt())
				.build();
	}

	private CommentResponse mapToCommentResponse(Comment comment) {
		return CommentResponse.builder()
				.id(comment.getId())
				.postId(comment.getPost().getId())
				.authorId(comment.getAuthor().getId())
				.authorName(comment.getAuthor().getFullName())
				.content(comment.getContent())
				.parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
				.isAcceptedAnswer(comment.getIsAcceptedAnswer())
				.voteCount(comment.getVoteCount())
				.replies(null)
				.createdAt(comment.getCreatedAt())
				.updatedAt(comment.getUpdatedAt())
				.build();
	}
}
