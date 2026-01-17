package com.example.demo.repository;

import com.example.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(Long postId);
	List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
	List<Comment> findByPostIdOrderByVoteCountDescCreatedAtDesc(Long postId);
	List<Comment> findByAuthorId(Long authorId);
	List<Comment> findByParentCommentId(Long parentCommentId);
	
	// Get top-level comments (no parent)
	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parentComment IS NULL ORDER BY c.voteCount DESC, c.createdAt ASC")
	List<Comment> findTopLevelCommentsByPostId(Long postId);
	
	// Find accepted answer for a post
	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.isAcceptedAnswer = true")
	Optional<Comment> findAcceptedAnswerByPostId(Long postId);
	
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
	Long countByPostId(Long postId);
	
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.author.id = :authorId")
	Long countByAuthorId(Long authorId);
}
