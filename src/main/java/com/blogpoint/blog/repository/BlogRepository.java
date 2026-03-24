package com.blogpoint.blog.repository;

import com.blogpoint.blog.model.Blog;
import com.blogpoint.blog.model.BlogStatus;
import com.blogpoint.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findBySlug(String slug);

    Page<Blog> findByStatus(BlogStatus status, Pageable pageable);

    Page<Blog> findByAuthor(User author, Pageable pageable);

    Page<Blog> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.status = 'PUBLISHED' AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Blog> searchPublishedBlogs(@Param("query") String query, Pageable pageable);

}
