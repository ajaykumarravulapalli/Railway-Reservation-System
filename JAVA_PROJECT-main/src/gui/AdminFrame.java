package gui;

import dao.TrainDAO;
import model.Train;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Time;
import java.util.List;

public class AdminFrame extends BaseFrame {
    private JTable trainTable;
    private DefaultTableModel tableModel;
    private TrainDAO trainDAO = new TrainDAO();

    public AdminFrame() {
        super("Admin Panel - Manage Trains");
        initUI();
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = createStyledButton("Add New Train");
        addBtn.addActionListener(e -> showTrainDialog(null));
        topPanel.add(addBtn);
        add(topPanel, BorderLayout.NORTH);

        String[] cols = {"Num", "Name", "Source", "Dest", "Total", "Avail", "Dep", "Arr", "Price"};
        tableModel = new DefaultTableModel(cols, 0);
        trainTable = new JTable(tableModel);
        add(new JScrollPane(trainTable), BorderLayout.CENTER);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Train> trains = trainDAO.getAllTrains();
        for (Train t : trains) {
            tableModel.addRow(new Object[]{
                t.getTrainNumber(), t.getTrainName(), t.getSource(), t.getDestination(),
                t.getTotalSeats(), t.getAvailableSeats(), t.getDepartureTime(), t.getArrivalTime(), t.getTicketPrice()
            });
        }
    }

    private void showTrainDialog(Train train) {
        JDialog dialog = new JDialog(this, "Train Details", true);
        dialog.setLayout(new GridLayout(10, 2, 10, 10));
        dialog.getContentPane().setBackground(PRIMARY_COLOR);
        
        JTextField numF = createStyledTextField();
        JTextField nameF = createStyledTextField();
        JTextField srcF = createStyledTextField();
        JTextField dstF = createStyledTextField();
        JTextField totalF = createStyledTextField();
        JTextField depF = createStyledTextField(); depF.setText("10:00:00");
        JTextField arrF = createStyledTextField(); arrF.setText("22:00:00");
        JTextField prcF = createStyledTextField();

        dialog.add(new JLabel("Train Number:")); dialog.add(numF);
        dialog.add(new JLabel("Name:")); dialog.add(nameF);
        dialog.add(new JLabel("Source:")); dialog.add(srcF);
        dialog.add(new JLabel("Destination:")); dialog.add(dstF);
        dialog.add(new JLabel("Total Seats:")); dialog.add(totalF);
        dialog.add(new JLabel("Departure (HH:mm:ss):")); dialog.add(depF);
        dialog.add(new JLabel("Arrival (HH:mm:ss):")); dialog.add(arrF);
        dialog.add(new JLabel("Price:")); dialog.add(prcF);

        JButton saveBtn = createStyledButton("Save");
        saveBtn.addActionListener(e -> {
            try {
                Train t = new Train();
                t.setTrainNumber(Integer.parseInt(numF.getText()));
                t.setTrainName(nameF.getText());
                t.setSource(srcF.getText());
                t.setDestination(dstF.getText());
                t.setTotalSeats(Integer.parseInt(totalF.getText()));
                t.setAvailableSeats(t.getTotalSeats());
                t.setDepartureTime(Time.valueOf(depF.getText()));
                t.setArrivalTime(Time.valueOf(arrF.getText()));
                t.setTicketPrice(Double.parseDouble(prcF.getText()));

                if (trainDAO.addTrain(t)) {
                    JOptionPane.showMessageDialog(dialog, "Train Added");
                    refreshTable();
                    dialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        dialog.add(saveBtn);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
