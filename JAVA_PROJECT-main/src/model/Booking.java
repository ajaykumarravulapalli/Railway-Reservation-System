package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String pnr;
    private int userId;
    private int trainNumber;
    private Date bookingDate;
    private String status; // BOOKED, CANCELLED
    private double totalAmount;
    private List<Passenger> passengers = new ArrayList<>();

    public Booking() {}

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getTrainNumber() { return trainNumber; }
    public void setTrainNumber(int trainNumber) { this.trainNumber = trainNumber; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
}
