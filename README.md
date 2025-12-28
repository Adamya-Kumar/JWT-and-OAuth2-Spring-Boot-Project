# JWT and OAuth2 Spring Boot Project

This is a Spring Boot application that implements secure authentication using JWT (JSON Web Tokens) with a two-token concept consisting of access tokens and refresh tokens. The project demonstrates best practices for secure authentication and authorization in modern web applications.

## Table of Contents
- [Features](#features)
- [Two-Token Concept](#two-token-concept)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)

## Features
- JWT-based authentication and authorization
- Two-token system (access token and refresh token)
- Secure password encoding using BCrypt
- Stateless authentication
- HTTP-only cookies for refresh token storage
- Spring Security integration
- JPA/Hibernate for database operations
- MySQL database support

## Two-Token Concept

This project implements a robust two-token authentication system that enhances security by separating short-lived access tokens from long-lived refresh tokens:

### Access Token
- **Purpose**: Used to access protected resources and APIs
- **Lifetime**: Short-lived (10 minutes in this implementation)
- **Storage**: Sent in the Authorization header as a Bearer token
- **Security**: Contains user information (ID, email, name) and expires quickly to minimize security risks

### Refresh Token
- **Purpose**: Used to obtain new access tokens without requiring user to re-authenticate
- **Lifetime**: Long-lived (10 days in this implementation)
- **Storage**: Stored in HTTP-only cookies to prevent XSS attacks
- **Security**: Allows users to stay logged in while access tokens expire frequently

### How It Works
1. **Login**: User logs in with credentials
2. **Token Generation**: Server generates both access and refresh tokens
3. **Response**: Access token is returned in response body, refresh token is stored in HTTP-only cookie
4. **API Access**: Client uses access token in Authorization header for API requests
5. **Token Refresh**: When access token expires, client uses refresh token to get a new access token
6. **Security**: If refresh token is compromised, it's harder to exploit due to HTTP-only cookie protection

## Technology Stack
- **Java 21**: Programming language
- **Spring Boot 4.0.1**: Application framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **MySQL**: Database management
- **JWT (jjwt)**: Token generation and validation
- **ModelMapper**: Object mapping
- **Lombok**: Code reduction
- **BCrypt**: Password encryption

## Architecture
The project follows a layered architecture:
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Handles database operations
- **Entity Layer**: Represents database models
- **DTO Layer**: Data transfer objects
- **Filter Layer**: JWT authentication filter
- **Config Layer**: Security and configuration classes

## API Endpoints

### Authentication Endpoints
- `POST /auth/signup` - User registration
- `POST /auth/login` - User login (returns access token, sets refresh token in cookie)
- `POST /auth/refresh` - Refresh access token using refresh token from cookie

### Protected Endpoints
All other endpoints require a valid access token in the Authorization header.

## Installation

1. **Clone the repository**:
```bash
git clone <repository-url>
cd JWTAndOAuth2
```

2. **Set up MySQL database**:
   - Install MySQL server
   - Create a database named `jwt_oauth2_db`
   - Update database credentials in `application.properties`

3. **Build and run the project**:
```bash
mvn clean install
mvn spring-boot:run
```

## Configuration

The application can be configured through `src/main/resources/application.properties`:

```properties
# Application name
spring.application.name=JWTAndOAuth2

# JWT Secret Token (change this in production)
secret.token=your-very-secure-secret-token-here

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_oauth2_db
spring.datasource.username=your-db-username
spring.datasource.password=your-db-password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Usage

### Registration
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```
Response will contain access token and refresh token will be set as an HTTP-only cookie.

### Accessing Protected Resources
```bash
curl -X GET http://localhost:8080/protected-endpoint \
  -H "Authorization: Bearer <access-token>"
```

### Refresh Token
```bash
curl -X POST http://localhost:8080/auth/refresh \
  -H "Cookie: refreshToken=<refresh-token>"
```

## Security Features
- Passwords are encrypted using BCrypt
- Access tokens expire after 10 minutes
- Refresh tokens are stored in HTTP-only cookies
- Stateless authentication (no session storage)
- CSRF protection disabled (for API usage)
- Authorization header parsing for JWT tokens
- Token verification using secure secret key

## Contributing
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Commit and push
6. Create a pull request

## License
This project is licensed under the MIT License.