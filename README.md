# BlogPoint - Secure Blogging Platform

A comprehensive Spring Boot backend application for a secure blogging platform with JWT authentication, role-based access control, and PostgreSQL database.

## ✨ Features

- **JWT Authentication**: Secure token-based authentication with refresh tokens
- **Role-Based Access Control**: Three user roles (ADMIN, AUTHOR, READER) with specific permissions
- **Blog Management**: Create, read, update, delete blogs with status management
- **Comment System**: Users can comment on blogs with edit/delete permissions
- **Category Management**: Organize blogs by categories
- **Search Functionality**: Search blogs by title or content
- **Pagination & Sorting**: Efficient data retrieval with configurable page sizes
- **RESTful APIs**: Well-designed REST endpoints with proper HTTP methods
- **Swagger Documentation**: Interactive API documentation
- **Docker Support**: Containerized deployment with Docker Compose
- **Database Auditing**: Automatic tracking of creation and modification timestamps

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database access
- **PostgreSQL** - Relational database
- **JWT (JSON Web Tokens)** - Token-based authentication
- **Lombok** - Reduce boilerplate code
- **Swagger/OpenAPI** - API documentation
- **Docker & Docker Compose** - Containerization
- **Maven** - Build tool

## 📋 Prerequisites

- **Java 21** or higher
- **Maven 3.6+** or use Maven wrapper
- **PostgreSQL 12+** (for manual setup)
- **Docker Desktop** (for Docker setup)

## 🚀 Quick Start

### Option 1: Docker (Recommended)

```bash
# Clone or navigate to project
cd D:\SB\blog-point-app

# Start with Docker Compose
docker-compose up --build
```

Access the application:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html

For detailed Docker setup, see [docs/DOCKER_SETUP.md](./docs/DOCKER_SETUP.md)

### Option 2: Manual Setup (PostgreSQL)

```bash
# Clone or navigate to project
cd D:\SB\blog-point-app

# Setup PostgreSQL database (see docs/MANUAL_SETUP.md for detailed instructions)
# Then build and run
mvn clean install
mvn spring-boot:run
```

For detailed manual setup, see [docs/MANUAL_SETUP.md](./docs/MANUAL_SETUP.md)

## 📚 Documentation

### Setup & Installation
- **[Docker Setup](./docs/DOCKER_SETUP.md)** - Complete Docker and Docker Compose guide
- **[Manual Setup](./docs/MANUAL_SETUP.md)** - PostgreSQL installation and configuration

### Database
- **[schema.sql](./docs/schema.sql)** - Database table creation script
- **[data.sql](./docs/data.sql)** - Sample data insertion script

### API Reference
- **[API Documentation](./docs/API_DOCUMENTATION.md)** - Complete API endpoints with examples
- **[Swagger UI](http://localhost:8080/swagger-ui.html)** - Interactive API testing

## 🔑 Default Credentials

After setup, use these credentials to login:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| author1 | author123 | AUTHOR |
| reader1 | reader123 | READER |

## 📡 API Endpoints

### Authentication
```
POST   /api/auth/register      - Register new user
POST   /api/auth/login         - Login user (get JWT token)
POST   /api/auth/refresh       - Refresh access token
GET    /api/auth/me            - Get current user details
```

### User Management (Admin only)
```
GET    /api/users              - Get all users
GET    /api/users/{id}         - Get user by ID
PUT    /api/users/{id}         - Update user
DELETE /api/users/{id}         - Delete user
PUT    /api/users/{id}/role    - Change user role
```

### Blog Management
```
GET    /api/blogs              - Get published blogs
GET    /api/blogs/{id}         - Get blog by ID
GET    /api/blogs/slug/{slug}  - Get blog by slug
POST   /api/blogs              - Create blog (Author/Admin)
PUT    /api/blogs/{id}         - Update blog (Owner/Admin)
DELETE /api/blogs/{id}         - Delete blog (Owner/Admin)
GET    /api/blogs/author/{id}  - Get blogs by author
GET    /api/blogs/category/{id} - Get blogs by category
PATCH  /api/blogs/{id}/publish - Publish blog (Owner/Admin)
GET    /api/blogs/search       - Search blogs by query
```

### Category Management
```
GET    /api/categories         - Get all categories
GET    /api/categories/{id}    - Get category by ID
POST   /api/categories         - Create category (Admin)
PUT    /api/categories/{id}    - Update category (Admin)
DELETE /api/categories/{id}    - Delete category (Admin)
```

### Comment Management
```
GET    /api/blogs/{id}/comments     - Get blog comments
POST   /api/blogs/{id}/comments     - Add comment (Authenticated)
PUT    /api/comments/{id}           - Update comment (Owner/Admin)
DELETE /api/comments/{id}           - Delete comment (Owner/Admin)
```

## 🧪 Testing Endpoints

### Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "fullName": "Test User"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### Get Published Blogs (No Auth Required)
```bash
curl -X GET "http://localhost:8080/api/blogs?page=0&size=10"
```

### Create a Blog (With JWT Token)
```bash
curl -X POST http://localhost:8080/api/blogs \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "My First Blog",
    "content": "This is the content of my first blog post.",
    "summary": "A summary of the blog",
    "categoryId": 1
  }'
```

See [docs/API_DOCUMENTATION.md](./docs/API_DOCUMENTATION.md) for complete API documentation and examples.

## 📁 Project Structure

```
blog-point-app/
├── docs/
│   ├── API_DOCUMENTATION.md    - Complete API reference
│   ├── DOCKER_SETUP.md         - Docker setup guide
│   ├── MANUAL_SETUP.md         - Manual setup guide
│   ├── schema.sql              - Database schema
│   └── data.sql                - Initial data
├── src/
│   ├── main/
│   │   ├── java/com/blogpoint/
│   │   │   ├── BlogPointApplication.java
│   │   │   ├── controller/     - REST controllers
│   │   │   ├── dto/            - Data transfer objects
│   │   │   ├── exception/      - Exception handling
│   │   │   ├── model/          - JPA entities
│   │   │   ├── repository/     - Data repositories
│   │   │   ├── security/       - Security config & JWT
│   │   │   ├── service/        - Business logic
│   │   │   └── config/         - Application config
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/java/com/blogpoint/
├── Dockerfile                   - Docker image definition
├── docker-compose.yml           - Multi-container setup
├── pom.xml                      - Maven dependencies
├── .gitignore
└── README.md
```

## ⚙️ Configuration

### Application Properties

**Development** (`application-dev.properties`):
```properties
spring.jpa.hibernate.ddl-auto=update
logging.level.com.blogpoint=DEBUG
```

**Production** (`application-prod.properties`):
```properties
spring.jpa.hibernate.ddl-auto=validate
logging.level.com.blogpoint=INFO
```

### Environment Variables (Docker)
```yaml
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/blogpoint
SPRING_DATASOURCE_USERNAME=blogpoint
SPRING_DATASOURCE_PASSWORD=password
APP_JWT_SECRET=mySecretKey
APP_JWT_EXPIRATION=3600000
APP_JWT_REFRESH_EXPIRATION=604800000
```

## 🔐 Security Features

- **Password Encryption**: BCrypt hashing algorithm
- **JWT Authentication**: Token-based stateless authentication
- **Role-Based Authorization**: @PreAuthorize annotations on endpoints
- **CORS Configuration**: Configured for frontend integration
- **CSRF Protection**: Disabled for REST API (stateless)
- **Stateless Sessions**: No server-side session management

### Access Control Matrix

| Role | Endpoints |
|------|-----------|
| **ADMIN** | All endpoints (full CRUD) |
| **AUTHOR** | Create/Update/Delete own blogs, manage comments |
| **READER** | View blogs, create/delete own comments |
| **Anonymous** | View published blogs, categories, search |

## 🗄️ Database Schema

### Users Table
- `id` - Primary key
- `username` - Unique username
- `email` - Unique email address
- `password` - BCrypt encrypted
- `role` - ADMIN, AUTHOR, or READER
- `fullName` - User's full name
- `enabled` - Account status
- `createdAt`, `updatedAt` - Audit fields

### Blogs Table
- `id` - Primary key
- `title`, `content`, `summary` - Blog details
- `slug` - URL-friendly identifier
- `status` - DRAFT, PUBLISHED, or ARCHIVED
- `viewCount` - Number of views
- `author_id` - Foreign key to User
- `category_id` - Foreign key to Category
- `createdAt`, `updatedAt`, `publishedAt` - Timestamps

### Categories Table
- `id` - Primary key
- `name`, `slug`, `description` - Category details
- `createdAt` - Audit field

### Comments Table
- `id` - Primary key
- `content` - Comment text
- `user_id` - Foreign key to User
- `blog_id` - Foreign key to Blog
- `createdAt`, `updatedAt` - Timestamps

See [docs/schema.sql](./docs/schema.sql) for complete schema definition.

## 🐛 Troubleshooting

### Docker Issues
- **Image not found**: Dockerfile now uses `eclipse-temurin:21-jdk-slim` (fixed)
- **Port already in use**: Change port in docker-compose.yml or kill process on 8080
- See [docs/DOCKER_SETUP.md](./docs/DOCKER_SETUP.md) for more solutions

### PostgreSQL Issues
- **Connection refused**: Ensure PostgreSQL is running
- **Database not found**: Create database as shown in [docs/MANUAL_SETUP.md](./docs/MANUAL_SETUP.md)
- **Password authentication failed**: Check credentials in application.properties

### Build Issues
- **Maven compilation errors**: Run `mvn clean compile`
- **Dependency issues**: Clear Maven cache: `mvn clean`
- **Java version mismatch**: Verify Java 21: `java -version`

See detailed troubleshooting in [docs/MANUAL_SETUP.md](./docs/MANUAL_SETUP.md) and [docs/DOCKER_SETUP.md](./docs/DOCKER_SETUP.md).

## 📊 Performance Optimization

- **Connection Pooling**: HikariCP with configurable pool size
- **Database Indexing**: Indexes on frequently queried columns
- **Pagination**: Efficient data retrieval with configurable page sizes
- **Lazy Loading**: JPA relationships configured for lazy loading
- **Caching**: Can be added using Spring Cache abstraction

## 🔄 JWT Token Management

- **Access Token**: Valid for 1 hour (3600000 ms)
- **Refresh Token**: Valid for 7 days (604800000 ms)
- **Token Refresh**: Use refresh token to get new access token
- **Secret Key**: Configure in `app.jwt.secret` property

## 📈 Pagination & Sorting

All list endpoints support pagination and sorting:

```bash
# Pagination
?page=0&size=10

# Sorting
?sort=createdAt,desc
?sort=title,asc
```

## 🚢 Deployment

### Docker Compose (Development)
```bash
docker-compose up --build
```

### Docker Compose (Production)
```bash
docker-compose -f docker-compose.prod.yml up -d
```

### Manual Deployment
1. Build JAR: `mvn clean package`
2. Run JAR: `java -jar target/blog-point-app-0.0.1-SNAPSHOT.jar`

## 📝 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

For issues, questions, or suggestions:
1. Check the [docs](./docs/) directory
2. Review [API_DOCUMENTATION.md](./docs/API_DOCUMENTATION.md)
3. See [MANUAL_SETUP.md](./docs/MANUAL_SETUP.md) or [DOCKER_SETUP.md](./docs/DOCKER_SETUP.md)

## 🎯 Next Steps

1. **Setup**: Follow [DOCKER_SETUP.md](./docs/DOCKER_SETUP.md) or [MANUAL_SETUP.md](./docs/MANUAL_SETUP.md)
2. **Test**: Access Swagger UI at http://localhost:8080/swagger-ui.html
3. **Develop**: Explore API endpoints in [API_DOCUMENTATION.md](./docs/API_DOCUMENTATION.md)
4. **Deploy**: Configure environment and run in production

---

**Happy Blogging! 🎉**

