package com.blogpoint.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BlogPoint API",
                version = "1.0.0",
                description = """
                        A comprehensive Spring Boot backend for a secure blogging platform with:
                        - JWT-based authentication
                        - Role-based access control (ADMIN, AUTHOR, READER)
                        - Complete blog management
                        - Category and comment management
                        - User management
                        """,
                contact = @Contact(
                        name = "BlogPoint Team",
                        email = "author@blogpoint.com",
                        url = "https://github.com/yourusername/blogpoint"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "https://blogpoint-api.com",
                        description = "Production Server"
                )
        },
        security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "Enter your JWT access token (without 'Bearer ' prefix)"
)
public class OpenApiConfig {
}