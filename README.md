# 🏦 Bank Users Microservice

This is a microservice for managing users and clients in a banking system, built with Spring Boot and PostgreSQL. It follows clean code principles and includes validation, exception handling, and automated tests.

---

## 🧱 Architecture

This microservice follows a layered architecture with clear separation of concerns:

- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic.
- **Repository Layer**: Communicates with the database using Spring Data JPA.
- **DTOs & Mappers**: Data Transfer Objects are used to decouple internal models from external input. A custom `ClientMapper` is used to map DTOs to entities.
- **Exception Handling**: Centralized with `@RestControllerAdvice`.

---

## ✅ Features

- Manage `Person` and `Client` entities with one-to-one relation.
- CRUD operations for clients.
- Field validation using annotations like `@NotBlank`, `@Min`, etc.
- Custom exception handling (e.g., 404 for not found).
- Unit tests with Mockito.
- Integration tests using H2.
- Dockerized for consistent environment setup.

---

## 🔍 API Endpoints

| Method | Endpoint             | Description          |
|--------|----------------------|----------------------|
| POST   | `/api/clients`       | Create a new client  |
| GET    | `/api/clients`       | Get all clients      |
| GET    | `/api/clients/{id}`  | Get client by ID     |
| PUT    | `/api/clients/{id}`  | Update client        |
| DELETE | `/api/clients/{id}`  | Delete client        |

---

## 📋 Example Request (POST)

```json
{
  "name": "Alice",
  "gender": "Female",
  "age": 30,
  "identification": "123456789",
  "address": "123 Main Street",
  "phone": "555-1234",
  "clientId": "alice001",
  "password": "securepass",
  "status": true
}
```

---

## 🧪 Testing

### Unit Tests

- Located in `ClientServiceImplTest.java`
- Uses `@Mock` and `@InjectMocks` with Mockito
- Verifies business logic in isolation

### Integration Tests

- Located in `ClientControllerTest.java`
- Uses Spring Boot test context and H2 database
- Validates full API flow (HTTP → DB)

Run tests:

```bash
mvn test
```

---

## 🚨 Exception Handling

Custom error messages and HTTP status codes are handled by `GlobalExceptionHandler`:

- 404 Not Found → for missing resources
- 400 Bad Request → for validation errors
- 500 Internal Server Error → fallback for unexpected exceptions

---

## 🐳 Dockerized Setup

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/usuarios-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml

```yaml
version: '3.8'

services:
  db:
    image: postgres:16
    container_name: bank-db
    environment:
      POSTGRES_DB: bank
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 4535
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

  app:
    build: .
    container_name: bank-users-service
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/bank
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 4535

volumes:
  postgres-data:
```

---

## ▶️ How to Run (Docker)

1. Make sure Docker and Docker Compose are installed.
2. Package the application:

```bash
mvn clean package -DskipTests
```

3. Build and start containers:

```bash
docker-compose up --build
```

4. Access the application at [http://localhost:8080/api/clients](http://localhost:8080/api/clients)

---

## 📂 Project Structure

```
src
├── main
│   ├── java/com.bank.users
│   │   ├── controller
│   │   ├── service / impl
│   │   ├── repository
│   │   ├── model
│   │   ├── dto
│   │   ├── mapper
│   │   └── exception
│   └── resources
│       ├── application.yml
└── test
    ├── service.impl
```

---

## 👤 Author

- **Name**: Gustavo hernandez
- **GitHub**: [https://github.com/gustavohmz](https://github.com/your-profile)

---
