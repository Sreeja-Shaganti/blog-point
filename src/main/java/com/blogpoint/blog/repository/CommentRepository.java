package com.blogpoint.blog.repository;

import com.blogpoint.blog.model.Blog;
import com.blogpoint.blog.model.Comment;
import com.blogpoint.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByBlog(Blog blog, Pageable pageable);

    Page<Comment> findByUser(User user, Pageable pageable);

}
