package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * La classe {@code FormHelper}
 */
public class FormHelper {

    /**
     * Costruttore FormHelper
     */
    FormHelper() {}

    /**
     * Associa i bottoni ai textField corrispondenti  .
     *
     * @param button         bottone
     * @param fieldDefaults  field defaults
     * @param optionalFields optional fields
     */
    public static void bindButtonToTextFields(JButton button,
                                              Map<JTextField, String> fieldDefaults,
                                              Set<JTextField> optionalFields) {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { toggle(); }
            public void removeUpdate(DocumentEvent e) { toggle(); }
            public void changedUpdate(DocumentEvent e) { toggle(); }

            private void toggle() {
                boolean allValid = fieldDefaults.entrySet().stream()
                        .filter(entry -> !optionalFields.contains(entry.getKey()))
                        .allMatch(entry -> {
                            String current = entry.getKey().getText().trim();
                            String defaultValue = entry.getValue();
                            return !current.isEmpty() && !current.equals(defaultValue);
                        });
                button.setEnabled(allValid);
            }
        };

        for (JTextField field : fieldDefaults.keySet()) {
            field.getDocument().addDocumentListener(listener);
        }

        // inizializza stato
        listener.insertUpdate(null);
    }

    /**
     * Associazione listeners
     *
     * @param usernameField             campo username
     * @param emailField                campo email
     * @param passwordField             campo password field
     * @param confirmPasswordField      campo di conferma password
     * @param usernameErrMessage        messaggio di errore username
     * @param emailErrMessage           messaggio di errore email
     * @param passwordRating            valutazione password
     * @param confirmPasswordErrMessage messaggio di errore confirm password
     * @param registerButton            bottone di registrazione
     * @param controller                controller
     * @param defaultUsernameText       testo di default username
     * @param defaultPasswordText       testo di default password
     */
    public static void bindFormListeners(
            JTextField usernameField,
            JTextField emailField,
            JPasswordField passwordField,
            JPasswordField confirmPasswordField,
            JLabel usernameErrMessage,
            JLabel emailErrMessage,
            JLabel passwordRating,
            JLabel confirmPasswordErrMessage,
            JButton registerButton,
            Controller controller,
            String defaultUsernameText,
            String defaultPasswordText
    ) {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aggiornaLabel(); }
            public void removeUpdate(DocumentEvent e) { aggiornaLabel(); }
            public void changedUpdate(DocumentEvent e) { aggiornaLabel(); }

            private void aggiornaLabel() {
                // Username
                String username = usernameField.getText().trim();
                if (username.length() < 3 || username.equals(defaultUsernameText)) {
                    usernameErrMessage.setText("Il Nome Utente deve contenere almeno 3 caratteri");
                    usernameErrMessage.setVisible(true);
                } else {
                    usernameErrMessage.setVisible(false);
                }

                // Email
                String email = emailField.getText().trim();
                boolean emailValid = controller.isEmailValid(email);
                emailErrMessage.setVisible(!emailValid);

                // Password
                char[] password = passwordField.getPassword();
                if (password.length < 8 || Arrays.equals(password, defaultPasswordText.toCharArray())) {
                    passwordRating.setText("La Password deve contenere almeno 8 caratteri");
                    passwordRating.setForeground(Color.red);
                } else if (password.length < 10) {
                    passwordRating.setText("Password: Vulnerabile");
                    passwordRating.setForeground(Color.black);
                } else if (password.length < 14) {
                    passwordRating.setText("Password: Sicura");
                    passwordRating.setForeground(Color.black);
                } else {
                    passwordRating.setText("Password: Molto Sicura");
                    passwordRating.setForeground(Color.black);
                }

                // Confirm password validation
                char[] confirmPassword = confirmPasswordField.getPassword();
                boolean passwordsMatch = Arrays.equals(password, confirmPassword);
                confirmPasswordErrMessage.setVisible(!passwordsMatch);

                // Enable/disable register button
                boolean canRegister = controller.canPressRegister(username, email, password, confirmPassword);
                registerButton.setEnabled(canRegister);
            }
        };

        usernameField.getDocument().addDocumentListener(listener);
        emailField.getDocument().addDocumentListener(listener);
        passwordField.getDocument().addDocumentListener(listener);
        confirmPasswordField.getDocument().addDocumentListener(listener);

        // Inizializza stato
        listener.insertUpdate(null);
    }
}
