package com.example.demo.controller;

import com.example.demo.dto.request.CreateCategoryRequest;
import com.example.demo.dto.request.UpdateCategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Course category management APIs")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Create category", description = "Create a new course category (ADMIN only)")
	public ResponseEntity<CategoryResponse> createCategory(
			@Valid @RequestBody CreateCategoryRequest request) {
		
		CategoryResponse category = categoryService.createCategory(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Update category", description = "Update an existing category (ADMIN only)")
	public ResponseEntity<CategoryResponse> updateCategory(
			@PathVariable Long id,
			@Valid @RequestBody UpdateCategoryRequest request) {
		
		CategoryResponse category = categoryService.updateCategory(id, request);
		return ResponseEntity.ok(category);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Delete category", description = "Delete a category (ADMIN only, fails if has subcategories or courses)")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@Operation(summary = "Get all categories", description = "Get all categories including parent and child categories")
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		List<CategoryResponse> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/root")
	@Operation(summary = "Get root categories", description = "Get only top-level categories without parents")
	public ResponseEntity<List<CategoryResponse>> getRootCategories() {
		List<CategoryResponse> categories = categoryService.getRootCategories();
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{id}/subcategories")
	@Operation(summary = "Get subcategories", description = "Get all child categories of a parent category")
	public ResponseEntity<List<CategoryResponse>> getSubcategories(
			@PathVariable Long id) {
		
		List<CategoryResponse> subcategories = categoryService.getSubcategories(id);
		return ResponseEntity.ok(subcategories);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get category by ID", description = "Get category details by ID")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
		CategoryResponse category = categoryService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}
	
	@GetMapping("/slug/{slug}")
	@Operation(summary = "Get category by slug", description = "Get category by URL-friendly slug")
	public ResponseEntity<CategoryResponse> getCategoryBySlug(@PathVariable String slug) {
		CategoryResponse category = categoryService.getCategoryBySlug(slug);
		return ResponseEntity.ok(category);
	}
}
