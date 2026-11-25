package gui;

import dao.UserDAO;
import model.User;
import util.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends BaseFrame {
    private JTextField userField;
    private JPasswordField passField;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        super("Railway Reservation - Login");
        initUI();
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(SECONDARY_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("RAILWAY PRO", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        JLabel subtitle = new JLabel("Login to secure console", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(MUTED_TEXT);
        gbc.gridy = 1;
        card.add(subtitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2; card.add(new JLabel("Username"), gbc);
        gbc.gridx = 1; userField = createStyledTextField(); card.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; card.add(new JLabel("Password"), gbc);
        gbc.gridx = 1; passField = new JPasswordField(); 
        passField.setBackground(SURFACE_COLOR);
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        card.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        JButton loginBtn = createStyledButton("AUTHENTICATE");
        loginBtn.addActionListener(this::handleLogin);
        card.add(loginBtn, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 10, 10);
        JButton regBtn = new JButton("<html><u>Create new account</u></html>");
        regBtn.setContentAreaFilled(false);
        regBtn.setBorderPainted(false);
        regBtn.setForeground(ACCENT_COLOR);
        regBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        regBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            this.dispose();
        });
        card.add(regBtn, gbc);

        add(card);
    }

    private void handleLogin(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userDAO.login(username, password);
        if (user != null) {
            SessionManager.setCurrentUser(user);
            new DashboardFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
