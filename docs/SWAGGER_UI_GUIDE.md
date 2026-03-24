# Swagger UI - Complete Guide for API Testing

## 🎯 Access Swagger UI

Open your browser and navigate to:
```
http://localhost:8080/swagger-ui.html
```

You should see an interactive API documentation interface with all 28 endpoints listed.

---

## 📋 WHAT YOU CAN DO IN SWAGGER UI

### 1. View API Documentation
- All 28 endpoints documented
- Request/response schemas
- Parameter descriptions
- HTTP status codes

### 2. Test Endpoints Interactively
- Send requests directly from UI
- View responses in JSON
- See response times
- Check HTTP status codes

### 3. Manage Authentication
- Login and get JWT token
- Use token for protected endpoints
- Refresh tokens
- Manage authorization

---

## 🔐 AUTHENTICATING IN SWAGGER UI

### Step 1: Login to Get Token
1. Scroll to **Authentication** section
2. Click **POST /api/auth/login**
3. Click **Try it out** button
4. Enter credentials:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
5. Click **Execute** button
6. Copy the `accessToken` from response

### Step 2: Authorize Future Requests
1. Click **Authorize** button (top-right)
2. In "bearer-jwt" field, enter: `Bearer YOUR_ACCESS_TOKEN`
3. Click **Authorize**
4. Click **Close**

Now all protected endpoints will automatically use your token!

---

## 📡 TESTING API ENDPOINTS

### Example 1: Get All Published Blogs (No Auth Required)
1. Find **GET /api/blogs** under Blog Management
2. Click **Try it out**
3. Click **Execute**
4. View the response with all published blogs

### Example 2: Create a New Blog (Auth Required)
1. Make sure you're authenticated (see above steps)
2. Find **POST /api/blogs** under Blog Management
3. Click **Try it out**
4. Enter request body:
   ```json
   {
     "title": "My Test Blog",
     "content": "This is test content for the blog",
     "summary": "A short summary",
     "categoryId": 1
   }
   ```
5. Click **Execute**
6. See response with created blog details

### Example 3: Get Comments on a Blog
1. Find **GET /api/blogs/{id}/comments** under Comment Management
2. Click **Try it out**
3. Enter `id`: `1`
4. Click **Execute**
5. See all comments on that blog

---

## 🎨 SWAGGER UI FEATURES

### Expandable Sections
- Click endpoint to expand/collapse
- See request parameters, body, responses
- Copy example curl commands

### Response Examples
- View successful response (200, 201)
- See error responses (400, 401, 404, 500)
- Understand response structure

### Model Schemas
- Click on model types to see structure
- Understand nested objects
- See all available fields

### Try It Out
- Enter parameters manually
- See curl command being sent
- Get real responses from server

---

## 🔍 API DOCUMENTATION AVAILABLE

### Response Documentation
- **200 OK** - Successful request
- **201 Created** - Resource created
- **204 No Content** - Successful deletion
- **400 Bad Request** - Invalid input
- **401 Unauthorized** - Authentication required
- **403 Forbidden** - Access denied (insufficient role)
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

### Parameter Types
- **Path Parameters** - In URL (e.g., {id})
- **Query Parameters** - In URL (e.g., ?page=0&size=10)
- **Request Body** - JSON in body
- **Headers** - Authorization and content-type

---

## 💡 TESTING WORKFLOW

### 1. Start with Public Endpoints
```
GET /api/blogs              - Get published blogs (no auth)
GET /api/categories         - Get categories (no auth)
GET /api/blogs/search       - Search blogs (no auth)
```

### 2. Test Authentication
```
POST /api/auth/register     - Register new user
POST /api/auth/login        - Login and get token
GET  /api/auth/me           - Get current user info
```

### 3. Test Blog Operations
```
POST   /api/blogs           - Create blog (auth required)
PUT    /api/blogs/{id}      - Update blog (auth required)
DELETE /api/blogs/{id}      - Delete blog (auth required)
PATCH  /api/blogs/{id}/publish - Publish blog (auth required)
```

### 4. Test Comments
```
GET  /api/blogs/{id}/comments      - Get comments
POST /api/blogs/{id}/comments      - Add comment
PUT  /api/comments/{id}            - Update comment
DELETE /api/comments/{id}          - Delete comment
```

### 5. Test Admin Functions
```
GET    /api/users           - Get all users (admin only)
PUT    /api/users/{id}/role - Change user role (admin only)
POST   /api/categories      - Create category (admin only)
```

---

## 🧪 SAMPLE TEST DATA

Default users to test with:
| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| author1 | author123 | AUTHOR |
| reader1 | reader123 | READER |

Sample categories (ID 1-4):
- Technology
- Lifestyle
- Travel
- Business

Sample blogs (ID 1-5):
- Multiple published blogs
- Draft blogs
- Different categories

---

## 🔗 ADDITIONAL ENDPOINTS

### OpenAPI Specification
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/v3/api-docs
- **API Docs (YAML)**: http://localhost:8080/v3/api-docs.yaml

### Import to Tools
- Copy JSON from `/v3/api-docs`
- Import to Postman, Insomnia, or other tools
- Use for automated testing

---

## 🚀 ADVANCED FEATURES

### Sorting & Pagination
Many list endpoints support:
- **page** - Page number (0-indexed)
- **size** - Items per page
- **sort** - Sort field and direction

Example:
```
GET /api/blogs?page=0&size=10&sort=createdAt,desc
```

In Swagger UI:
1. Click endpoint
2. Scroll to query parameters
3. Enter values in input fields
4. Execute

### Filtering & Searching
- **Search blogs**: GET /api/blogs/search?query=spring
- **By author**: GET /api/blogs/author/{authorId}
- **By category**: GET /api/blogs/category/{categoryId}

---

## 📝 USEFUL TIPS

1. **Try Execute First** - Click "Try it out" before entering data
2. **Check Response** - Scroll down to see full response
3. **View Curl Command** - See the actual curl being sent
4. **Copy Response** - Double-click response to copy
5. **Check Status Code** - Green = success, Red = error
6. **Read Error Message** - Error responses explain what went wrong

---

## 🐛 TROUBLESHOOTING

### Swagger UI Not Loading
- Check if app is running: http://localhost:8080/api/blogs
- Clear browser cache
- Try incognito/private window
- Restart application

### Authorization Not Working
- Make sure you clicked "Authorize" button
- Token might have expired (valid for 1 hour)
- Get new token: POST /api/auth/login
- Check if endpoint requires authentication

### Endpoint Returns 403 Forbidden
- You don't have the required role
- Try with "admin" user instead
- Check endpoint documentation for required role

### Cannot Create/Update Resources
- Make sure you're authenticated
- Provide all required fields
- Check field formats (email, numbers, etc.)
- Review error message in response

---

## 🎯 NEXT STEPS

1. **Open Swagger UI**: http://localhost:8080/swagger-ui.html
2. **Login**: Get token from POST /api/auth/login
3. **Authorize**: Add token in Authorize button
4. **Test**: Try different endpoints
5. **Explore**: See all 28 endpoints in action

---

**Happy API Testing! 🚀**

Swagger UI is your interactive API documentation and testing playground!
