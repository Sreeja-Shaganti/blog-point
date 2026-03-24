package com.blogpoint.blog.service;

import com.blogpoint.blog.dto.CategoryRequest;
import com.blogpoint.blog.dto.CategoryResponse;
import com.blogpoint.blog.model.Category;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(@Valid CategoryRequest request);

    CategoryResponse updateCategory(Long id, @Valid CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse mapToCategoryResponse(Category category);
}
