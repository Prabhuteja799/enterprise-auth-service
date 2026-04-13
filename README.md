# enterprise-auth-service

A production-style authentication and authorization service built with Spring Boot 4 and Spring Security. Implements JWT-based stateless auth, role-based access control (RBAC), BCrypt password hashing, and a proper security filter chain — structured for real-world use, not tutorials.

---

## What it covers

**Authentication**
- User registration with BCrypt-hashed passwords
- Login endpoint that issues signed JWT tokens
- Stateless sessions — no server-side session storage

**Authorization**
- Role-based access control (RBAC) with database-backed roles
- Protected endpoints enforced by Spring Security filter chain
- Method-level security (`@PreAuthorize`)

**Security architecture**
- Custom JWT validation filter wired into the Spring Security chain
- Centralized exception handling for auth errors (401/403)
- Roles stored in the database — not hardcoded enums — for runtime flexibility

---

## Tech stack

| Component | Tech |
|---|---|
| Framework | Spring Boot 4, Spring Security |
| Auth | JWT (`jjwt` 0.12.7) |
| Persistence | Spring Data JPA, PostgreSQL / MySQL |
| Password hashing | BCrypt |
| Build | Maven |
| Language | Java 17 |

---

## API flow

### Register

```http
POST /auth/register
Content-Type: application/json

{
  "username": "alice",
  "password": "securepassword",
  "role": "USER"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "alice",
  "password": "securepassword"
}
```

Returns: `{ "token": "eyJ..." }`

### Access protected endpoints

```http
GET /api/protected-resource
Authorization: Bearer eyJ...
```

Spring Security validates the token before the request reaches the controller.

---

## Setup

### 1. Clone

```bash
git clone https://github.com/Prabhuteja799/enterprise-auth-service.git
cd enterprise-auth-service
```

### 2. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

app.jwt.secret=your-256-bit-secret-key
app.jwt.expiration-ms=86400000
```

### 3. Run

```bash
mvn spring-boot:run
```

---

## Architecture

```
Request
  │
  ▼
JwtAuthFilter          ← validates token, sets SecurityContext
  │
  ▼
Spring Security chain  ← checks roles, enforces access rules
  │
  ▼
Controller → Service → Repository → Database
```

---

## Design decisions

**JWT over sessions** — stateless, horizontally scalable, no shared session store needed for microservices.

**BCrypt** — adaptive cost factor, built-in salting. Passwords are never stored or logged in any recoverable form.

**Database-backed roles** — allows role changes without redeployment. Enum roles break the moment you need to add a new permission at runtime.

---

## Future enhancements

- Refresh tokens with revocation support
- OAuth2 / social login
- Rate limiting on auth endpoints
- Audit logging (login events, failed attempts)
- API Gateway integration
