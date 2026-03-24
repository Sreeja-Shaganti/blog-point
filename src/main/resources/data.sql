-- Insert default admin user
-- Password is BCrypt encoded for 'admin123'
INSERT INTO users (username, email, password, full_name, role, enabled, created_at, updated_at) VALUES
('admin', 'admin@blogpoint.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Administrator', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample categories
INSERT INTO category (name, description, slug, created_at) VALUES
('Technology', 'Posts about technology and programming', 'technology', CURRENT_TIMESTAMP),
('Lifestyle', 'Lifestyle and personal development', 'lifestyle', CURRENT_TIMESTAMP),
('Travel', 'Travel experiences and tips', 'travel', CURRENT_TIMESTAMP);

-- Insert sample blog posts
INSERT INTO blog (title, content, summary, slug, status, view_count, created_at, updated_at, published_at, author_id, category_id) VALUES
('Welcome to BlogPoint', 'This is the first blog post on BlogPoint platform.', 'Introduction to the platform', 'welcome-to-blogpoint', 'PUBLISHED', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
('Getting Started with Spring Boot', 'Learn how to build applications with Spring Boot.', 'Spring Boot tutorial', 'getting-started-spring-boot', 'PUBLISHED', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);
