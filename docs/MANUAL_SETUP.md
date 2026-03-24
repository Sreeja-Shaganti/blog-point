# Setup Guide - Manual Installation with PostgreSQL

This guide provides step-by-step instructions to set up BlogPoint locally without Docker, using PostgreSQL directly.

## Prerequisites

- **Java 21** or higher
- **PostgreSQL 12** or higher
- **Maven 3.6+**
- **Git** (optional)

## Step 1: Install PostgreSQL

### Windows
1. Download from [PostgreSQL Official Website](https://www.postgresql.org/download/windows/)
2. Run the installer and follow the setup wizard
3. Note down the superuser password you set during installation
4. Select PostgreSQL version 12 or higher
5. Complete the installation

### macOS
```bash
brew install postgresql
brew services start postgresql
```

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

## Step 2: Create Database and User

Open PostgreSQL command prompt or client:

```bash
# Windows: Open "SQL Shell (psql)" from Start Menu
# macOS/Linux: Open terminal and run
psql -U postgres
```

Run the following SQL commands:

```sql
-- Create database
CREATE DATABASE blogpoint;

-- Create user
CREATE USER blogpoint WITH PASSWORD 'password';

-- Grant privileges
ALTER ROLE blogpoint SET client_encoding TO 'utf8';
ALTER ROLE blogpoint SET default_transaction_isolation TO 'read committed';
ALTER ROLE blogpoint SET default_transaction_deferrable TO on;
ALTER ROLE blogpoint SET timezone TO 'UTC';

-- Grant all privileges
GRANT ALL PRIVILEGES ON DATABASE blogpoint TO blogpoint;

-- Connect to the database
\c blogpoint

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO blogpoint;
GRANT ALL ON ALL TABLES IN SCHEMA public TO blogpoint;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO blogpoint;

-- Exit psql
\q
```

## Step 3: Clone or Extract Project

```bash
cd D:\SB\blog-point-app
```

Or if cloning:
```bash
git clone <repository-url>
cd blog-point-app
```

## Step 4: Create Tables and Insert Initial Data

### Option A: Automatic (Spring Boot Schema Generation)

1. Open `src/main/resources/application.properties`
2. Verify these settings:
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.url=jdbc:postgresql://localhost:5432/blogpoint
   spring.datasource.username=blogpoint
   spring.datasource.password=password
   ```

3. Build and run the application (Step 5 below). Spring will automatically create tables.

### Option B: Manual SQL Script

If you prefer to create tables manually before running the application:

```bash
# Navigate to the project directory
cd D:\SB\blog-point-app

# Connect to PostgreSQL and run the schema script
psql -U blogpoint -d blogpoint -f src/main/resources/schema.sql
psql -U blogpoint -d blogpoint -f src/main/resources/data.sql
```

See the SQL scripts in the `docs` directory:
- `schema.sql` - Creates all database tables
- `data.sql` - Inserts initial data

## Step 5: Build and Run Application

```bash
# Navigate to project directory
cd D:\SB\blog-point-app

# Build the project (downloads dependencies, compiles code)
mvn clean install

# Run the application
mvn spring-boot:run
```

Or using the Maven wrapper:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## Step 6: Verify Application

### Check Application is Running
```bash
curl http://localhost:8080/swagger-ui.html
```

### Check Database Connection
Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

Try the login endpoint:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

If you get a valid JWT token, the setup is complete!

## Configuration

Edit `src/main/resources/application.properties` for custom settings:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/blogpoint
spring.datasource.username=blogpoint
spring.datasource.password=password

# JPA
spring.jpa.hibernate.ddl-auto=update  # or 'validate' after first run
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT
app.jwt.secret=mySecretKey
app.jwt.expiration=3600000
app.jwt.refresh-expiration=604800000

# Logging
logging.level.com.blogpoint=DEBUG
```

## Default Credentials

After database setup with `data.sql`:

- **Username**: admin
- **Password**: admin123
- **Role**: ADMIN

## Troubleshooting

### 1. Connection Refused Error
```
Exception: Connection to localhost:5432 refused
```
**Solution**: 
- Ensure PostgreSQL is running
- Windows: Check Services (Services.msc)
- macOS: `brew services list`
- Linux: `sudo systemctl status postgresql`

### 2. Authentication Failed
```
FATAL: password authentication failed for user "blogpoint"
```
**Solution**:
- Check username and password in application.properties
- Verify user was created correctly
- Reset password: `ALTER USER blogpoint WITH PASSWORD 'newpassword';`

### 3. Database Does Not Exist
```
Exception: database "blogpoint" does not exist
```
**Solution**:
- Create database: `CREATE DATABASE blogpoint;`
- Verify with: `\l` in psql

### 4. Port Already in Use
```
Address already in use: bind
```
**Solution**:
- Change port in application.properties: `server.port=8081`
- Or kill existing process on port 8080

### 5. Maven Build Fails
```
[ERROR] BUILD FAILURE
```
**Solution**:
- Clear cache: `mvn clean`
- Update dependencies: `mvn update-snapshots`
- Check Java version: `java -version` (should be 21+)

## Performance Tuning

For production, update PostgreSQL connection pool:

```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
```

## Backup Database

```bash
# Backup
pg_dump -U blogpoint -d blogpoint > backup.sql

# Restore
psql -U blogpoint -d blogpoint < backup.sql
```

## Next Steps

1. Access API Docs: http://localhost:8080/swagger-ui.html
2. Try the authentication endpoints
3. Create your first blog post
4. Explore the API endpoints

For API documentation, see [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)
