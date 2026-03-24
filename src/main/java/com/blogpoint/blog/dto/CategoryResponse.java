package com.blogpoint.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String slug;
    private LocalDateTime createdAt;

}
