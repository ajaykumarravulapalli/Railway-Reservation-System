package gui;

import dao.BookingDAO;
import model.Booking;
import util.SessionManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryFrame extends BaseFrame {
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private BookingDAO bookingDAO = new BookingDAO();

    public HistoryFrame() {
        super("My Booking History");
        initUI();
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        String[] cols = {"PNR", "Train", "Date", "Status", "Amount"};
        tableModel = new DefaultTableModel(cols, 0);
        bookingTable = new JTable(tableModel);
        add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingDAO.getBookingsByUserId(SessionManager.getCurrentUser().getId());
        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                b.getPnr(), b.getTrainNumber(), b.getBookingDate(), b.getStatus(), b.getTotalAmount()
            });
        }
    }
}
