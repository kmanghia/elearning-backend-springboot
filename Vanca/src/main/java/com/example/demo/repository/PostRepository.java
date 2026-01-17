package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByCourseId(Long courseId);
	List<Post> findByCourseIdOrderByCreatedAtDesc(Long courseId);
	List<Post> findByCourseIdOrderByVoteCountDescCreatedAtDesc(Long courseId);
	List<Post> findByAuthorId(Long authorId);
	List<Post> findByCourseIdAndIsPinned(Long courseId, Boolean isPinned);
	
	@Query("SELECT p FROM Post p WHERE p.course.id = :courseId AND p.type = :type ORDER BY p.createdAt DESC")
	List<Post> findByCourseIdAndType(Long courseId, Post.PostType type);
	
	@Query("SELECT COUNT(p) FROM Post p WHERE p.course.id = :courseId")
	Long countByCourseId(Long courseId);
	
	@Query("SELECT COUNT(p) FROM Post p WHERE p.author.id = :authorId")
	Long countByAuthorId(Long authorId);
}
