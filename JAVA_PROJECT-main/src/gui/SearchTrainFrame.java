package gui;

import dao.TrainDAO;
import model.Train;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchTrainFrame extends BaseFrame {
    private JTextField sourceField, destField;
    private JTable trainTable;
    private DefaultTableModel tableModel;
    private TrainDAO trainDAO = new TrainDAO();

    public SearchTrainFrame() {
        super("Search Trains");
        initUI();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(PRIMARY_COLOR);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topPanel.setBackground(new Color(15, 17, 22));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel srcLabel = new JLabel("SOURCE");
        srcLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        srcLabel.setForeground(MUTED_TEXT);
        topPanel.add(srcLabel);
        
        sourceField = createStyledTextField(); sourceField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(sourceField);

        JLabel dstLabel = new JLabel("DESTINATION");
        dstLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        dstLabel.setForeground(MUTED_TEXT);
        topPanel.add(dstLabel);

        destField = createStyledTextField(); destField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(destField);

        JButton searchBtn = createStyledButton("SEARCH ROUTE");
        searchBtn.addActionListener(e -> refreshTable());
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        String[] cols = {"Number", "Name", "Source", "Destination", "Available", "Dep", "Arr", "Price"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        trainTable = new JTable(tableModel);
        trainTable.setRowHeight(40);
        trainTable.setIntercellSpacing(new Dimension(10, 0));
        trainTable.setShowGrid(false);
        trainTable.setSelectionBackground(new Color(99, 102, 241, 40));
        trainTable.setSelectionForeground(Color.WHITE);
        trainTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JScrollPane scrollPane = new JScrollPane(trainTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.getViewport().setBackground(PRIMARY_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        botPanel.setBackground(PRIMARY_COLOR);
        botPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        JButton bookBtn = createStyledButton("RESERVE SELECTED SEAT");
        bookBtn.addActionListener(this::handleBooking);
        botPanel.add(bookBtn);
        add(botPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Train> trains = trainDAO.searchTrains(sourceField.getText(), destField.getText());
        for (Train t : trains) {
            tableModel.addRow(new Object[]{
                t.getTrainNumber(), t.getTrainName(), t.getSource(), t.getDestination(),
                t.getAvailableSeats(), t.getDepartureTime(), t.getArrivalTime(), t.getTicketPrice()
            });
        }
    }

    private void handleBooking(java.awt.event.ActionEvent e) {
        int row = trainTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a train first");
            return;
        }

        int trainNum = (int) tableModel.getValueAt(row, 0);
        int available = (int) tableModel.getValueAt(row, 4);
        double price = (double) tableModel.getValueAt(row, 7);

        if (available <= 0) {
            JOptionPane.showMessageDialog(this, "No seats available");
            return;
        }

        new BookingFrame(trainNum, price).setVisible(true);
    }
}
