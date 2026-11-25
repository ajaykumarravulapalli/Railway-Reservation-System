package dao;

import database.DatabaseConnection;
import model.Booking;
import model.Passenger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDAO {
    public String bookTicket(Booking booking) {
        String pnr = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        booking.setPnr(pnr);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Insert into bookings
            String sqlBooking = "INSERT INTO bookings (pnr, user_id, train_number, booking_date, status, total_amount) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ptBooking = conn.prepareStatement(sqlBooking)) {
                ptBooking.setString(1, pnr);
                ptBooking.setInt(2, booking.getUserId());
                ptBooking.setInt(3, booking.getTrainNumber());
                ptBooking.setDate(4, booking.getBookingDate());
                ptBooking.setString(5, "BOOKED");
                ptBooking.setDouble(6, booking.getTotalAmount());
                ptBooking.executeUpdate();
            }

            // 2. Insert passengers
            String sqlPassenger = "INSERT INTO passengers (pnr, name, age, gender, seat_number) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ptPassenger = conn.prepareStatement(sqlPassenger)) {
                for (Passenger p : booking.getPassengers()) {
                    ptPassenger.setString(1, pnr);
                    ptPassenger.setString(2, p.getName());
                    ptPassenger.setInt(3, p.getAge());
                    ptPassenger.setString(4, p.getGender());
                    ptPassenger.setInt(5, p.getSeatNumber());
                    ptPassenger.addBatch();
                }
                ptPassenger.executeBatch();
            }

            // 3. Update train available seats
            String sqlTrain = "UPDATE trains SET available_seats = available_seats - ? WHERE train_number = ?";
            try (PreparedStatement ptTrain = conn.prepareStatement(sqlTrain)) {
                ptTrain.setInt(1, booking.getPassengers().size());
                ptTrain.setInt(2, booking.getTrainNumber());
                ptTrain.executeUpdate();
            }

            conn.commit();
            return pnr;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setPnr(rs.getString("pnr"));
                b.setUserId(rs.getInt("user_id"));
                b.setTrainNumber(rs.getInt("train_number"));
                b.setBookingDate(rs.getDate("booking_date"));
                b.setStatus(rs.getString("status"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                bookings.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean cancelBooking(String pnr) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Get info to restore seats
            String sqlInfo = "SELECT train_number, (SELECT count(*) FROM passengers WHERE pnr = ?) as passenger_count FROM bookings WHERE pnr = ? AND status = 'BOOKED'";
            int trainNum = -1;
            int count = 0;
            try (PreparedStatement ptInfo = conn.prepareStatement(sqlInfo)) {
                ptInfo.setString(1, pnr);
                ptInfo.setString(2, pnr);
                ResultSet rs = ptInfo.executeQuery();
                if (rs.next()) {
                    trainNum = rs.getInt("train_number");
                    count = rs.getInt("passenger_count");
                }
            }

            if (trainNum == -1) return false;

            // 2. Update booking status
            String sqlUpdate = "UPDATE bookings SET status = 'CANCELLED' WHERE pnr = ?";
            try (PreparedStatement ptUpdate = conn.prepareStatement(sqlUpdate)) {
                ptUpdate.setString(1, pnr);
                ptUpdate.executeUpdate();
            }

            // 3. Restore seats
            String sqlRestore = "UPDATE trains SET available_seats = available_seats + ? WHERE train_number = ?";
            try (PreparedStatement ptRestore = conn.prepareStatement(sqlRestore)) {
                ptRestore.setInt(1, count);
                ptRestore.setInt(2, trainNum);
                ptRestore.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
             if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}
