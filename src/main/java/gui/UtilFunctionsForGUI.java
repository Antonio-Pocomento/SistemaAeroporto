package gui;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * La classe {@code UtilFunctionsForGUI} contiene metodi utili per il funzionamento dell'interfaccia
 */
public class UtilFunctionsForGUI {
    private UtilFunctionsForGUI() {}

    /**
     * Aggiungi l'effetto hover
     *
     * @param button il bottone
     */
    public static void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(button.isEnabled()) {
                    button.setBackground(Color.LIGHT_GRAY);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(null);
            }
        });
    }

    /**
     * setupFrame
     *
     * @param frame il frame
     */
    public static void setupFrame(JFrame frame) {
        // imposta chiusura
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // centra e massimizza
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    /**
     * Imposta layout e sfondo.
     *
     * @param frame        il frame
     * @param contentPanel il pannello dei contenuti
     */
    public static void setupLayoutAndBackground(JFrame frame, JPanel contentPanel) {
        frame.setContentPane(contentPanel);
        contentPanel.setLayout(new OverlayLayout(contentPanel));
        ImageLoader.addBackgroundPanel(contentPanel);
    }

    /**
     * Non consentire spazi.
     *
     * @param textComponent la componente testuale
     */
    public static void disallowSpaces(JTextComponent textComponent) {
        textComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });
    }

    /**
     * Aggiunge un interruttore per la visibilità della password.
     *
     * @param passwordField       il campo password
     * @param passwordEyeIcon     l'icona dell'occhio per la password
     * @param defaultPasswordText il testo di default per password
     */
    public static void addPasswordVisibilityToggle(JPasswordField passwordField, JLabel passwordEyeIcon, String defaultPasswordText) {
        passwordEyeIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Se la password è diversa dal valore di default
                if (!Arrays.equals(passwordField.getPassword(), defaultPasswordText.toCharArray())) {
                    if (passwordField.getEchoChar() == '●') {
                        // Mostra la password
                        passwordField.setEchoChar((char) 0);
                        passwordEyeIcon.setIcon(ImageLoader.loadIcon("src/main/images/closedEye.png"));
                    } else {
                        // Nascondi la password
                        passwordField.setEchoChar('●');
                        passwordEyeIcon.setIcon(ImageLoader.loadIcon("src/main/images/openEye.png"));
                    }
                }
            }
        });
    }

    /**
     * Aggiunge segnaposto al campo di testo.
     *
     * @param textField       il campo di testo
     * @param placeholderText il testo del segnaposto
     */
    public static void addTextFieldPlaceholder(JTextField textField, String placeholderText) {
        textField.setForeground(Color.gray);
        textField.setText(placeholderText);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isBlank()) {
                    textField.setText(placeholderText);
                    textField.setForeground(Color.gray);
                }
            }
        });
    }

    /**
     * Aggiunge segnaposto per il campo password.
     *
     * @param passwordField   il campo password
     * @param placeholderText il testo per il segnaposto
     */
    public static void addPasswordFieldPlaceholder(JPasswordField passwordField, String placeholderText) {
        passwordField.setForeground(Color.gray);
        passwordField.setText(placeholderText);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(passwordField.getPassword(), placeholderText.toCharArray())) {
                    passwordField.setForeground(Color.black);
                    passwordField.setText("");
                    passwordField.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isBlank()) {
                    passwordField.setForeground(Color.gray);
                    passwordField.setText(placeholderText);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }
}
