package com.blogpoint.blog.controller;

import com.blogpoint.blog.dto.BlogRequest;
import com.blogpoint.blog.dto.BlogResponse;
import com.blogpoint.blog.dto.BlogSummaryResponse;
import com.blogpoint.blog.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<BlogSummaryResponse>> getAllPublishedBlogs(Pageable pageable) {
        Page<BlogSummaryResponse> blogs = blogService.getAllPublishedBlogs(pageable);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        BlogResponse blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<BlogResponse> getBlogBySlug(@PathVariable String slug) {
        BlogResponse blog = blogService.getBlogBySlug(slug);
        return ResponseEntity.ok(blog);
    }

    @PostMapping
    @PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity<BlogResponse> createBlog(@Valid @RequestBody BlogRequest request) {
        BlogResponse blog = blogService.createBlog(request);
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequest request) {
        BlogResponse blog = blogService.updateBlog(id, request);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<BlogSummaryResponse>> getBlogsByAuthor(@PathVariable Long authorId, Pageable pageable) {
        Page<BlogSummaryResponse> blogs = blogService.getBlogsByAuthor(authorId, pageable);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<BlogSummaryResponse>> getBlogsByCategory(@PathVariable Long categoryId, Pageable pageable) {
        Page<BlogSummaryResponse> blogs = blogService.getBlogsByCategory(categoryId, pageable);
        return ResponseEntity.ok(blogs);
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity<BlogResponse> publishBlog(@PathVariable Long id) {
        BlogResponse blog = blogService.publishBlog(id);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BlogSummaryResponse>> searchBlogs(@RequestParam String query, Pageable pageable) {
        Page<BlogSummaryResponse> blogs = blogService.searchBlogs(query, pageable);
        return ResponseEntity.ok(blogs);
    }

}
