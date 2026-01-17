"# Learning-Management-System-SPB" 
"# E-Learning Backend - Spring Boot LMS

A comprehensive Learning Management System (LMS) backend built with Spring Boot, providing RESTful APIs for online education platforms.

## 🚀 Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security + JWT Authentication
- **Cloud Storage**: AWS S3 (for video storage)
- **Documentation**: Swagger/OpenAPI (Springdoc)
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven

## 📋 Features

### Core Functionality
- 🔐 **Authentication & Authorization** - JWT-based authentication with role-based access control (STUDENT, INSTRUCTOR, ADMIN)
- 📚 **Course Management** - Create, update, delete courses with instructor assignment
- 📖 **Lesson Management** - Video lessons with progress tracking
- ✅ **Quiz System** - Multiple choice, single choice, and true/false questions with auto-grading
- 📊 **Progress Tracking** - Track student progress through courses and lessons
- 🎓 **Enrollment System** - Student course enrollment management
- ☁️ **Video Streaming** - AWS S3 integration with pre-signed URLs for secure video delivery

### API Features
- RESTful API design
- Input validation with Jakarta Validation
- Exception handling with detailed error responses
- CORS configuration for frontend integration
- Actuator endpoints for monitoring

## 🛠️ Setup & Installation

### Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher
- AWS Account (optional, for video storage)

### Database Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE LMS;
```

2. Update database credentials in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/LMS
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### AWS S3 Configuration (Optional)

Set environment variables or update `application.properties`:
```properties
aws.s3.bucket-name=your-bucket-name
aws.s3.region=your-region
aws.s3.access-key=your-access-key
aws.s3.secret-key=your-secret-key
```

### Run Application

```bash
cd Vanca
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Swagger UI
Access interactive API documentation at: `http://localhost:8080/swagger-ui.html`

### Main Endpoints

#### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

#### Courses
- `GET /api/courses` - Get all published courses
- `GET /api/courses/{id}` - Get course by ID
- `POST /api/courses` - Create new course (INSTRUCTOR/ADMIN)
- `PUT /api/courses/{id}` - Update course (INSTRUCTOR/ADMIN)
- `DELETE /api/courses/{id}` - Delete course (INSTRUCTOR/ADMIN)

#### Lessons
- `GET /api/lessons/course/{courseId}` - Get lessons by course
- `GET /api/lessons/{id}` - Get lesson by ID
- `GET /api/lessons/{id}/video-url` - Get video streaming URL
- `POST /api/lessons/course/{courseId}` - Create lesson (INSTRUCTOR/ADMIN)
- `PUT /api/lessons/{id}` - Update lesson (INSTRUCTOR/ADMIN)
- `DELETE /api/lessons/{id}` - Delete lesson (INSTRUCTOR/ADMIN)

#### Quizzes
- `GET /api/quizzes/{id}` - Get quiz details
- `POST /api/quizzes/{id}/attempt` - Start quiz attempt (STUDENT)
- `POST /api/quizzes/attempts/{attemptId}/submit` - Submit quiz answers (STUDENT)
- `GET /api/quizzes/{id}/results` - Get quiz results

#### Progress & Enrollment
- `GET /api/progress/courses/{courseId}` - Get course progress
- `PUT /api/progress/lessons/{lessonId}` - Update lesson progress
- `POST /api/enrollments/course/{courseId}` - Enroll in course (STUDENT)
- `GET /api/enrollments` - Get user enrollments

## 🏗️ Project Structure

```
Vanca/src/main/java/com/example/demo/
├── config/              # Security and application configuration
├── controller/          # REST API controllers
├── dto/                 # Data Transfer Objects
│   ├── request/        # Request DTOs
│   └── response/       # Response DTOs
├── exception/          # Custom exceptions and handlers
├── model/              # JPA entities
├── repository/         # Spring Data JPA repositories
├── security/           # JWT and security components
└── service/            # Business logic layer
```

## 🔒 Security

- **JWT Authentication**: All protected endpoints require valid JWT token in Authorization header
- **Role-Based Access Control**: Different permissions for STUDENT, INSTRUCTOR, and ADMIN roles
- **Password Encryption**: BCrypt hashing for secure password storage
- **CORS**: Configured for frontend integration

### Authentication Flow
1. Register/Login to get JWT token
2. Include token in Authorization header: `Bearer <token>`
3. Access protected endpoints based on user role

## 🗄️ Database Schema

### Main Entities
- **User** - Users with roles (STUDENT, INSTRUCTOR, ADMIN)
- **Course** - Courses with instructor reference
- **Lesson** - Video lessons belonging to courses
- **Quiz** - Quizzes associated with lessons
- **Question** - Quiz questions with multiple options
- **QuizAttempt** - Student quiz attempts with scoring
- **Progress** - Student progress tracking per lesson
- **Enrollment** - Student course enrollments

## 📝 Environment Profiles

- **Default**: Uses `application.properties`
- **Dev**: `application-dev.properties` (for development)
- **Prod**: `application-prod.properties` (for production)

Activate profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🧪 Testing

Run tests:
```bash
mvn test
```

## 📊 Monitoring

Actuator endpoints available at:
- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Built with ❤️ for online education

---

For questions or issues, please open an issue in the repository.
" 
