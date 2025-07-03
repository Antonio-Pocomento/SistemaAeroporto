package gui;

import controller.Controller;
import custom_exceptions.EmailAlreadyExistsException;
import custom_exceptions.UserAlreadyExistsException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe {@code RegisterGUI}
 */
public class RegisterGUI {
    public final JFrame frame = new JFrame("Registrazione");
    private JPanel registerPanel;
    private JPanel contentPanel;
    private JButton registerButton;
    private JButton returnButton;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel usernameIcon;
    private JLabel emailIcon;
    private JLabel passwordIcon;
    private JPasswordField confirmPasswordField;
    private JLabel confirmPasswordIcon;
    private JLabel passwordEyeIcon;
    private JLabel confirmPasswordEyeIcon;
    private JLabel passwordRating;
    private JLabel usernameErrMessage;
    private JLabel emailErrMessage;
    private JLabel confirmPasswordErrMessage;
    private static final String DEFAULT_USERFIELD_TEXT = "Nome Utente";
    private static final String DEFAULT_PASSWORDFIELD_TEXT = "Password";
    private static final String DEFAULT_CONFIRMPASSWORDFIELD_TEXT = "Conferma Password";
    private static final String DEFAULT_EMAIL_TEXT = "Email";
    private static final String ERROR_TEXT = "Errore Registrazione";

    /**
     * Costruttore della classe {@code RegisterGUI}
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    public RegisterGUI(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,registerPanel);

        usernameIcon.setIcon(ImageLoader.loadIcon("src/main/images/userNameIcon.png"));
        emailIcon.setIcon(ImageLoader.loadIcon("src/main/images/emailIcon.png"));
        passwordIcon.setIcon(ImageLoader.loadIcon("src/main/images/lock.png"));
        confirmPasswordIcon.setIcon(ImageLoader.loadIcon("src/main/images/lock.png"));
        confirmPasswordEyeIcon.setIcon(ImageLoader.loadIcon("src/main/images/openEye.png"));
        passwordEyeIcon.setIcon(ImageLoader.loadIcon("src/main/images/openEye.png"));
        registerButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));

        passwordField.setEchoChar((char) 0);
        confirmPasswordField.setEchoChar((char) 0);

        confirmPasswordField.setBorder(new LineBorder(Color.black,2,false));
        usernameField.setBorder(new LineBorder(Color.black,2,false));
        passwordField.setBorder(new LineBorder(Color.black,2,false));
        emailField.setBorder(new LineBorder(Color.black,2,false));
        FormHelper.bindFormListeners(usernameField,emailField,passwordField,confirmPasswordField,usernameErrMessage,emailErrMessage,passwordRating,confirmPasswordErrMessage,
                registerButton,controller,DEFAULT_USERFIELD_TEXT,DEFAULT_PASSWORDFIELD_TEXT);
        UtilFunctionsForGUI.disallowSpaces(confirmPasswordField);
        UtilFunctionsForGUI.disallowSpaces(passwordField);
        UtilFunctionsForGUI.disallowSpaces(emailField);
        UtilFunctionsForGUI.disallowSpaces(usernameField);
        UtilFunctionsForGUI.addTextFieldPlaceholder(usernameField,DEFAULT_USERFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(emailField,DEFAULT_EMAIL_TEXT);
        UtilFunctionsForGUI.addPasswordFieldPlaceholder(passwordField,DEFAULT_PASSWORDFIELD_TEXT);
        UtilFunctionsForGUI.addPasswordFieldPlaceholder(confirmPasswordField,DEFAULT_CONFIRMPASSWORDFIELD_TEXT);
        UtilFunctionsForGUI.addHoverEffect(registerButton);
        UtilFunctionsForGUI.addHoverEffect(returnButton);
        UtilFunctionsForGUI.addPasswordVisibilityToggle(passwordField,passwordEyeIcon,DEFAULT_PASSWORDFIELD_TEXT);
        UtilFunctionsForGUI.addPasswordVisibilityToggle(confirmPasswordField,confirmPasswordEyeIcon,DEFAULT_CONFIRMPASSWORDFIELD_TEXT);

        UtilFunctionsForGUI.setupFrame(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        registerButton.addActionListener(_ -> {
            try {
                controller.registraUtente(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getPassword());
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(1000, 100));
                JLabel label = new JLabel("Registrazione avvenuta con successo!", SwingConstants.CENTER);
                label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                panel.add(label);
                JOptionPane.showMessageDialog(null, panel, "Registrazione riuscita", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (UserAlreadyExistsException _) {
                ErrorPanel.showErrorDialog(null,"Nome Utente già in uso!",DEFAULT_USERFIELD_TEXT);
            }
            catch (EmailAlreadyExistsException _) {
                ErrorPanel.showErrorDialog(null,"È già presente un account che usa questa email!", ERROR_TEXT);
            }
            catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto.<br>Assicurati di aver inserito correttamente i dati.", ERROR_TEXT);
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, ERROR_TEXT, ex);
            }
        });

        returnButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
    }
}
