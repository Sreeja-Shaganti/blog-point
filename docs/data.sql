-- BlogPoint Initial Data Script
-- Insert default data after schema creation

-- Insert default admin user (password: admin123)
INSERT INTO users (username, email, password, full_name, role, enabled, created_at, updated_at) VALUES
('admin', 'admin@blogpoint.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Administrator', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (username) DO NOTHING;

-- Insert sample author user (password: author123)
INSERT INTO users (username, email, password, full_name, role, enabled, created_at, updated_at) VALUES
('author1', 'author1@blogpoint.com', '$2a$10$Zp5KSLdPvNxAaKxOQi/nZevPnELJVWqR.JZq3KVN0vKvDXqpY5VLy', 'John Author', 'AUTHOR', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (username) DO NOTHING;

-- Insert sample reader user (password: reader123)
INSERT INTO users (username, email, password, full_name, role, enabled, created_at, updated_at) VALUES
('reader1', 'reader1@blogpoint.com', '$2a$10$gLvYvFvtK5qqZvT8L/r5dePXVOOQ7v5gJpqVZxqp.3kX/cVwFBaEO', 'Jane Reader', 'READER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (username) DO NOTHING;

-- Insert sample categories
INSERT INTO category (name, description, slug, created_at) VALUES
('Technology', 'Posts about technology, programming, and software development', 'technology', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

INSERT INTO category (name, description, slug, created_at) VALUES
('Lifestyle', 'Lifestyle tips, personal development, and wellness articles', 'lifestyle', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

INSERT INTO category (name, description, slug, created_at) VALUES
('Travel', 'Travel experiences, tips, and destination guides', 'travel', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

INSERT INTO category (name, description, slug, created_at) VALUES
('Business', 'Business insights, entrepreneurship, and career advice', 'business', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- Insert sample published blogs
INSERT INTO blog (title, content, summary, slug, status, view_count, created_at, updated_at, published_at, author_id, category_id) VALUES
(
    'Welcome to BlogPoint',
    'BlogPoint is a modern, secure blogging platform built with Spring Boot. This platform allows users to create, manage, and share their thoughts with the world. With advanced features like JWT authentication, role-based access control, and a clean REST API, BlogPoint is perfect for both beginners and experienced bloggers.',
    'Introduction to the BlogPoint platform',
    'welcome-to-blogpoint',
    'PUBLISHED',
    42,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1,
    1
)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO blog (title, content, summary, slug, status, view_count, created_at, updated_at, published_at, author_id, category_id) VALUES
(
    'Getting Started with Spring Boot',
    'Spring Boot is a powerful framework for building Java applications quickly and efficiently. In this comprehensive guide, we''ll explore the fundamentals of Spring Boot, including dependency injection, auto-configuration, and creating RESTful APIs. Whether you''re a beginner or an experienced developer, you''ll find valuable insights here.',
    'A comprehensive guide to Spring Boot development',
    'getting-started-spring-boot',
    'PUBLISHED',
    156,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    2,
    1
)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO blog (title, content, summary, slug, status, view_count, created_at, updated_at, published_at, author_id, category_id) VALUES
(
    'PostgreSQL Best Practices',
    'PostgreSQL is one of the most advanced open-source relational databases available. This article covers essential best practices for database design, performance optimization, backup strategies, and security considerations. Learn how to get the most out of your PostgreSQL databases.',
    'Learn the best practices for PostgreSQL databases',
    'postgresql-best-practices',
    'PUBLISHED',
    89,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    2,
    1
)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO blog (title, content, summary, slug, status, view_count, created_at, updated_at, published_at, author_id, category_id) VALUES
(
    'Work-Life Balance Tips',
    'Achieving a healthy work-life balance is crucial for your mental and physical well-being. This article provides practical tips for managing your time effectively, setting boundaries, and maintaining your health while pursuing your career goals. Discover strategies that successful professionals use to stay balanced.',
    'Essential tips for maintaining work-life balance',
    'work-life-balance-tips',
    'PUBLISHED',
    73,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1,
    2
)
ON CONFLICT (slug) DO NOTHING;

-- Insert sample draft blog
INSERT INTO blog (title, content, summary, slug, status, view_count, created_at, updated_at, published_at, author_id, category_id) VALUES
(
    'Top 10 Travel Destinations in 2026',
    'Draft: Planning your next adventure? Check out these amazing destinations that offer unique experiences, breathtaking landscapes, and unforgettable memories. From tropical beaches to mountain peaks, we have something for every traveler.',
    'Discover the best travel destinations for 2026',
    'top-10-travel-destinations-2026',
    'DRAFT',
    0,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL,
    2,
    3
)
ON CONFLICT (slug) DO NOTHING;

-- Insert sample comments
INSERT INTO comment (content, created_at, updated_at, user_id, blog_id) VALUES
(
    'Great introduction to BlogPoint! Looking forward to exploring the platform.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    3,
    1
)
ON CONFLICT DO NOTHING;

INSERT INTO comment (content, created_at, updated_at, user_id, blog_id) VALUES
(
    'Very informative article on Spring Boot. This helped me understand the framework better.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    3,
    2
)
ON CONFLICT DO NOTHING;

INSERT INTO comment (content, created_at, updated_at, user_id, blog_id) VALUES
(
    'Excellent tips on work-life balance. I''m going to implement these suggestions in my life.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    2,
    4
)
ON CONFLICT DO NOTHING;
