package com.blogpoint.blog.service.impl;

import com.blogpoint.blog.dto.BlogRequest;
import com.blogpoint.blog.dto.BlogResponse;
import com.blogpoint.blog.dto.BlogSummaryResponse;
import com.blogpoint.blog.exception.ResourceNotFoundException;
import com.blogpoint.blog.exception.UnauthorizedException;
import com.blogpoint.blog.model.*;
import com.blogpoint.blog.repository.BlogRepository;
import com.blogpoint.blog.repository.CategoryRepository;
import com.blogpoint.blog.repository.UserRepository;
import com.blogpoint.blog.service.BlogService;
import com.blogpoint.blog.service.CategoryService;
import com.blogpoint.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    private final CategoryRepository categoryRepository;

    private final  UserRepository userRepository;

    private final  UserService userService;

    private final CategoryService categoryService;

    @Override
    public Page<BlogSummaryResponse> getAllPublishedBlogs(Pageable pageable) {
        return blogRepository.findByStatus(BlogStatus.PUBLISHED, pageable)
                .map(this::mapToBlogSummaryResponse);
    }

    @Override
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));
        blog.setViewCount(blog.getViewCount() + 1);
        blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    @Override
    public BlogResponse getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with slug: " + slug));
        blog.setViewCount(blog.getViewCount() + 1);
        blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    @Override
    public BlogResponse createBlog(BlogRequest request) {
        User currentUser = userService.getCurrentUser();

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }

        String slug = generateSlug(request.getTitle());
        if (blogRepository.findBySlug(slug).isPresent()) {
            slug = slug + "-" + System.currentTimeMillis();
        }

        Blog blog = Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .summary(request.getSummary())
                .slug(slug)
                .status(BlogStatus.DRAFT)
                .viewCount(0L)
                .author(currentUser)
                .category(category)
                .build();

        blog = blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    @Override
    public BlogResponse updateBlog(Long id, BlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!blog.getAuthor().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You can only update your own blogs");
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }

        String slug = generateSlug(request.getTitle());
        if (!slug.equals(blog.getSlug()) && blogRepository.findBySlug(slug).isPresent()) {
            slug = slug + "-" + System.currentTimeMillis();
        }

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setSummary(request.getSummary());
        blog.setSlug(slug);
        blog.setCategory(category);

        blog = blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!blog.getAuthor().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You can only delete your own blogs");
        }

        blogRepository.deleteById(id);
    }

    public Page<BlogSummaryResponse> getBlogsByAuthor(Long authorId, Pageable pageable) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + authorId));
        return blogRepository.findByAuthor(author, pageable).map(this::mapToBlogSummaryResponse);
    }

    public Page<BlogSummaryResponse> getBlogsByCategory(Long categoryId, Pageable pageable) {
        return blogRepository.findByCategoryId(categoryId, pageable)
                .map(this::mapToBlogSummaryResponse);
    }

    @Override
    public BlogResponse publishBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!blog.getAuthor().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You can only publish your own blogs");
        }

        blog.setStatus(BlogStatus.PUBLISHED);
        blog.setPublishedAt(LocalDateTime.now());
        blog = blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    @Override
    public Page<BlogSummaryResponse> searchBlogs(String query, Pageable pageable) {
        return blogRepository.searchPublishedBlogs(query, pageable)
                .map(this::mapToBlogSummaryResponse);
    }

    private String generateSlug(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
    }

    private BlogResponse mapToBlogResponse(Blog blog) {
        BlogResponse response = new BlogResponse();
        response.setId(blog.getId());
        response.setTitle(blog.getTitle());
        response.setContent(blog.getContent());
        response.setSummary(blog.getSummary());
        response.setSlug(blog.getSlug());
        response.setStatus(blog.getStatus());
        response.setViewCount(blog.getViewCount());
        response.setCreatedAt(blog.getCreatedAt());
        response.setUpdatedAt(blog.getUpdatedAt());
        response.setPublishedAt(blog.getPublishedAt());
        response.setAuthor(userService.mapToUserResponse(blog.getAuthor()));
        if (blog.getCategory() != null) {
            response.setCategory(categoryService.mapToCategoryResponse(blog.getCategory()));
        }
        return response;
    }

    private BlogSummaryResponse mapToBlogSummaryResponse(Blog blog) {
        BlogSummaryResponse response = new BlogSummaryResponse();
        response.setId(blog.getId());
        response.setTitle(blog.getTitle());
        response.setSummary(blog.getSummary());
        response.setSlug(blog.getSlug());
        response.setStatus(blog.getStatus());
        response.setViewCount(blog.getViewCount());
        response.setCreatedAt(blog.getCreatedAt());
        response.setPublishedAt(blog.getPublishedAt());
        response.setAuthor(userService.mapToUserResponse(blog.getAuthor()));
        if (blog.getCategory() != null) {
            response.setCategory(categoryService.mapToCategoryResponse(blog.getCategory()));
        }
        return response;
    }

}
