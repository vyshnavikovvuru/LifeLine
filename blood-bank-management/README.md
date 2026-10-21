# Blood Bank Management System

A starter full-stack Blood Bank Management System using **Spring Boot + Spring Data JPA + MySQL** for the backend and **Angular** for the frontend.

## Features
- Register blood donors
- View registered donors
- Search donors by blood group
- Delete donor records
- Submit blood requests
- View blood requests

## Requirements
- Java 21+
- Maven 3.9+
- Node.js 20.19+ (or a version supported by your Angular CLI)
- Angular CLI (`npm install -g @angular/cli`)
- MySQL 8+

## 1. Create the database
Open MySQL and run:
```sql
CREATE DATABASE blood_bank_db;
```

## 2. Run the Spring Boot backend
Edit `backend/src/main/resources/application.properties` and set your own MySQL username/password.
From the `backend` directory:
```bash
mvn spring-boot:run
```
Backend base URL: `http://localhost:8080/api`

## 3. Run the Angular frontend
From the `frontend` directory:
```bash
npm install
ng serve
```
Open `http://localhost:4200`.

## API endpoints
- `GET /api/donors`
- `GET /api/donors?bloodGroup=A%2B`
- `POST /api/donors`
- `DELETE /api/donors/{id}`
- `GET /api/requests`
- `POST /api/requests`

## Notes
- This is a learning/demo project, not a production medical system.
- Add authentication, authorization, validation, audit logs, and privacy/security controls before real deployment.
- The frontend source is included; `node_modules` is intentionally not included in the ZIP.
