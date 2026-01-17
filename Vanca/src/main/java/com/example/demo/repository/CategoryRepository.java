package com.example.demo.repository;

import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findBySlug(String slug);
	List<Category> findByParentIsNull(); // Get root categories
	List<Category> findByParentId(Long parentId); // Get subcategories
	Long countByParentId(Long parentId); // Bug #1 Fix: Count subcategories efficiently
	boolean existsBySlug(String slug);
}
