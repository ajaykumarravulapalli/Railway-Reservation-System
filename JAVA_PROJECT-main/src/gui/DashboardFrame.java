package gui;

import util.SessionManager;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends BaseFrame {
    public DashboardFrame() {
        super("Railway Reservation - Dashboard");
        initUI();
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Sidebar / Menu
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(SECONDARY_COLOR);
        menu.setPreferredSize(new Dimension(260, 0));
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));

        // Sidebar Logo Section
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 30));
        logoPanel.setBackground(SECONDARY_COLOR);
        
        JPanel rIcon = new JPanel(new GridBagLayout());
        rIcon.setPreferredSize(new Dimension(45, 45));
        rIcon.setBackground(ACCENT_COLOR);
        JLabel rChar = new JLabel("R");
        rChar.setForeground(Color.WHITE);
        rChar.setFont(new Font("Segoe UI", Font.BOLD, 24));
        rIcon.add(rChar);
        logoPanel.add(rIcon);

        JLabel titleLabel = new JLabel("<html><b>RAILWAY</b><br/><font color='#6366F1' size='3'>Pro Systems</font></html>");
        logoPanel.add(titleLabel);
        menu.add(logoPanel);

        menu.add(Box.createVerticalStrut(20));
        JLabel labelMain = new JLabel("  MAIN CONSOLE");
        labelMain.setFont(new Font("Segoe UI", Font.BOLD, 10));
        labelMain.setForeground(MUTED_TEXT);
        menu.add(labelMain);
        menu.add(Box.createVerticalStrut(10));

        String[] labels = {"Search Trains", "Ticket Reservations", "Cancellations"};
        for (String label : labels) {
            JButton btn = new JButton(label);
            btn.setMaximumSize(new Dimension(240, 45));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(SECONDARY_COLOR);
            btn.setForeground(TEXT_COLOR);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            if (label.equals("Search Trains")) btn.addActionListener(e -> new SearchTrainFrame().setVisible(true));
            if (label.equals("Ticket Reservations")) btn.addActionListener(e -> new HistoryFrame().setVisible(true));
            if (label.equals("Cancellations")) btn.addActionListener(e -> {
                String pnr = JOptionPane.showInputDialog(this, "Enter PNR to cancel:");
                if (pnr != null && !pnr.isEmpty()) {
                    new dao.BookingDAO().cancelBooking(pnr);
                    JOptionPane.showMessageDialog(this, "Cancellation Processed");
                }
            });

            menu.add(btn);
            menu.add(Box.createVerticalStrut(5));
        }

        if (SessionManager.isAdmin()) {
            menu.add(Box.createVerticalStrut(20));
            JLabel labelAdmin = new JLabel("  ADMINISTRATION");
            labelAdmin.setFont(new Font("Segoe UI", Font.BOLD, 10));
            labelAdmin.setForeground(MUTED_TEXT);
            menu.add(labelAdmin);
            menu.add(Box.createVerticalStrut(10));

            JButton adminBtn = createStyledButton("Manage Trains");
            adminBtn.setMaximumSize(new Dimension(240, 45));
            adminBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            adminBtn.addActionListener(e -> new AdminFrame().setVisible(true));
            menu.add(adminBtn);
        }

        // Sidebar Footer Status
        menu.add(Box.createVerticalGlue());
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(SURFACE_COLOR);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel statusLabel = new JLabel("<html><font color='#22C55E'>●</font> JDBC ACTIVE</html>");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusPanel.add(statusLabel, BorderLayout.NORTH);
        
        JLabel statusSub = new JLabel("Connected to MySQL: rrs_db");
        statusSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        statusSub.setForeground(MUTED_TEXT);
        statusPanel.add(statusSub, BorderLayout.SOUTH);
        
        JPanel statusContainer = new JPanel(new BorderLayout());
        statusContainer.setBackground(SECONDARY_COLOR);
        statusContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statusContainer.add(statusPanel);
        menu.add(statusContainer);

        add(menu, BorderLayout.WEST);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 17, 22)); // #0F1116
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(BorderFactory.createCompoundBorder(header.getBorder(), BorderFactory.createEmptyBorder(0, 30, 0, 30)));
        
        JLabel headerTitle = new JLabel("Operational Overview");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerTitle.setForeground(Color.WHITE);
        header.add(headerTitle, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        userPanel.setOpaque(false);
        
        JPanel userInfo = new JPanel(new GridLayout(2, 1));
        userInfo.setOpaque(false);
        JLabel userName = new JLabel(SessionManager.getCurrentUser().getUsername());
        userName.setForeground(Color.WHITE);
        userName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel userRole = new JLabel(SessionManager.getCurrentUser().getRole().toString());
        userRole.setForeground(MUTED_TEXT);
        userRole.setFont(new Font("Segoe UI", Font.BOLD, 10));
        userInfo.add(userName);
        userInfo.add(userRole);
        userPanel.add(userInfo);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            SessionManager.logout();
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        userPanel.add(logoutBtn);
        header.add(userPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Main Content Area
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(PRIMARY_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Stats Row
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        statsRow.setOpaque(false);
        statsRow.add(createStatCard("ACTIVE TRAINS", "128", 75));
        statsRow.add(createStatCard("DAILY BOOKINGS", "1,402", 0));
        statsRow.add(createStatCard("AVAILABLE SEATS", "4,821", 0));
        statsRow.add(createStatCard("DAILY REVENUE", "$12.4k", 0));
        mainContent.add(statsRow, BorderLayout.NORTH);

        JLabel logoLabel = new JLabel("RAILWAY SYSTEM", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 64));
        logoLabel.setForeground(new Color(25, 28, 35));
        mainContent.add(logoLabel, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String val, int progress) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(SURFACE_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel tLabel = new JLabel(title);
        tLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        tLabel.setForeground(MUTED_TEXT);
        card.add(tLabel, BorderLayout.NORTH);

        JLabel vLabel = new JLabel(val);
        vLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        vLabel.setForeground(Color.WHITE);
        card.add(vLabel, BorderLayout.CENTER);

        if (progress > 0) {
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(progress);
            bar.setPreferredSize(new Dimension(0, 4));
            bar.setForeground(ACCENT_COLOR);
            bar.setBackground(new Color(30, 35, 45));
            bar.setBorder(null);
            card.add(bar, BorderLayout.SOUTH);
        }

        return card;
    }
}
