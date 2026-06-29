# CleanUp - Cleaning Service App

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Railway](https://img.shields.io/badge/Deployed%20on-Railway-purple?style=for-the-badge&logo=railway)](https://cleanup.up.railway.app/)

##### CleanUp is a Spring Boot-based web application for managing cleaning services, including user authentication, service booking, worker management, payments, and a customer rating system.

🌐 **Live Demo:** [https://cleanup.up.railway.app/](https://cleanup.up.railway.app/)

---

## Features

- User authentication and authorization (register, login, session management)
- Service booking = customers can browse and book available cleaning services
- Worker management = admins can manage cleaning staff and their assignments
- Payment handling = integrated payment flow for each booking
- Customer rating system = customers can rate workers after service completion
- Admin dashboard for managing services, bookings, and users

---

## Tech

CleanUp is built with the following technologies:

- [Spring Boot 4.0.6](https://spring.io/projects/spring-boot) - Main web application framework (MVC, JPA, Actuator)
- [Java 17](https://www.oracle.com/java/) - Core programming language
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - ORM and database interaction
- [PostgreSQL](https://www.postgresql.org/) - Production relational database
- [H2 Database](https://www.h2database.com/) - In-memory database for testing
- [Lombok](https://projectlombok.org/) - Reduces boilerplate code with annotations
- [spring-dotenv](https://github.com/paulschwarz/spring-dotenv) - `.env` file support for environment variables
- [Maven](https://maven.apache.org/) - Build and dependency management tool
- [Railway](https://railway.app/) - Cloud deployment platform

---

## Initialization

1. Clone the repository:
```bash
   git clone https://github.com/deviprajna/Cleaning-Service-App.git
   cd Cleaning-Service-App
```
2. Create a `.env` file in the root directory and fill in your environment variables:
```env
   DB_URL=jdbc:postgresql://localhost:5432/cleaningdb
   DB_USERNAME=your_db_user
   DB_PASSWORD=your_db_password
```
3. Make sure PostgreSQL is running and the database is created.
4. Run the application using Maven Wrapper:
```bash
   ./mvnw spring-boot:run
```
5. Access the application at `http://localhost:9999`.

