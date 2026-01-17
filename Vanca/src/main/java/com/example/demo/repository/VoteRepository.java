package com.example.demo.repository;

import com.example.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	// Post votes
	Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
	boolean existsByUserIdAndPostId(Long userId, Long postId);
	
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.post.id = :postId AND v.voteType = 'UPVOTE'")
	Long countUpvotesByPostId(Long postId);
	
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.post.id = :postId AND v.voteType = 'DOWNVOTE'")
	Long countDownvotesByPostId(Long postId);
	
	// Comment votes
	Optional<Vote> findByUserIdAndCommentId(Long userId, Long commentId);
	boolean existsByUserIdAndCommentId(Long userId, Long commentId);
	
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.comment.id = :commentId AND v.voteType = 'UPVOTE'")
	Long countUpvotesByCommentId(Long commentId);
	
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.comment.id = :commentId AND v.voteType = 'DOWNVOTE'")
	Long countDownvotesByCommentId(Long commentId);
	
	// Delete votes
	void deleteByUserIdAndPostId(Long userId, Long postId);
	void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
