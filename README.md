# 💈 Barber App REST API

The Barber App is a RESTful API for managing barbershops, built with Spring Boot. It provides secure user authentication, role-based access control, and endpoints for managing barbers, schedules, and appointments.
## 🌐 Features

    JWT-based authentication for secure login and registration

    Role-based access control:

        Customer – browse barbers and book appointments

        Barber – manage schedules and client appointments

        Admin – manage users and appointments

    Full CRUD operations for users, barbers, schedules, and appointments

    RESTful endpoints for integration with web or mobile apps

    Unit and integration tests with Spring Boot and Mockito

## 🛠 Technologies Used

    Java 17

    Spring Boot 3 (Spring Security, Spring Web, Spring Data JPA)

    Hibernate & JPA

    H2 / PostgreSQL (configurable)

    JWT for authentication

    JUnit 5 & Mockito for testing

    Docker for containerized deployment

## 🚀 Getting Started

    Clone the repository:

git clone https://github.com/HeorhiiKortunov/BarberApp.git

Navigate to the project folder:

cd BarberApp

Start the database with Docker:

docker-compose up -d

Run the application using Maven:

mvn spring-boot:run

Access the API:

    http://localhost:8080/api

## 🔑 Example Endpoints

    Authentication

        POST /api/auth/register – Register a new user

        POST /api/auth/login – Login and receive a JWT token

    Users

        GET /api/users/me – Get current user profile

    Barbers & Appointments

        GET /api/barbers – List all barbers

        POST /api/appointments – Book an appointment

## 📄 License

This project is open-source and available under the MIT License.

## 👤 Author

Heorhii Kortunov
📧 heorhiikortunov@gmail.com
🔗 [GitHub Profile](https://github.com/HeorhiiKortunov)
