package com.example.demo.repository;

import com.example.demo.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	
	Optional<Wishlist> findByUserIdAndCourseId(Long userId, Long courseId);
	
	boolean existsByUserIdAndCourseId(Long userId, Long courseId);
	
	@Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId ORDER BY w.addedAt DESC")
	Page<Wishlist> findByUserId(@Param("userId") Long userId, Pageable pageable);
	
	void deleteByUserIdAndCourseId(Long userId, Long courseId);
	
	@Query("SELECT COUNT(w) FROM Wishlist w WHERE w.course.id = :courseId")
	Long countByCourseId(@Param("courseId") Long courseId);
}
