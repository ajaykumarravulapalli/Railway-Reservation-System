package gui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterFrame extends BaseFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<User.Role> roleCombo;
    private UserDAO userDAO = new UserDAO();

    public RegisterFrame() {
        super("Railway Reservation - Register");
        initUI();
        setSize(400, 350);
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

        JLabel title = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; card.add(new JLabel("Username"), gbc);
        gbc.gridx = 1; userField = createStyledTextField(); card.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; card.add(new JLabel("Password"), gbc);
        gbc.gridx = 1; passField = new JPasswordField();
        passField.setBackground(SURFACE_COLOR);
        passField.setForeground(Color.WHITE);
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        card.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; card.add(new JLabel("Role"), gbc);
        gbc.gridx = 1; roleCombo = new JComboBox<>(User.Role.values()); card.add(roleCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        JButton regBtn = createStyledButton("REGISTER SYSTEM");
        regBtn.addActionListener(this::handleRegister);
        card.add(regBtn, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 10, 10);
        JButton backBtn = new JButton("<html><u>Back to identification</u></html>");
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setForeground(ACCENT_COLOR);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        card.add(backBtn, gbc);

        add(card);
    }

    private void handleRegister(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        User.Role role = (User.Role) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        if (userDAO.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            new LoginFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed or Username exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
