# Clinic Management System

## 1. Project Overview

The Clinic Management System is a web-based application developed to support the management of clinic activities. It provides a backend REST API built with Spring Boot and a frontend interface developed using React.

The system helps manage important clinic information, including doctors, patients, offices, appointments, and prescriptions.

## 2. Technologies Used

**Backend**
- Java
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

**Frontend**
- React
- JavaScript
- HTML
- CSS
- Vite
- Axios
- React Router

**Tools**
- IntelliJ IDEA
- Visual Studio Code
- Git and GitHub
- PostgreSQL / pgAdmin

## 3. Main Features

- Doctor management
- Patient management
- Office management
- Appointment management
- Prescription management
- Specialization management
- Dashboard information
- REST API integration between the frontend and backend
- Office search and delete functionality in the frontend

## 4. Project Structure

```text
clinic-management-system-27328/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── mvnw.cmd
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   └── App.jsx
│   ├── package.json
│   └── vite.config.js
│
└── README.md
```

## 5. Database Configuration

The project uses PostgreSQL.

Create a database named:

```sql
CREATE DATABASE clinic_db;
```

Configure the database credentials using environment variables.

In Windows PowerShell:

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

Replace `your_password` with your PostgreSQL password.

## 6. How to Run the Project

### Backend Setup

Open PowerShell and navigate to the backend folder:

```powershell
cd backend
```

Set the database credentials:

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

Start the Spring Boot application:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.main-class=auca.ac.clinicsystem.ClinicsystemApplication"
```

The backend runs at:

`http://localhost:8082`

### Frontend Setup

Open another terminal and navigate to the frontend folder:

```powershell
cd frontend
```

Install the dependencies:

```powershell
npm install
```

Start the React development server:

```powershell
npm run dev
```

Open the frontend in your browser:

`http://localhost:5173`

The Office page can be accessed at:

`http://localhost:5173/office`

## 7. API Integration

The React frontend communicates with the Spring Boot backend through REST API endpoints.

The Office interface uses:

- `GET /api/offices` – Retrieve office records.
- `DELETE /api/offices/{id}` – Delete an office record.

## 8. Author

**Name:** Igiraneza Irene  
**Student ID:** 27328  
**Institution:** Adventist University of Central Africa (AUCA)

## 9. Repository

GitHub: https://github.com/ni-irene/clinic-management-system-27328
