package gui;

import javax.swing.*;
import java.awt.*;

public class ErrorPanel extends JPanel {
    public ErrorPanel(String message) {
        setPreferredSize(new Dimension(1000, 150));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><div style='text-align: center;'>" + message + "</html>", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 36));
        add(label, BorderLayout.CENTER);
    }

    // Metodo statico comodo per mostrare il pannello in una JOptionPane
    public static void showErrorDialog(Component parent, String message, String title) {
        ErrorPanel panel = new ErrorPanel(message);
        JOptionPane.showMessageDialog(parent, panel, title, JOptionPane.ERROR_MESSAGE);
    }
}
