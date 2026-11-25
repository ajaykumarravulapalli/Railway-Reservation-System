package gui;

import dao.BookingDAO;
import model.Booking;
import model.Passenger;
import util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BookingFrame extends BaseFrame {
    private int trainNumber;
    private double pricePerTicket;
    private JPanel passengerListPanel;
    private List<PassengerInputRow> rows = new ArrayList<>();

    public BookingFrame(int trainNumber, double pricePerTicket) {
        super("Book Ticket - Train " + trainNumber);
        this.trainNumber = trainNumber;
        this.pricePerTicket = pricePerTicket;
        initUI();
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        passengerListPanel = new JPanel();
        passengerListPanel.setLayout(new BoxLayout(passengerListPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(passengerListPanel), BorderLayout.CENTER);

        addPassengerRow(); // Initial row

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addPassengerBtn = new JButton("+ Add Passenger");
        addPassengerBtn.addActionListener(e -> addPassengerRow());
        botPanel.add(addPassengerBtn);

        JButton confirmBtn = createStyledButton("Confirm Booking");
        confirmBtn.addActionListener(e -> handleConfirm());
        botPanel.add(confirmBtn);
        
        add(botPanel, BorderLayout.SOUTH);
    }

    private void addPassengerRow() {
        PassengerInputRow row = new PassengerInputRow();
        rows.add(row);
        passengerListPanel.add(row);
        passengerListPanel.revalidate();
    }

    private void handleConfirm() {
        Booking b = new Booking();
        b.setUserId(SessionManager.getCurrentUser().getId());
        b.setTrainNumber(trainNumber);
        b.setBookingDate(new Date(System.currentTimeMillis()));
        b.setTotalAmount(rows.size() * pricePerTicket);

        for (PassengerInputRow row : rows) {
            String name = row.nameField.getText();
            String ageStr = row.ageField.getText();
            String gender = (String) row.genderCombo.getSelectedItem();
            
            if (name.isEmpty() || ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete all passenger details");
                return;
            }
            
            Passenger p = new Passenger(name, Integer.parseInt(ageStr), gender, 0); // Seat handled by DB/Random
            b.getPassengers().add(p);
        }

        String pnr = new BookingDAO().bookTicket(b);
        if (pnr != null) {
            JOptionPane.showMessageDialog(this, "Booking Successful! PNR: " + pnr);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Booking Failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class PassengerInputRow extends JPanel {
        JTextField nameField, ageField;
        JComboBox<String> genderCombo;

        PassengerInputRow() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBackground(SECONDARY_COLOR);
            nameField = createStyledTextField(); nameField.setPreferredSize(new Dimension(150, 25));
            ageField = createStyledTextField(); ageField.setPreferredSize(new Dimension(50, 25));
            genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
            
            add(new JLabel("Name:")); add(nameField);
            add(new JLabel("Age:")); add(ageField);
            add(new JLabel("Gender:")); add(genderCombo);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        }
    }
}
