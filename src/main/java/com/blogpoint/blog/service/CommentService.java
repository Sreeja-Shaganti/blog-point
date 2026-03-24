package com.blogpoint.blog.service;

import com.blogpoint.blog.dto.CommentRequest;
import com.blogpoint.blog.dto.CommentResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentResponse> getCommentsByBlog(Long blogId, Pageable pageable);

    CommentResponse createComment(Long blogId, @Valid CommentRequest request);

    CommentResponse updateComment(Long id, @Valid CommentRequest request);

    void deleteComment(Long id);
}
