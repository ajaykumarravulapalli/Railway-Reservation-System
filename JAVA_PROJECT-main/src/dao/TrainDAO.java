package dao;

import database.DatabaseConnection;
import model.Train;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {
    public boolean addTrain(Train train) {
        String sql = "INSERT INTO trains (train_number, train_name, source, destination, total_seats, available_seats, departure_time, arrival_time, ticket_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, train.getTrainNumber());
            pstmt.setString(2, train.getTrainName());
            pstmt.setString(3, train.getSource());
            pstmt.setString(4, train.getDestination());
            pstmt.setInt(5, train.getTotalSeats());
            pstmt.setInt(6, train.getAvailableSeats());
            pstmt.setTime(7, train.getDepartureTime());
            pstmt.setTime(8, train.getArrivalTime());
            pstmt.setDouble(9, train.getTicketPrice());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                trains.add(extractTrainFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }

    public List<Train> searchTrains(String source, String destination) {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains WHERE source LIKE ? AND destination LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + source + "%");
            pstmt.setString(2, "%" + destination + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                trains.add(extractTrainFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }

    public boolean updateAvailableSeats(int trainNumber, int seatsToReduce) {
        String sql = "UPDATE trains SET available_seats = available_seats - ? WHERE train_number = ? AND available_seats >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, seatsToReduce);
            pstmt.setInt(2, trainNumber);
            pstmt.setInt(3, seatsToReduce);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Train extractTrainFromResultSet(ResultSet rs) throws SQLException {
        Train train = new Train();
        train.setTrainNumber(rs.getInt("train_number"));
        train.setTrainName(rs.getString("train_name"));
        train.setSource(rs.getString("source"));
        train.setDestination(rs.getString("destination"));
        train.setTotalSeats(rs.getInt("total_seats"));
        train.setAvailableSeats(rs.getInt("available_seats"));
        train.setDepartureTime(rs.getTime("departure_time"));
        train.setArrivalTime(rs.getTime("arrival_time"));
        train.setTicketPrice(rs.getDouble("ticket_price"));
        return train;
    }
}
