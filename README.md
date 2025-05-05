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
- Dockerized for consistent environment setup..
- Integration with RabbitMQ for asynchronous communication.

---
### ⚙️ Prerequisites

- ✅ Install **Docker Desktop**: [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
- ✅ Make sure Docker Daemon is running.

## ▶️ How to Run

### Clone the Repository

```bash
git clone https://github.com/gustavohmz/usuarios-service.git
cd usuarios-service
```

### Run with Docker

```bash
docker-compose up --build
```

Access the API: [http://localhost:8080/api/clients](http://localhost:8080/api/clients)

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

### 🧱 Dockerfile

```dockerfile
# Usamos una imagen base con Java 17
FROM openjdk:17-jdk-slim

# Directorio dentro del contenedor
WORKDIR /app

# Copia el jar compilado al contenedor
COPY target/usuarios-service-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto
EXPOSE 8080

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 📦 docker-compose.yml

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16
    container_name: users-db
    restart: always
    environment:
      POSTGRES_DB: users_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 4535
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq-users
    restart: always
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest

  app:
    build: .
    container_name: usuarios-service
    depends_on:
      - postgres
      - rabbitmq
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/users_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 4535

      SPRING_RABBITMQ_HOST: rabbitmq
      SPRING_RABBITMQ_PORT: 5672
      SPRING_RABBITMQ_USERNAME: guest
      SPRING_RABBITMQ_PASSWORD: guest
    restart: on-failure

volumes:
  postgres_data:
```

### 📄 application.yml

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/users_db}
    username: ${SPRING_DATASOURCE_USERNAME:postgres}
    password: ${SPRING_DATASOURCE_PASSWORD:4535}
    driver-class-name: org.postgresql.Driver

  rabbitmq:
    host: ${SPRING_RABBITMQ_HOST:rabbitmq}
    port: ${SPRING_RABBITMQ_PORT:5672}
    username: ${SPRING_RABBITMQ_USERNAME:guest}
    password: ${SPRING_RABBITMQ_PASSWORD:guest}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: ${SERVER_PORT:8080}
```

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

- **Name**: Gustavo Hernandez
- **GitHub**: [https://github.com/gustavohmz](https://github.com/gustavohmz)
