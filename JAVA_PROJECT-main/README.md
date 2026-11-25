# 🚆 Railway Reservation and Ticket Management System

A desktop-based Railway Reservation and Ticket Management System developed using **Core Java**, **Java Swing**, **JDBC**, and **MySQL**. The application provides a complete solution for managing train reservations, passenger details, ticket bookings, cancellations, and administrative operations through a user-friendly graphical interface.

---

## 📌 Project Overview

The Railway Reservation System is designed to automate the process of railway ticket booking and management. It allows users to search trains, reserve seats, generate PNR numbers, cancel tickets, and view booking history. Administrators can manage train schedules, monitor reservations, and generate reports.

This project follows **Object-Oriented Programming (OOP)** principles and demonstrates the use of **Java Swing GUI**, **JDBC connectivity**, **MySQL database integration**, and **modular software architecture**.

---

## ✨ Features

### 👤 User Features
- User Registration
- User Login & Logout
- Search Available Trains
- View Train Details
- Book Tickets
- Generate Unique PNR Numbers
- Cancel Reservations
- View Booking History

### 🚆 Train Management
- Add New Trains
- Update Train Information
- Delete Trains
- View Available Trains
- Manage Seat Availability

### 🎫 Reservation Management
- Ticket Booking
- Seat Allocation
- Passenger Information Management
- Booking Confirmation

### 🛠 Admin Features
- Manage Train Records
- View Passenger Details
- Monitor Reservations
- Generate Booking Reports
- Manage System Data

---

## 🛠 Technologies Used

| Technology | Purpose |
|------------|---------|
| Core Java | Application Logic |
| Java Swing | Desktop GUI |
| JDBC | Database Connectivity |
| MySQL | Database Management |
| OOP Concepts | Software Design |
| Collections Framework | Data Handling |
| Exception Handling | Error Management |

---

## 📂 Project Structure

```text
RailwayReservationSystem/
│
├── src/
│
├── model/
│   ├── User.java
│   ├── Train.java
│   ├── Passenger.java
│   └── Booking.java
│
├── dao/
│   ├── UserDAO.java
│   ├── TrainDAO.java
│   ├── BookingDAO.java
│
├── database/
│   └── DBConnection.java
│
├── gui/
│   ├── LoginFrame.java
│   ├── RegisterFrame.java
│   ├── DashboardFrame.java
│   ├── SearchTrainFrame.java
│   ├── BookingFrame.java
│   ├── CancellationFrame.java
│   └── AdminFrame.java
│
├── service/
│   └── ReservationService.java
│
└── Main.java
```

---

## 🗄 Database Schema

### Users Table

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(100),
    role VARCHAR(20)
);
```

### Trains Table

```sql
CREATE TABLE trains (
    train_no INT PRIMARY KEY,
    train_name VARCHAR(100),
    source_station VARCHAR(100),
    destination_station VARCHAR(100),
    total_seats INT,
    available_seats INT
);
```

### Passengers Table

```sql
CREATE TABLE passengers (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(20),
    phone VARCHAR(15)
);
```

### Bookings Table

```sql
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    pnr VARCHAR(20),
    passenger_id INT,
    train_no INT,
    seat_number VARCHAR(20),
    booking_status VARCHAR(20)
);
```

---

## ⚙ Installation & Setup

### Prerequisites

- Java JDK 8 or higher
- MySQL Server
- MySQL Workbench (Optional)
- IntelliJ IDEA / Eclipse / NetBeans

### Steps

#### 1. Clone Repository

```bash
git clone https://github.com/yourusername/RailwayReservationSystem.git
```

#### 2. Create Database

```sql
CREATE DATABASE railway_reservation;
```

#### 3. Execute Database Schema

Run all SQL scripts to create required tables.

#### 4. Configure Database Connection

Update database credentials in:

```java
DBConnection.java
```

Example:

```java
String url = "jdbc:mysql://localhost:3306/railway_reservation";
String user = "root";
String password = "password";
```

#### 5. Run Application

Execute:

```java
Main.java
```

---

## 📸 Screens

### Login Screen
- User Authentication
- Registration Access

### Dashboard
- Train Search
- Booking
- Cancellation
- Booking History

### Train Search
- Search by Source
- Destination
- Travel Date

### Booking Window
- Passenger Details
- Seat Reservation
- PNR Generation

### Admin Dashboard
- Train Management
- Reservation Reports
- Passenger Records

---

## 🎯 Learning Outcomes

This project demonstrates:

- Object-Oriented Programming
- Java Swing GUI Development
- JDBC Database Connectivity
- SQL Database Design
- CRUD Operations
- Exception Handling
- Software Architecture Design
- Real-World System Development

---

## 🔮 Future Enhancements

- Waiting List Management
- RAC System
- Online Payment Gateway
- Ticket PDF Generation
- Email Notifications
- SMS Alerts
- Advanced Analytics Dashboard
- Multi-User Support
- Role-Based Access Control

---

## 🎓 Academic Relevance

This project is suitable for:

- Java Major Project
- Final Year Project
- Mini Project
- Database Management System Project
- Object-Oriented Programming Project

---

## 👨‍💻 Author

Mahathi chinta
