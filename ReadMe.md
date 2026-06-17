# ShareMyRecipe 🍳

A Spring Boot REST API application for sharing and managing recipes. This platform allows chefs to share their recipes with users, featuring authentication, authorization, file uploads to AWS S3, and comprehensive API documentation.

---

## 📋 Table of Contents
- [About the Project](#about-the-project)
- [Features & Libraries Used](#features--libraries-used)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Local Setup](#local-setup)
  - [Running the Application](#running-the-application)
- [Production Deployment](#production-deployment)
- [API Documentation](#api-documentation)
- [Concepts & Learning Outcomes](#concepts--learning-outcomes)
- [Testing](#testing)
- [CI/CD Pipeline](#cicd-pipeline)

---

## 🎯 About the Project

**ShareMyRecipe** is a recipe-sharing platform built with Spring Boot that enables:
- **Chefs** to register, create, and manage their recipes
- **Users** to browse and discover recipes
- **Admins** to manage the platform

The application demonstrates enterprise-level Spring Boot development with security, database management, cloud storage integration, and automated deployment.

---

## ✨ Features & Libraries Used

### Core Spring Boot Starters
1. **spring-boot-starter-web** - RESTful API development
2. **spring-boot-starter-data-jpa** - Database operations with JPA/Hibernate
3. **spring-boot-starter-security** - Security framework
4. **spring-boot-starter-validation** - Request validation
5. **spring-boot-starter-actuator** - Application monitoring and health checks
6. **spring-boot-starter-mail** - Email service using JavaMailSender interface

### Security & Authentication
- **JWT (JSON Web Tokens)** - Token-based authentication
- **Role-based Authorization** - CHEF, USER, and ADMIN roles
- **Password Encryption** - BCrypt password encoding

### Database
- **MySQL** - Production database
- **H2** - In-memory database for testing
- **JPA/Hibernate** - ORM framework

### Cloud Integration
- **AWS S3** - File storage for recipe images
- **AWS EC2** - Application hosting
- **AWS RDS** - Managed MySQL database

### Email Service
- **JavaMailSender** - Spring's email abstraction interface
- **SMTP Configuration** - Gmail SMTP integration
- **Email Verification System** - Token-based email verification for user/chef registration
- **Verification Tokens** - Secure, time-limited tokens for account activation

### Documentation & Testing
- **Springdoc OpenAPI (Swagger)** - Interactive API documentation
- **JUnit 5** - Unit testing framework
- **Jacoco** - Code coverage reports

### DevOps & CI/CD
- **Docker** - Containerization
- **GitHub Actions** - Continuous Integration/Continuous Deployment
- **Gradle** - Build automation

### Additional Features
- **Global Exception Handling** - Centralized error management
- **JPA Auditing** - Automatic timestamp tracking
- **Multipart File Upload** - Recipe image uploads
- **Email Verification** - User/Chef account activation

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.7**
- **MySQL 8.x**
- **Gradle**
- **Docker**
- **AWS (EC2, RDS, S3)**

---

## 🚀 Getting Started

### Prerequisites

Before running this application, ensure you have:

- **JDK 17** or higher installed
- **MySQL 8.x** running locally
- **Gradle** (or use the included Gradle wrapper)
- **Git** for version control
- **AWS Account** (for S3 file uploads)

### Local Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ShareMyRecipe
   ```

2. **Configure MySQL Database**
   
   Create a MySQL database:
   ```sql
   CREATE DATABASE sharemyrecipe;
   ```

3. **Configure Application Properties**
   
   Update `src/main/resources/application-dev.properties` with your local configuration:
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/sharemyrecipe
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   
   # JPA/Hibernate
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   
   # JWT Configuration
   jwt.secret=your_secret_key
   jwt.expiration=86400000
   
   # AWS S3 Configuration
   aws.s3.bucket.name=your_bucket_name
   aws.access.key.id=your_access_key
   aws.secret.access.key=your_secret_key
   aws.region=ap-south-1
   
   # Email Configuration
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_app_password
   ```

4. **Install Dependencies**
   ```bash
   gradlew clean build
   ```

### Running the Application

#### Option 1: Using Gradle
```bash
gradlew bootRun
```

#### Option 2: Using Java
```bash
gradlew build
java -jar build/libs/ShareMyRecipe-0.0.1-SNAPSHOT.jar
```

#### Option 3: Using Docker
```bash
docker build -t sharemyrecipe .
docker run -p 8080:8080 sharemyrecipe
```

The application will start on **http://localhost:8080**

---

## 🌐 Production Deployment

The application is deployed on **AWS EC2** and accessible at:

**Base URL:** `http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com`

### API Endpoints

#### Health Check
```
GET http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/
```

#### User Registration
```
POST http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/api/v1/users/auth/register
```

#### Chef Registration
```
POST http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/api/v1/chefs/auth/register
```

#### Create Recipe (Requires Authentication)
```
POST http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/api/v1/chef/{chefId}/recipes
```

### Actuator Endpoints
Monitor application health and metrics:
```
http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/actuator
http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/actuator/health
```

---

## 📚 API Documentation

Interactive API documentation is available via **Swagger UI**:

### Swagger UI (Interactive Documentation)
```
http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/docs
```
or
```
http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/swagger-ui.html
```

### OpenAPI JSON
```
http://ec2-43-205-98-249.ap-south-1.compute.amazonaws.com/v3/api-docs
```

**Features of Swagger Documentation:**
- Interactive API testing
- Request/Response examples
- Authentication testing with JWT tokens
- Model schemas and validation rules

---

## 🎓 Concepts & Learning Outcomes

This project demonstrates expertise in the following areas:

### 1. Authentication & Authorization
- **Spring Security** configuration and customization
- **JWT-based authentication** (stateless session management)
- **Role-based access control** (RBAC) with @PreAuthorize annotations
- Custom authentication filters and security chains

### 2. Filters & Configuration (Java Beans)
- `PasswordEncoder` - BCrypt password hashing
- `AuthenticationManager` - Authentication handling
- `UserDetailsService` - Custom user loading logic
- `loadUserByUsername()` - User lookup implementation
- JWT filters for token validation

### 3. Dependency Injection
- **Constructor-based injection** (recommended approach)
- **Field-based injection** with @Autowired
- Understanding IoC (Inversion of Control) container
- Bean lifecycle management

### 4. JPA Annotations & Database Design
- `@Entity` - Entity mapping
- `@Table` - Table customization
- `@Inheritance` - Inheritance strategies (JOINED)
- `@EnableJpaAuditing` - Automatic auditing
- `@EntityListeners` - AuditingEntityListener for timestamps
- `@CreatedDate` & `@LastModifiedDate` - Audit fields

### 5. Hibernate Object Management
- **Persistence context** and entity states
- **Persist** - Making entities managed
- **Detach** - Removing entities from context
- **Merge** - Reattaching detached entities
- Understanding first-level cache

### 6. Relationships & Transactions
- `@OneToOne` - One-to-one relationships
- `@OneToMany` - One-to-many relationships
- `@ManyToOne` - Many-to-one relationships
- `@Transactional` - Transaction management
- **CASCADE operations** - Propagating operations
- Lazy vs Eager fetching strategies

### 7. File Upload & AWS S3 Integration
- `MultipartFile` handling
- AWS **S3Client** Java SDK
- File validation and storage
- Generating pre-signed URLs
- Environment-specific configuration

### 8. Request Handling
- `@RequestBody` - JSON payload mapping
- `@RequestParam` - Query parameters
- `@RequestPart` - Multipart form data
- `@PathVariable` - URL path variables
- Content negotiation and media types

### 9. Stream API & Functional Programming
- Using Java Streams for data processing
- Lambda expressions
- Method references
- Collectors and reduction operations

### 10. Exception Handling
- **Global exception handler** with @RestControllerAdvice
- Custom exception classes
- Proper HTTP status codes
- Consistent error response format

### 11. API Documentation
- **Springdoc OpenAPI** integration
- Swagger annotations
- API versioning strategies
- Interactive documentation

### 12. DevOps & Cloud Deployment
- **Dockerfile** creation (multi-stage builds)
- **GitHub Actions** workflows
- **AWS EC2** deployment and management
- **AWS RDS** database configuration
- Environment profiles (dev, prod)
- Continuous Integration/Continuous Deployment

---

## 🧪 Testing

### Run All Tests
```bash
gradlew test
```

### Generate Code Coverage Report
```bash
gradlew jacocoTestReport
```

Coverage report will be available at: `build/reports/jacoco/test/html/index.html`

### Test Results
View test results at: `build/reports/tests/test/index.html`

---

## 🔄 CI/CD Pipeline

The project uses **GitHub Actions** for automated CI/CD:

1. **Build Stage**: Compile and run tests
2. **Test Stage**: Execute unit tests and generate coverage
3. **Package Stage**: Create Docker image
4. **Deploy Stage**: Deploy to AWS EC2

---

## 📝 Project Structure

```
ShareMyRecipe/
├── src/
│   ├── main/
│   │   ├── java/com/airtribe/ShareMyRecipe/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── exception/       # Custom exceptions
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── security/        # Security configuration
│   │   │   ├── service/         # Business logic
│   │   │   └── util/            # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/                    # Unit tests
├── build.gradle                 # Gradle build file
├── Dockerfile                   # Docker configuration
├── docker-compose.yml          # Docker Compose setup
└── ReadMe.md                   # Project documentation
```

---

## 👨‍💻 Author

Built with ❤️ as a learning project to master Spring Boot and enterprise Java development.

---

## 📄 License

This project is for educational purposes.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

---

## 📞 Support

For support and queries, please check the API documentation at `/docs` endpoint.
