# API Documentation

Complete API documentation for BlogPoint with request/response examples.

## Base URL

```
http://localhost:8080/api
```

## Authentication

All protected endpoints require a JWT token in the `Authorization` header:

```
Authorization: Bearer <JWT_TOKEN>
```

Get a token by logging in:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer"
}
```

## Authentication Endpoints

### POST /auth/register
Register a new user

**Request Body:**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123",
  "fullName": "New User"
}
```

**Response (201 Created):**
```json
{
  "message": "User registered successfully"
}
```

**Validation Rules:**
- Username: 3-50 characters
- Email: Valid email format
- Password: Minimum 6 characters

---

### POST /auth/login
User login

**Request Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDkzOTY0MCwiZXhwIjoxNzEwOTQzMjQwfQ.signature",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDkzOTY0MCwiZXhwIjoxNzExNTQ0NDQwfQ.signature",
  "tokenType": "Bearer"
}
```

---

### POST /auth/refresh
Refresh access token

**Request Parameters:**
```
refreshToken=<REFRESH_TOKEN>
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer"
}
```

---

### GET /auth/me
Get current user details (Requires Authentication)

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@blogpoint.com",
  "fullName": "Administrator",
  "role": "ADMIN",
  "enabled": true,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T11:00:00"
}
```

---

## User Management Endpoints (Admin Only)

### GET /users
Get all users with pagination

**Query Parameters:**
```
page=0&size=10&sort=createdAt,desc
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "username": "admin",
      "email": "admin@blogpoint.com",
      "fullName": "Administrator",
      "role": "ADMIN",
      "enabled": true,
      "createdAt": "2026-03-23T11:00:00",
      "updatedAt": "2026-03-23T11:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

---

### GET /users/{id}
Get user by ID

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@blogpoint.com",
  "fullName": "Administrator",
  "role": "ADMIN",
  "enabled": true,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T11:00:00"
}
```

---

### PUT /users/{id}
Update user (Admin only)

**Request Body:**
```json
{
  "username": "admin_updated",
  "email": "admin@blogpoint.com",
  "fullName": "Admin User",
  "role": "ADMIN",
  "enabled": true
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "admin_updated",
  "email": "admin@blogpoint.com",
  "fullName": "Admin User",
  "role": "ADMIN",
  "enabled": true,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T12:30:00"
}
```

---

### DELETE /users/{id}
Delete user (Admin only)

**Response (204 No Content):**
```
(empty)
```

---

### PUT /users/{id}/role
Change user role (Admin only)

**Query Parameters:**
```
role=AUTHOR
```

Valid roles: `ADMIN`, `AUTHOR`, `READER`

**Response (200 OK):**
```json
{
  "id": 2,
  "username": "author1",
  "email": "author1@blogpoint.com",
  "fullName": "John Author",
  "role": "AUTHOR",
  "enabled": true,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T12:30:00"
}
```

---

## Blog Endpoints

### GET /blogs
Get all published blogs (Public)

**Query Parameters:**
```
page=0&size=10&sort=publishedAt,desc
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "title": "Welcome to BlogPoint",
      "summary": "Introduction to the BlogPoint platform",
      "slug": "welcome-to-blogpoint",
      "status": "PUBLISHED",
      "viewCount": 42,
      "createdAt": "2026-03-23T11:00:00",
      "publishedAt": "2026-03-23T11:00:00",
      "author": {
        "id": 1,
        "username": "admin",
        "email": "admin@blogpoint.com",
        "fullName": "Administrator",
        "role": "ADMIN",
        "enabled": true,
        "createdAt": "2026-03-23T11:00:00",
        "updatedAt": "2026-03-23T11:00:00"
      },
      "category": {
        "id": 1,
        "name": "Technology",
        "description": "Posts about technology and programming",
        "slug": "technology",
        "createdAt": "2026-03-23T11:00:00"
      }
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

---

### GET /blogs/{id}
Get blog by ID (Public, increments view count)

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Welcome to BlogPoint",
  "content": "BlogPoint is a modern, secure blogging platform...",
  "summary": "Introduction to the BlogPoint platform",
  "slug": "welcome-to-blogpoint",
  "status": "PUBLISHED",
  "viewCount": 43,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T11:00:00",
  "publishedAt": "2026-03-23T11:00:00",
  "author": { ... },
  "category": { ... }
}
```

---

### GET /blogs/slug/{slug}
Get blog by slug (Public)

**Response (200 OK):**
Same as GET /blogs/{id}

---

### POST /blogs
Create new blog (Author, Admin)

**Request Body:**
```json
{
  "title": "My First Blog Post",
  "content": "This is the content of my first blog post. It can be very long and contain rich text.",
  "summary": "A brief summary of the blog post",
  "categoryId": 1
}
```

**Response (200 OK):**
```json
{
  "id": 6,
  "title": "My First Blog Post",
  "content": "This is the content of my first blog post...",
  "summary": "A brief summary of the blog post",
  "slug": "my-first-blog-post",
  "status": "DRAFT",
  "viewCount": 0,
  "createdAt": "2026-03-23T12:30:00",
  "updatedAt": "2026-03-23T12:30:00",
  "publishedAt": null,
  "author": { ... },
  "category": { ... }
}
```

---

### PUT /blogs/{id}
Update blog (Owner or Admin)

**Request Body:**
```json
{
  "title": "Updated Blog Title",
  "content": "Updated content...",
  "summary": "Updated summary",
  "categoryId": 2
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Updated Blog Title",
  "content": "Updated content...",
  "summary": "Updated summary",
  "slug": "updated-blog-title",
  "status": "DRAFT",
  "viewCount": 43,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T12:35:00",
  "publishedAt": null,
  "author": { ... },
  "category": { ... }
}
```

---

### DELETE /blogs/{id}
Delete blog (Owner or Admin)

**Response (204 No Content):**
```
(empty)
```

---

### PATCH /blogs/{id}/publish
Publish blog (Owner or Admin)

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Welcome to BlogPoint",
  "content": "BlogPoint is a modern...",
  "summary": "Introduction to the BlogPoint platform",
  "slug": "welcome-to-blogpoint",
  "status": "PUBLISHED",
  "viewCount": 43,
  "createdAt": "2026-03-23T11:00:00",
  "updatedAt": "2026-03-23T12:40:00",
  "publishedAt": "2026-03-23T12:40:00",
  "author": { ... },
  "category": { ... }
}
```

---

### GET /blogs/author/{authorId}
Get blogs by author (Public)

**Query Parameters:**
```
page=0&size=10&sort=publishedAt,desc
```

**Response:** Same as GET /blogs

---

### GET /blogs/category/{categoryId}
Get blogs by category (Public)

**Query Parameters:**
```
page=0&size=10&sort=publishedAt,desc
```

**Response:** Same as GET /blogs

---

### GET /blogs/search
Search blogs (Public)

**Query Parameters:**
```
query=spring&page=0&size=10
```

**Response:** Same as GET /blogs

---

## Category Endpoints

### GET /categories
Get all categories (Public)

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Technology",
    "description": "Posts about technology and programming",
    "slug": "technology",
    "createdAt": "2026-03-23T11:00:00"
  },
  {
    "id": 2,
    "name": "Lifestyle",
    "description": "Lifestyle and personal development",
    "slug": "lifestyle",
    "createdAt": "2026-03-23T11:00:00"
  }
]
```

---

### GET /categories/{id}
Get category by ID (Public)

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Technology",
  "description": "Posts about technology and programming",
  "slug": "technology",
  "createdAt": "2026-03-23T11:00:00"
}
```

---

### POST /categories
Create category (Admin only)

**Request Body:**
```json
{
  "name": "Health & Fitness",
  "description": "Articles about health, fitness, and wellness"
}
```

**Response (200 OK):**
```json
{
  "id": 5,
  "name": "Health & Fitness",
  "description": "Articles about health, fitness, and wellness",
  "slug": "health-fitness",
  "createdAt": "2026-03-23T12:40:00"
}
```

---

### PUT /categories/{id}
Update category (Admin only)

**Request Body:**
```json
{
  "name": "Health & Wellness",
  "description": "Updated description"
}
```

**Response (200 OK):**
```json
{
  "id": 5,
  "name": "Health & Wellness",
  "description": "Updated description",
  "slug": "health-wellness",
  "createdAt": "2026-03-23T12:40:00"
}
```

---

### DELETE /categories/{id}
Delete category (Admin only)

**Response (204 No Content):**
```
(empty)
```

---

## Comment Endpoints

### GET /blogs/{blogId}/comments
Get comments for a blog (Public)

**Query Parameters:**
```
page=0&size=10
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "content": "Great introduction to BlogPoint!",
      "createdAt": "2026-03-23T11:30:00",
      "updatedAt": "2026-03-23T11:30:00",
      "user": {
        "id": 3,
        "username": "reader1",
        "email": "reader1@blogpoint.com",
        "fullName": "Jane Reader",
        "role": "READER",
        "enabled": true,
        "createdAt": "2026-03-23T11:00:00",
        "updatedAt": "2026-03-23T11:00:00"
      }
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

---

### POST /blogs/{blogId}/comments
Create comment (Authenticated)

**Request Body:**
```json
{
  "content": "This is a great blog post!"
}
```

**Response (200 OK):**
```json
{
  "id": 5,
  "content": "This is a great blog post!",
  "createdAt": "2026-03-23T12:45:00",
  "updatedAt": "2026-03-23T12:45:00",
  "user": {
    "id": 3,
    "username": "reader1",
    "email": "reader1@blogpoint.com",
    "fullName": "Jane Reader",
    "role": "READER",
    "enabled": true,
    "createdAt": "2026-03-23T11:00:00",
    "updatedAt": "2026-03-23T11:00:00"
  }
}
```

---

### PUT /comments/{id}
Update comment (Owner or Admin)

**Request Body:**
```json
{
  "content": "Updated comment content"
}
```

**Response (200 OK):**
```json
{
  "id": 5,
  "content": "Updated comment content",
  "createdAt": "2026-03-23T12:45:00",
  "updatedAt": "2026-03-23T12:50:00",
  "user": { ... }
}
```

---

### DELETE /comments/{id}
Delete comment (Owner or Admin)

**Response (204 No Content):**
```
(empty)
```

---

## Error Responses

### 400 Bad Request
```json
{
  "username": "Username must be between 3 and 50 characters",
  "email": "Email should be valid"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2026-03-23T12:50:00",
  "status": 401,
  "message": "Unauthorized",
  "path": "uri=/api/auth/me"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2026-03-23T12:50:00",
  "status": 403,
  "message": "Access denied",
  "path": "uri=/api/users"
}
```

### 404 Not Found
```json
{
  "timestamp": "2026-03-23T12:50:00",
  "status": 404,
  "message": "Blog not found with id: 999",
  "path": "uri=/api/blogs/999"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2026-03-23T12:50:00",
  "status": 500,
  "message": "Internal server error",
  "path": "uri=/api/blogs"
}
```

---

## Rate Limiting & Best Practices

- **Page Size**: Use `size` parameter (default 10, max 100)
- **Sorting**: Use `sort=field,asc|desc` (example: `sort=createdAt,desc`)
- **Authentication**: Token expires in 1 hour, use refresh token to get new one
- **Token Refresh**: Refresh token expires in 7 days

---

## Sample API Usage Flow

```bash
# 1. Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "fullName": "Test User"
  }'

# 2. Login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }' | jq -r '.accessToken')

# 3. Get current user
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer $TOKEN"

# 4. Get published blogs
curl -X GET "http://localhost:8080/api/blogs?page=0&size=10"

# 5. Create blog
curl -X POST http://localhost:8080/api/blogs \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "title": "My Blog",
    "content": "Blog content",
    "summary": "Summary",
    "categoryId": 1
  }'

# 6. Search blogs
curl -X GET "http://localhost:8080/api/blogs/search?query=spring&page=0"

# 7. Get comments
curl -X GET "http://localhost:8080/api/blogs/1/comments"

# 8. Add comment
curl -X POST http://localhost:8080/api/blogs/1/comments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "content": "Great post!"
  }'
```

---

## Swagger UI

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

You can test all endpoints directly from the Swagger UI interface.
