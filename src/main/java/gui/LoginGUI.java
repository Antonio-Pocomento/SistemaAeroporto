package gui;

import controller.Controller;
import custom_exceptions.UserNotFoundException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe {@code LoginGUI}
 */
public class LoginGUI {
    public final JFrame frame = new JFrame(LOGIN_TEXT);
    private JPanel loginPanel;
    private JPanel contentPanel;
    private JTextField userField;
    private JLabel userIcon;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton returnButton;
    private JLabel passwordIcon;
    private JLabel passwordEyeIcon;
    private static final String LOGIN_TEXT = "Login";
    private static final String DEFAULT_USERFIELD_TEXT = "Nome Utente o Email";
    private static final String DEFAULT_PASSWORDFIELD_TEXT = "Password";

    private void caricaHomePage(JFrame frameChiamante, Controller controller)
    {
        HomePage homePage = new HomePage(frameChiamante, controller);
        homePage.frame.setVisible(true);
        frame.setVisible(false);
    }

    private void caricaAdminHomePage(JFrame frameChiamante, Controller controller)
    {
        HomePageAdmin homePageAdmin = new HomePageAdmin(frameChiamante, controller);
        homePageAdmin.frame.setVisible(true);
        frame.setVisible(false);
    }

    /**
     * Costruttore della classe {@code LoginGUI}
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    public LoginGUI(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,loginPanel);

        userIcon.setIcon(ImageLoader.loadIcon("src/main/images/userIcon.png"));
        passwordIcon.setIcon(ImageLoader.loadIcon("src/main/images/lock.png"));
        passwordEyeIcon.setIcon(ImageLoader.loadIcon("src/main/images/openEye.png"));
        passwordEyeIcon.setBorder(new EmptyBorder(0,0,0,120));
        userField.setBorder(new LineBorder(Color.black,2,false));
        passwordField.setBorder(new LineBorder(Color.black,2,false));
        loginButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));

        Map<JTextField, String> fields = new HashMap<>();
        fields.put(userField, DEFAULT_USERFIELD_TEXT);
        fields.put(passwordField, DEFAULT_PASSWORDFIELD_TEXT);
        Set<JTextField> optional = new HashSet<>();
        FormHelper.bindButtonToTextFields(loginButton,fields,optional);

        passwordField.setEchoChar((char) 0);

        UtilFunctionsForGUI.disallowSpaces(passwordField);
        UtilFunctionsForGUI.disallowSpaces(userField);
        UtilFunctionsForGUI.addHoverEffect(returnButton);
        UtilFunctionsForGUI.addHoverEffect(loginButton);
        UtilFunctionsForGUI.addPasswordVisibilityToggle(passwordField,passwordEyeIcon,DEFAULT_PASSWORDFIELD_TEXT);
        UtilFunctionsForGUI.addPasswordFieldPlaceholder(passwordField,DEFAULT_PASSWORDFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(userField,DEFAULT_USERFIELD_TEXT);

        UtilFunctionsForGUI.setupFrame(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        loginButton.addActionListener(_ -> {
            try {
                if(controller.loginUtente(userField.getText().trim(),passwordField.getPassword()) == 1)
                    caricaAdminHomePage(frameChiamante, controller);
                else
                    caricaHomePage(frameChiamante, controller);
            } catch (UserNotFoundException _) {
                ErrorPanel.showErrorDialog(null,"Nessun utente corrisponde ai dati inseriti.",LOGIN_TEXT);
            }
              catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto.<br>Assicurati di aver inserito correttamente i dati.",LOGIN_TEXT);
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Login Utente", ex);
            }

        });
        returnButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
    }
}
