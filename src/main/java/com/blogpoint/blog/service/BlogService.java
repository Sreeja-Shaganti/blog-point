package com.blogpoint.blog.service;

import com.blogpoint.blog.dto.BlogRequest;
import com.blogpoint.blog.dto.BlogResponse;
import com.blogpoint.blog.dto.BlogSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Page<BlogSummaryResponse> getAllPublishedBlogs(Pageable pageable);

    BlogResponse getBlogById(Long id);

    BlogResponse getBlogBySlug(String slug);

    BlogResponse createBlog(@Valid BlogRequest request);

    BlogResponse updateBlog(Long id, @Valid BlogRequest request);

    void deleteBlog(Long id);

    Page<BlogSummaryResponse> getBlogsByAuthor(Long authorId, Pageable pageable);

    Page<BlogSummaryResponse> getBlogsByCategory(Long categoryId, Pageable pageable);

    BlogResponse publishBlog(Long id);

    Page<BlogSummaryResponse> searchBlogs(String query, Pageable pageable);
}
