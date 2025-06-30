# Mentoring App Backend

A REST API backend for managing mentor-mentee relationships built with Spring Boot.

## Features

- User management (mentors and mentees)
- Mentorship assignment and management
- RESTful API with OpenAPI/Swagger documentation
- Input validation and error handling
- Database migrations with Flyway

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.3**
- **PostgreSQL using Docker** - Database
- **Spring Data JPA** - ORM
- **Flyway** - Database migrations
- **MapStruct** - Entity-DTO mapping
- **OpenAPI 3** - API documentation
- **Maven** - Build tool

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL
- Maven

### Database Setup

1. Create a PostgreSQL database
2. Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mentoring_app
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### API Endpoints

#### User Management (`/api/v1/users`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/users` | Get all users |
| POST | `/api/v1/users` | Create a new user |
| GET | `/api/v1/users/mentors` | Get all mentors |
| GET | `/api/v1/users/mentees` | Get all mentees |
| GET | `/api/v1/users/mentors/{id}/mentees` | Get mentees for a specific mentor |
| GET | `/api/v1/users/mentees/{id}/mentor` | Get mentor for a specific mentee |

#### Mentorship Management (`/api/v1/mentorships`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/mentorships` | Get all mentorships |
| POST | `/api/v1/mentorships` | Assign mentor to mentee |
| DELETE | `/api/v1/mentorships/{id}` | Remove a mentorship |

## Project Structure

```
src/main/java/com/mentoringapp/
├── controller/          # REST controllers
├── service/            # Business logic
├── repository/         # Data access layer
├── domain/             # Entity classes
├── dto/                # Data Transfer Objects
│   ├── request/        # Request DTOs
│   └── response/       # Response DTOs
├── mapper/             # MapStruct mappers
├── exceptions/         # Custom exceptions
└── config/             # Configuration classes
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.
