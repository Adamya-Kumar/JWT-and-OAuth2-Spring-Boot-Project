# JWT and OAuth2 Spring Boot Project

This is a Spring Boot application that implements secure authentication using JWT (JSON Web Tokens) with a two-token concept consisting of access tokens and refresh tokens. The project demonstrates best practices for secure authentication, authorization, and **active session management**.

## Table of Contents

* [Features](https://www.google.com/search?q=%23features)
* [Two-Token Concept](https://www.google.com/search?q=%23two-token-concept)
* [Session Management System](https://www.google.com/search?q=%23session-management-system)
* [Technology Stack](https://www.google.com/search?q=%23technology-stack)
* [Architecture](https://www.google.com/search?q=%23architecture)
* [API Endpoints](https://www.google.com/search?q=%23api-endpoints)
* [Installation](https://www.google.com/search?q=%23installation)

## Features

* JWT-based authentication and authorization.
* Two-token system (access token and refresh token).
* **Session Management**: Limits the number of active concurrent logins per user.
* Secure password encoding using BCrypt.
* Stateless authentication with HTTP-only cookies for refresh tokens.

---

## Session Management System

To enhance security and control resource usage, this project includes a custom **Session Management System**. This ensures that a single user account cannot have an unlimited number of active "logged-in" devices or sessions simultaneously.

### The SESSION_LIMIT Logic

In this implementation, we set a `SESSION_LIMIT = 2`. This means a user can be logged in on a maximum of two devices/browsers at once.

1. **Check Existing Sessions**: Every time a user logs in, the system checks the `Session` table for existing entries linked to that `user_id`.
2. **Limit Enforcement**: If the count reaches the limit (2), the system identifies the **Least Recently Used (LRU)** session based on the `least_used_at` timestamp.
3. **Automatic Eviction**: The oldest session is deleted from the database, effectively logging out the oldest device, and the new session is saved.

### Implementation Preview

```java
public void generateNewSession(User user, String refreshToken) {
    List<Session> userSessions = sessionRepository.findByUser(user);
    
    if (userSessions.size() == SESSION_LIMIT) {
        // Sort sessions by last usage to find the oldest one
        userSessions.sort(Comparator.comparing(Session::getLeastUsedAt));
        Session oldestSession = userSessions.getFirst();
        sessionRepository.delete(oldoldestSession);
    }
    
    Session newSession = Session.builder()
            .refreshToken(refreshToken)
            .user(user)
            .leastUsedAt(LocalDateTime.now())
            .build();
            
    sessionRepository.save(newSession);
}

```
---
## Project Screenshots

![Screenshot 1](src/main/resources/static/Screenshot%202025-12-28%20233832.jpg)<br>
![Screenshot 2](src/main/resources/static/Screenshot%202025-12-28%20233854.jpg)<br>
![Screenshot 3](src/main/resources/static/Screenshot%202025-12-28%20234003.jpg)<br>
![Screenshot 4](src/main/resources/static/Screenshot%202025-12-28%20234022.jpg)<br>

---

## Two-Token Concept

### Access Token

* **Purpose**: Short-lived token (10 mins) used for authorizing API requests.
* **Storage**: Sent in the `Authorization: Bearer` header.

### Refresh Token

* **Purpose**: Long-lived token (10 days) used to generate new access tokens.
* **Storage**: Stored in a database `Session` table and sent to the client via **HTTP-only cookies** to prevent XSS attacks.

---

## Technology Stack

* **Java 24**: Programming language.
* **Spring Boot 4.0.1**: Application framework.
* **Spring Security**: Authentication and authorization.
* **Spring Data JPA & MySQL**: Persistence and session storage.
* **Lombok**: Boilerplate reduction (using `@RequiredArgsConstructor`, `@Builder`, etc.).

---

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/auth/signup` | Register a new user. |
| `POST` | `/auth/login` | Authenticate and create a new session (enforces limit). |
| `POST` | `/auth/refresh` | Use refresh token cookie to get a new access token. |

---

## Configuration

Update your `application.properties` to manage session constraints:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_oauth2_db
spring.datasource.username=root
spring.datasource.password=yourpassword

# JPA
spring.jpa.hibernate.ddl-auto=update

```

---

**Would you like me to add a section on how to test the session eviction using Postman?**