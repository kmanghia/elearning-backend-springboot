package com.example.demo.service;

import com.example.demo.dto.request.CreateCategoryRequest;
import com.example.demo.dto.request.UpdateCategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CourseRepository courseRepository;
	
	@Transactional
	public CategoryResponse createCategory(CreateCategoryRequest request) {
		// Check if slug already exists
		if (categoryRepository.existsBySlug(request.getSlug())) {
			throw new IllegalStateException("Category with slug '" + request.getSlug() + "' already exists");
		}
		
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setSlug(request.getSlug());
		category.setIconUrl(request.getIconUrl());
		
		// Set parent if provided
		if (request.getParentId() != null) {
			Category parent = categoryRepository.findById(request.getParentId())
				.orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
			category.setParent(parent);
		}
		
		Category savedCategory = categoryRepository.save(category);
		
		return mapToResponse(savedCategory);
	}
	
	@Transactional
	public CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		
		if (request.getName() != null) {
			category.setName(request.getName());
		}
		if (request.getDescription() != null) {
			category.setDescription(request.getDescription());
		}
		if (request.getIconUrl() != null) {
			category.setIconUrl(request.getIconUrl());
		}
		
		Category updatedCategory = categoryRepository.save(category);
		
		return mapToResponse(updatedCategory);
	}
	
	@Transactional
	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		
		// Check if category has subcategories
		List<Category> subcategories = categoryRepository.findByParentId(categoryId);
		if (!subcategories.isEmpty()) {
			throw new IllegalStateException("Cannot delete category with subcategories. Delete subcategories first.");
		}
		
		// Check if category is used by any courses
		Long courseCount = courseRepository.countByCategoryId(categoryId);
		if (courseCount != null && courseCount > 0) {
			throw new IllegalStateException("Cannot delete category that is assigned to courses");
		}
		
		categoryRepository.delete(category);
	}
	
	public List<CategoryResponse> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	public List<CategoryResponse> getRootCategories() {
		List<Category> rootCategories = categoryRepository.findByParentIsNull();
		return rootCategories.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	public List<CategoryResponse> getSubcategories(Long parentId) {
		// Verify parent exists
		categoryRepository.findById(parentId)
			.orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
		
		List<Category> subcategories = categoryRepository.findByParentId(parentId);
		return subcategories.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}
	
	public CategoryResponse getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		
		return mapToResponse(category);
	}
	
	public CategoryResponse getCategoryBySlug(String slug) {
		Category category = categoryRepository.findBySlug(slug)
			.orElseThrow(() -> new ResourceNotFoundException("Category not found with slug: " + slug));
		
		return mapToResponse(category);
	}
	
	private CategoryResponse mapToResponse(Category category) {
		Long subcategoryCount = (long) categoryRepository.findByParentId(category.getId()).size();
		Long courseCount = courseRepository.countByCategoryId(category.getId());
		
		return CategoryResponse.builder()
			.id(category.getId())
			.name(category.getName())
			.description(category.getDescription())
			.slug(category.getSlug())
			.parentId(category.getParent() != null ? category.getParent().getId() : null)
			.parentName(category.getParent() != null ? category.getParent().getName() : null)
			.iconUrl(category.getIconUrl())
			.subcategoryCount(subcategoryCount)
			.courseCount(courseCount != null ? courseCount : 0L)
			.createdAt(category.getCreatedAt())
			.build();
	}
}
