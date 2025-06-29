package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConfirmDialog extends JDialog {

    private boolean confirmed = false;
    private static final String DEFAULT_FONT = "Times New Roman";

    public ConfirmDialog(Frame parent, String message, String title) {
        super(parent, title, true);

        // Titolo grande centrato
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(DEFAULT_FONT, Font.BOLD, 36));

        // Messaggio centrato
        JLabel messageLabel = new JLabel(
                "<html><div style='text-align: center;'>" + message + "</div></html>",
                SwingConstants.CENTER
        );
        messageLabel.setFont(new Font(DEFAULT_FONT, Font.BOLD, 28));

        // Pulsanti
        JButton yesButton = new JButton("Sì");
        JButton noButton = new JButton("No");
        UtilFunctionsForGUI.addHoverEffect(yesButton);
        UtilFunctionsForGUI.addHoverEffect(noButton);
        yesButton.setBorder(new LineBorder(Color.black,3));
        noButton.setBorder(new LineBorder(Color.black,3));

        yesButton.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 24));
        noButton.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 24));
        yesButton.setFocusable(Boolean.FALSE);
        noButton.setFocusable(Boolean.FALSE);

        // Dimensione pulsanti più grande
        Dimension buttonSize = new Dimension(140, 50);
        yesButton.setPreferredSize(buttonSize);
        noButton.setPreferredSize(buttonSize);

        yesButton.addActionListener((ActionEvent _) -> {
            confirmed = true;
            dispose();
        });

        noButton.addActionListener((ActionEvent _) -> dispose());

        // Pannello pulsanti con spazio tra i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Pannello principale con bordo nero spesso
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new LineBorder(Color.BLACK, 3));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Rendi il pannello focusable
        mainPanel.setFocusable(true);

        // Imposta listener per focus
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                mainPanel.requestFocusInWindow();
            }
        });

        getContentPane().add(mainPanel);

        setSize(1000, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Mostra il dialog e ritorna true se l'utente conferma.
     */
    public boolean showDialog() {
        setVisible(true);
        return confirmed;
    }
}
