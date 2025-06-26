School Management API
This is a RESTful API for managing students, courses, enrollments, and grades in a school system.

Features
Student Management: Create, update, and fetch students.

Course Enrollment: Enroll students in courses.

Grade Management: Assign grades to students.

Database: H2 in-memory database.

API Docs: Swagger UI for easy testing.

Requirements
Java 17

Maven

Getting Started

Clone the repo

git clone https://github.com/rajamuneeeb/school-management-api.git
cd school-management-api

Build and run the project using Maven

mvn clean install
mvn spring-boot:run

Access the H2 Database
URL: http://localhost:8080/h2-console/

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: 4321

Access Swagger UI
URL: http://localhost:8080/swagger-ui/index.html#/


Example Requests
Create Student
{
  "name": "test",
  "email": "test@gmail.com"
}


This version specifies Java 17 as the required version for running the project. Let me know if you need further adjustments!


