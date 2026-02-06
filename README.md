

## Spring Security JWT Authentication System

A production-style authentication and authorization service built using **Spring Boot** and **Spring Security**.

This project demonstrates how modern backend systems implement secure, stateless authentication using JWT while enforcing role-based access control.

Designed to reflect real-world backend architecture rather than tutorial-level code.

---

## Why This Project Exists

Most security tutorials stop at basic login.

Real systems require:

* Stateless authentication
* Strong password encryption
* Role-based authorization
* Secure filter chains
* Proper exception handling
* Token validation
* Scalable architecture

This repository focuses on those production patterns.

---

## Tech Stack

* Java 8+
* Spring Boot
* Spring Security
* JWT (JSON Web Tokens)
* Hibernate / JPA
* PostgreSQL / MySQL
* Maven
* Lombok

Optional upgrades you can add later:

* Redis for token blacklisting
* Refresh tokens
* OAuth2
* API Gateway integration


---

## Core Features

### Authentication

* Secure user registration
* BCrypt password hashing
* Login with JWT generation
* Stateless session design

### Authorization

* Role-Based Access Control (RBAC)
* Protected endpoints
* Method-level security

### Security Architecture

* Custom authentication filter
* JWT validation filter
* Centralized exception handling
* Secure password storage

### Backend Best Practices

* DTO pattern
* Layered architecture
* Clean separation of concerns
* Database-backed roles
* Immutable response models

---

## Architecture

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Security Layer:

```
JWT Filter
   ↓
Spring Security Context
   ↓
Authorization Checks
```

No session storage. Fully stateless.

---

## Example API Flow

### Register User

```
POST /auth/register
```

Creates a user with encrypted password.

---

### Login

```
POST /auth/login
```

Returns:

```
JWT Token
```

---

### Access Protected Endpoint

Include header:

```
Authorization: Bearer <token>
```

Spring Security validates the token before allowing access.

---

## Security Decisions Explained

### Why JWT?

* Eliminates server-side session storage
* Horizontally scalable
* Ideal for microservices

---

### Why BCrypt?

Passwords must **never** be stored in plain text.

BCrypt automatically handles salting and adaptive hashing.

---

### Why Roles in Database Instead of Enum?

Allows:

* dynamic permission changes
* admin-controlled roles
* enterprise flexibility

Enums are better only when roles never change.

---

## Getting Started

### Clone

```bash
git clone https://github.com/prabhuteja799/enterprise-auth-service.git
```

---

### Configure Database

Update:

```
application.properties
```

Example:

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

---

### Run Application

```
mvn spring-boot:run
```

---

## Future Enhancements


* Refresh tokens
* Token revocation
* OAuth2 / Social login
* Multi-tenant authorization
* API Gateway integration
* Rate limiting
* Audit logging

Shows architectural thinking.

---

## What This Project Demonstrates

* Strong backend fundamentals
* Security-first API design
* Production mindset
* Knowledge of stateless systems
* Understanding of authorization models

---

## Author

**Prabhuteja Chintala**

Software Engineer focused on backend systems, distributed architecture, and secure application design.

