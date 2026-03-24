package com.blogpoint.blog.service.impl;

import com.blogpoint.blog.dto.CommentRequest;
import com.blogpoint.blog.dto.CommentResponse;
import com.blogpoint.blog.exception.ResourceNotFoundException;
import com.blogpoint.blog.exception.UnauthorizedException;
import com.blogpoint.blog.model.Blog;
import com.blogpoint.blog.model.Comment;
import com.blogpoint.blog.model.User;
import com.blogpoint.blog.model.UserRole;
import com.blogpoint.blog.repository.BlogRepository;
import com.blogpoint.blog.repository.CommentRepository;
import com.blogpoint.blog.service.CommentService;
import com.blogpoint.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;

    private final UserService userService;

    @Override
    public Page<CommentResponse> getCommentsByBlog(Long blogId, Pageable pageable) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + blogId));
        return commentRepository.findByBlog(blog, pageable)
                .map(this::mapToCommentResponse);
    }

    @Override
    public CommentResponse createComment(Long blogId, CommentRequest request) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + blogId));

        User currentUser = userService.getCurrentUser();

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(currentUser)
                .blog(blog)
                .build();

        comment = commentRepository.save(comment);
        return mapToCommentResponse(comment);
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!comment.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You can only update your own comments");
        }

        comment.setContent(request.getContent());
        comment = commentRepository.save(comment);
        return mapToCommentResponse(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!comment.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("You can only delete your own comments");
        }

        commentRepository.deleteById(id);
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());
        response.setUser(userService.mapToUserResponse(comment.getUser()));
        return response;
    }

}
