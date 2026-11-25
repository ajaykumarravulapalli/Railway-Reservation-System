-- Database Schema for Railway Reservation System
CREATE DATABASE IF NOT EXISTS railway_reservation;
USE railway_reservation;

-- Users table for authentication
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trains table
CREATE TABLE IF NOT EXISTS trains (
    train_number INT PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    ticket_price DECIMAL(10, 2) NOT NULL
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    pnr VARCHAR(10) PRIMARY KEY,
    user_id INT,
    train_number INT,
    booking_date DATE DEFAULT (CURRENT_DATE),
    status ENUM('BOOKED', 'CANCELLED') DEFAULT 'BOOKED',
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (train_number) REFERENCES trains(train_number)
);

-- Passengers table
CREATE TABLE IF NOT EXISTS passengers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pnr VARCHAR(10),
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    seat_number INT NOT NULL,
    FOREIGN KEY (pnr) REFERENCES bookings(pnr) ON DELETE CASCADE
);

-- Insert sample admin
INSERT IGNORE INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
INSERT IGNORE INTO users (username, password, role) VALUES ('user', 'user123', 'USER');

-- Insert sample trains
INSERT IGNORE INTO trains (train_number, train_name, source, destination, total_seats, available_seats, departure_time, arrival_time, ticket_price) 
VALUES (12301, 'Rajdhani Express', 'New Delhi', 'Howrah', 500, 500, '16:50:00', '10:30:00', 2500.00);
INSERT IGNORE INTO trains (train_number, train_name, source, destination, total_seats, available_seats, departure_time, arrival_time, ticket_price) 
VALUES (12002, 'Shatabdi Express', 'New Delhi', 'Bhopal', 800, 800, '06:00:00', '14:00:00', 1200.00);
