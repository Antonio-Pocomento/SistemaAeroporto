package gui;

import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code ErrorPanel}
 */
public class ErrorPanel extends JPanel {
    /**
     * Costruttore di ErrorPanel.
     *
     * @param message messaggio di errore
     */
    public ErrorPanel(String message) {
        setPreferredSize(new Dimension(1000, 150));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><div style='text-align: center;'>" + message + "</html>", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 36));
        add(label, BorderLayout.CENTER);
    }

    /**
     * Mostra la finestra di errore
     *
     * @param parent  Component genitore
     * @param message messaggio di errore
     * @param title   titolo finestra
     */
// Metodo statico comodo per mostrare il pannello in una JOptionPane
    public static void showErrorDialog(Component parent, String message, String title) {
        ErrorPanel panel = new ErrorPanel(message);
        JOptionPane.showMessageDialog(parent, panel, title, JOptionPane.ERROR_MESSAGE);
    }
}
