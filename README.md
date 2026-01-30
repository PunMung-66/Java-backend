# Java Backend (Educational Project) ✅

**Short description**

This is an educational Spring Boot backend that demonstrates: authentication (JWT), basic user registration/login, file upload, and a protected product endpoint. It's intentionally simple and suitable for learning Spring Security, token-based auth, and a clean package structure.

---

## 🔧 Prerequisites

- Java 17+ (or compatible with your Maven settings) (For me I use Java 25)
- PostgreSQL (or change datasource in `src/main/resources/application.properties`)
- Git (optional)

---

## ▶️ How to run

On Windows (from project root):

```powershell
# Run with Maven Wrapper (Windows)
.m\vnw.cmd spring-boot:run

# or build and run jar
.m\vnw.cmd package
java -jar target/*.jar
```

On macOS / Linux:

```bash
./mvnw spring-boot:run
# or
./mvnw package
java -jar target/*.jar
```

Run tests:

```bash
# Windows
.mvnw.cmd test
# macOS / Linux
./mvnw test
```

> Default server port: `8080` (Spring Boot default).

---

## 📁 Project structure

Top-level packages and purpose:

- `com.punnawat.backend.api.controllers` — REST controllers (entry points)
- `com.punnawat.backend.api.dtos` — request/response DTOs
- `com.punnawat.backend.business` — business logic
- `com.punnawat.backend.config` — security configuration, filters
- `com.punnawat.backend.services` — services (e.g., `TokenService`)
- `com.punnawat.backend.repositorys` — data repositories
- `com.punnawat.backend.entity` — JPA entities
- `com.punnawat.backend.expections` — custom exceptions & handler
- `src/main/resources/application.properties` — configuration (DB, token settings)

Example file tree (relevant files):

```
src/main/java/com/punnawat/backend/
  api/controllers/MyController.java
  api/dtos/request/{LoginDTORequest, RegisterDTORequest}
  api/dtos/response/{LoginDTOResponse, RegisterDTOResponse, TokenResponse}
  config/SecurityConfig.java
  config/Token/TokenFilter.java
  services/TokenService.java
```

---

## 🔗 API endpoints (summary)

Base URL: `http://localhost:8080/api`

- GET `/api` — health/test endpoint (returns a simple string)
- POST `/api/user/register` — register a user (public)

  - Request (JSON):
    ```json
    {
      "email": "user@example.com",
      "name": "User Name",
      "password": "password123"
    }
    ```
  - Response: `ApiResponse<RegisterDTOResponse>`
- POST `/api/user/login` — login (public)

  - Request (JSON):
    ```json
    {
      "email": "user@example.com",
      "password": "password123"
    }
    ```
  - Response: `ApiResponse<LoginDTOResponse>` which includes `TokenResponse` with `token` and `expiresAt`.
- GET `/api/product/{id}` — get product info (requires role `USER` or `ADMIN`)
- POST `/api` — upload a profile picture (multipart file field name: `file`) — permission depends on security rules

---

## 🔒 Security details

- Authentication: JWT tokens (HMAC256). Token is created by `TokenService` using properties in `application.properties`:

```properties
app.token.secret=YourSuperSecretPasswordThatIsVeryLongAndSecure123!
app.token.issuer=auth0
app.token.expiration-time-ms=3600000
```

- Token verification is implemented in `TokenFilter` which reads the `Authorization` header and sets the Spring Security context.
- `SecurityConfig` rules (key points):

  - `/api/user/register` and `/api/user/login` -> `permitAll()` (public)
  - `/api/product/**` -> `hasAnyRole("USER", "ADMIN")`
  - `/api/**` -> `hasRole("ADMIN")` (other `/api` endpoints restricted to ADMIN unless explicitly permitted above)
- In-memory admin user exists in `SecurityConfig.userDetailsService`:

  - username: `admin`
  - password: `admin123` (encoded)
  - roles: `ADMIN`, `USER`
- Use token in requests via header:

```
Authorization: Bearer <token>
```

Example curl for a protected endpoint (after login):

```bash
# login -> assume you get token
TOKEN="ey..."

curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/product/1
```

---

## ⚙️ Configuration & environment

Adjust database and token settings in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=pun
spring.datasource.password=syspass
```

If you don't want to use PostgreSQL for local dev, you can switch to an in-memory DB (H2) by updating dependencies and properties.

---

## 🧪 Tests & linting

- Run unit/integration tests with `./mvnw test` (or `mvnw.cmd test` on Windows).

---

## 💡 Tips & troubleshooting

- If you get an `401` or `403`, verify:

  - Token present and not expired
  - Token claims contain correct `role` (TokenService sets `role: USER` by default for generated tokens)
  - Endpoint permission vs. token role
- Increase multipart limits in `application.properties` if upload fails:

```properties
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=10MB
```

---

## 📚 Learning pointers

- Review `TokenFilter` to understand how claims are extracted and `SecurityContext` is set.
- Inspect `SecurityConfig` to learn how to map endpoints to roles.

---

If you'd like, I can also:

- add example Postman collection / OpenAPI (Swagger) file ✅
- add a Docker Compose config for DB and app ✅

---

Made for learning 👩‍💻👨‍💻 — feel free to open an issue or ask for clarifications.
