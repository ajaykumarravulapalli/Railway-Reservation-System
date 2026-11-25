# Railway Reservation and Ticket Management System

## Overview
A high-performance desktop application built using Core Java and Swing, designed to manage railway operations including train scheduling, ticket booking, and user management.

## Tech Stack
- **Language**: Java 17+
- **GUI Framework**: Java Swing & AWT
- **Database**: MySQL 8.0+
- **Persistence**: JDBC (Java Database Connectivity)
- **Build Tool**: Gradle

## Features
1. **Authentication**: Secure login and registration for Users and Admins.
2. **Train Search**: Real-time searching of trains based on source and destination.
3. **Booking System**: Multi-passenger booking with unique PNR generation.
4. **Cancellation**: Flexible ticket cancellation with seat restoration.
5. **Admin Panel**: Management interface for adding and updating train data.
6. **Persistence**: All data is stored in a relational MySQL database.

## System Architecture
The project follows the **DAO (Data Access Object) Pattern** and **MVC (Model-View-Controller)** principles:
- `model/`: Data structures representing system entities.
- `dao/`: Data access logic isolating database operations.
- `gui/`: User interface components and event handling.
- `database/`: Database connection management.
- `util/`: Helper classes for session management.

## Diagrams

### Entity Relationship (ER) Diagram
- **Users**: (id, username, password, role)
- **Trains**: (train_number, train_name, source, destination, total_seats, available_seats, dep_time, arr_time, ticket_price)
- **Bookings**: (pnr, user_id, train_number, booking_date, status, total_amount)
- **Passengers**: (id, pnr, name, age, gender, seat_number)

**Relationships:**
- User : Booking (1:N)
- Train : Booking (1:N)
- Booking : Passenger (1:N)

### Use Case Diagram
- **User**: Register, Login, Search Train, Book Ticket, View History, Cancel Ticket.
- **Admin**: All User actions + Add Train, Update Train, Delete Train, View All Reservations.

### Class Diagram
- Main -> LoginFrame
- LoginFrame -> UserDAO -> DatabaseConnection
- DashboardFrame -> SessionManager
- SearchTrainFrame -> TrainDAO
- BookingFrame -> BookingDAO

## Setup Instructions
1. Import `schema.sql` into your MySQL Server.
2. Update `src/database/DatabaseConnection.java` with your MySQL credentials.
3. Install MySQL Connector J (included in Gradle dependencies).
4. Run the `Main.java` file.
