package com.blogpoint.blog.service.impl;

import com.blogpoint.blog.dto.CategoryRequest;
import com.blogpoint.blog.dto.CategoryResponse;
import com.blogpoint.blog.exception.BadRequestException;
import com.blogpoint.blog.exception.ResourceNotFoundException;
import com.blogpoint.blog.model.Category;
import com.blogpoint.blog.repository.CategoryRepository;
import com.blogpoint.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException("Category name already exists");
        }

        String slug = generateSlug(request.getName());
        if (categoryRepository.existsBySlug(slug)) {
            throw new BadRequestException("Category slug already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .slug(slug)
                .build();

        category = categoryRepository.save(category);
        return mapToCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!request.getName().equals(category.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException("Category name already exists");
        }

        String slug = generateSlug(request.getName());
        if (!slug.equals(category.getSlug()) && categoryRepository.existsBySlug(slug)) {
            throw new BadRequestException("Category slug already exists");
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setSlug(slug);

        category = categoryRepository.save(category);
        return mapToCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
    }

    @Override
    public CategoryResponse mapToCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setSlug(category.getSlug());
        response.setCreatedAt(category.getCreatedAt());
        return response;
    }

}
