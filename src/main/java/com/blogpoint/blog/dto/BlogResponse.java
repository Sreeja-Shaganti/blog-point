package com.blogpoint.blog.dto;

import com.blogpoint.blog.model.BlogStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogResponse {

    private Long id;
    private String title;
    private String content;
    private String summary;
    private String slug;
    private BlogStatus status;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private UserResponse author;
    private CategoryResponse category;

}
