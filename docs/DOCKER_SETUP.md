# Docker Setup Guide

This guide provides step-by-step instructions for setting up BlogPoint using Docker and Docker Compose.

## Prerequisites

- **Docker Desktop** installed and running
- **Docker Compose** (usually included with Docker Desktop)
- At least 2GB of free disk space

## System Requirements

### Windows
- Windows 10/11 with WSL2 enabled
- Docker Desktop for Windows (4.0+)

### macOS
- macOS 11.0 or newer
- Docker Desktop for Mac (Intel or Apple Silicon)

### Linux
- Docker Engine 20.0+
- Docker Compose 2.0+

## Installation

### Step 1: Install Docker

#### Windows & macOS
1. Download **Docker Desktop** from [docker.com](https://www.docker.com/products/docker-desktop)
2. Run the installer and follow the setup wizard
3. Restart your computer if prompted
4. Verify installation:
   ```bash
   docker --version
   docker-compose --version
   ```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install docker.io docker-compose
sudo systemctl start docker
sudo usermod -aG docker $USER
```

### Step 2: Verify Docker is Running

```bash
# Should show Docker info
docker ps

# Should pull and run hello-world
docker run hello-world
```

## Setup & Run BlogPoint with Docker

### Step 1: Navigate to Project Directory

```bash
cd D:\SB\blog-point-app
```

### Step 2: Build and Start Services

```bash
docker-compose up --build
```

This command will:
1. Build the BlogPoint application image
2. Pull the PostgreSQL 15 image
3. Create and start both containers
4. Initialize the database

**First run may take 2-3 minutes** as it downloads dependencies and builds the application.

### Step 3: Verify Services Are Running

In another terminal:
```bash
docker-compose ps
```

You should see:
- `blogpoint-postgres` - Running
- `blogpoint-app` - Running

### Step 4: Access the Application

Once you see both services running:

- **API Base URL**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

### Step 5: Test the Application

```bash
# Login with default credentials
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

You should receive a JWT token if successful.

## Common Docker Commands

### View Logs

```bash
# All services
docker-compose logs

# Specific service
docker-compose logs app
docker-compose logs postgres

# Follow logs (real-time)
docker-compose logs -f app
```

### Stop Services

```bash
# Stop without removing containers
docker-compose stop

# Stop and remove containers
docker-compose down

# Stop and remove everything (including volumes)
docker-compose down -v
```

### Restart Services

```bash
# Restart all services
docker-compose restart

# Restart specific service
docker-compose restart app
```

### Access Database from Host

```bash
# Connect to PostgreSQL container
docker exec -it blogpoint-postgres psql -U blogpoint -d blogpoint

# Run a query
docker exec -it blogpoint-postgres psql -U blogpoint -d blogpoint -c "SELECT * FROM users;"
```

### Access Application Container

```bash
# Open bash shell in app container
docker exec -it blogpoint-app /bin/bash

# View application logs
docker exec -it blogpoint-app tail -f logs/application.log
```

## Troubleshooting

### Error: Image not found

```
ERROR: failed to solve: openjdk:21-jdk-slim: not found
```

**Solution**: The Dockerfile should use `eclipse-temurin:21-jdk-slim`. Verify your Dockerfile has this image.

### Error: Port already in use

```
Error response from daemon: Ports are not available: exposing port TCP 0.0.0.0:8080 -> 0.0.0.0:0: listen tcp 0.0.0.0:8080: bind: Only one usage of each socket address (protocol/IP address/port) is normally permitted.
```

**Solutions**:
```bash
# Option 1: Kill process on port 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :8080
kill -9 <PID>

# Option 2: Use different port
# Edit docker-compose.yml and change:
# ports:
#   - "8081:8080"
```

### Error: Connection refused

```
Exception: Connection refused (Connection refused)
```

**Solution**:
```bash
# Check if containers are running
docker-compose ps

# Restart containers
docker-compose restart

# Check logs
docker-compose logs app
```

### Error: Database connection failed

```
Connection to postgres:5432 refused
```

**Solution**:
```bash
# Wait for PostgreSQL to start (can take 10-30 seconds)
docker-compose logs postgres

# Restart the app service
docker-compose restart app
```

### Error: Out of memory

```
ERROR: Docker daemon ran out of memory
```

**Solution**:
- Allocate more RAM in Docker Desktop settings
- Windows/macOS: Docker Desktop > Settings > Resources > Memory
- Increase to at least 4GB

### Containers keep restarting

**Solution**:
```bash
# Check detailed logs
docker-compose logs

# Stop and remove everything
docker-compose down -v

# Start fresh
docker-compose up --build
```

## Database Backup with Docker

### Backup PostgreSQL Database

```bash
docker exec blogpoint-postgres pg_dump -U blogpoint -d blogpoint > backup.sql
```

### Restore from Backup

```bash
cat backup.sql | docker exec -i blogpoint-postgres psql -U blogpoint -d blogpoint
```

## Environment Variables

Edit `docker-compose.yml` to customize:

```yaml
environment:
  SPRING_PROFILES_ACTIVE: prod
  SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/blogpoint
  SPRING_DATASOURCE_USERNAME: blogpoint
  SPRING_DATASOURCE_PASSWORD: password
  APP_JWT_SECRET: mySecretKey
  APP_JWT_EXPIRATION: 3600000
  APP_JWT_REFRESH_EXPIRATION: 604800000
```

## Performance Tuning

### Limit Resource Usage

Edit `docker-compose.yml`:

```yaml
services:
  app:
    deploy:
      resources:
        limits:
          cpus: '1'
          memory: 1G
        reservations:
          cpus: '0.5'
          memory: 512M
```

### Optimize Database

Edit `docker-compose.yml` for PostgreSQL:

```yaml
postgres:
  environment:
    POSTGRES_INITDB_ARGS: |
      -c max_connections=200
      -c shared_buffers=256MB
      -c effective_cache_size=1GB
```

## Development vs Production

### Development Setup (Current)
- Auto-reload of application on code changes
- Debug logging enabled
- Direct connection to containers

### Production Setup

For production, create `docker-compose.prod.yml`:

```yaml
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile.prod
    environment:
      SPRING_PROFILES_ACTIVE: prod
      APP_JWT_SECRET: ${JWT_SECRET}
    restart: always
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/api/auth/me"]
      interval: 30s
      timeout: 10s
      retries: 3

  postgres:
    restart: always
    volumes:
      - postgres_data:/var/lib/postgresql/data
```

Run with:
```bash
docker-compose -f docker-compose.prod.yml up -d
```

## Monitoring

### Check Container Status

```bash
docker stats
```

### View Container Resource Usage

```bash
docker-compose stats
```

### Container Health Check

```bash
docker-compose ps
```

Look for `(healthy)` status in the output.

## Cleanup

### Remove Unused Resources

```bash
# Remove unused images
docker image prune

# Remove unused containers
docker container prune

# Remove unused volumes
docker volume prune

# Remove all unused resources
docker system prune -a
```

### Complete Cleanup

```bash
# Stop and remove everything
docker-compose down -v

# Remove the built image
docker rmi blogpoint-app-app

# Verify cleanup
docker ps -a
docker images
docker volume ls
```

## Next Steps

1. Access Swagger UI: http://localhost:8080/swagger-ui.html
2. Login with default credentials (admin/admin123)
3. Create your first blog post
4. Explore API endpoints

For API documentation, see [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)
