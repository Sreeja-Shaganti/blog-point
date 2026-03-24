# BlogPoint Application - Production Ready Dockerfile
# Multi-stage build for optimal image size

# Stage 1: Build stage
FROM eclipse-temurin:21-alpine AS builder

WORKDIR /build

# Install Maven
RUN apk add --no-cache maven

# Copy POM
COPY pom.xml .

# Download dependencies (cached layer)
RUN mvn dependency:resolve

# Copy source code
COPY src ./src

# Build application JAR
RUN mvn clean package -DskipTests -q

# Stage 2: Runtime stage (minimal image)
FROM eclipse-temurin:21-alpine

# Install curl for health checks
RUN apk add --no-cache curl

WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /build/target/blog-point-app-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=50s --retries=3 \
    CMD curl -f http://localhost:8080/api/blogs || exit 1

# Run application
CMD ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]
