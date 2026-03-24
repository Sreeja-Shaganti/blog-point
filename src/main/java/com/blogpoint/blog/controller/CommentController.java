package com.blogpoint.blog.controller;

import com.blogpoint.blog.dto.CommentRequest;
import com.blogpoint.blog.dto.CommentResponse;
import com.blogpoint.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/blogs/{blogId}/comments")
    public ResponseEntity<Page<CommentResponse>> getCommentsByBlog(@PathVariable Long blogId, Pageable pageable) {
        Page<CommentResponse> comments = commentService.getCommentsByBlog(blogId, pageable);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/blogs/{blogId}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long blogId, @Valid @RequestBody CommentRequest request) {
        CommentResponse comment = commentService.createComment(blogId, request);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequest request) {
        CommentResponse comment = commentService.updateComment(id, request);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
