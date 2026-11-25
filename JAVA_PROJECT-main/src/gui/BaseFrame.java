package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame {
    protected static final Color PRIMARY_COLOR = new Color(10, 11, 14);     // #0A0B0E
    protected static final Color SECONDARY_COLOR = new Color(17, 20, 24);   // #111418
    protected static final Color SURFACE_COLOR = new Color(22, 25, 30);     // #16191E
    protected static final Color BORDER_COLOR = new Color(30, 35, 42);      // #1E232A
    protected static final Color ACCENT_COLOR = new Color(99, 102, 241);    // #6366F1 (Indigo-500)
    protected static final Color TEXT_COLOR = new Color(203, 213, 225);     // #CBD5E1 (Slate-300)
    protected static final Color MUTED_TEXT = new Color(100, 116, 139);      // #64748B (Slate-500)

    public BaseFrame(String title) {
        super(title);
        setupGlobalStyle();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(PRIMARY_COLOR);
    }

    private void setupGlobalStyle() {
        UIManager.put("Panel.background", PRIMARY_COLOR);
        UIManager.put("Label.foreground", TEXT_COLOR);
        UIManager.put("Button.background", ACCENT_COLOR);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("TextField.background", SURFACE_COLOR);
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.caretForeground", Color.WHITE);
        UIManager.put("ComboBox.background", SURFACE_COLOR);
        UIManager.put("ComboBox.foreground", TEXT_COLOR);
        UIManager.put("Table.background", SURFACE_COLOR);
        UIManager.put("Table.foreground", TEXT_COLOR);
        UIManager.put("Table.gridColor", BORDER_COLOR);
        UIManager.put("TableHeader.background", SECONDARY_COLOR);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("ScrollPane.background", PRIMARY_COLOR);
        UIManager.put("Viewport.background", PRIMARY_COLOR);
    }

    protected JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(ACCENT_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    protected JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(SURFACE_COLOR);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }
}
