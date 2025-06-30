package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * La classe {@code Home} rappresenta l'interfaccia grafica principale dell'applicazione.
 * Consente all'utente di scegliere se effettuare il login, registrarsi o uscire dall'applicazione.
 *
 * <p>Questa schermata contiene tre pulsanti:
 * <ul>
 *     <li><b>Login</b>: apre la finestra di login.</li>
 *     <li><b>Register</b>: apre la finestra di registrazione.</li>
 *     <li><b>Exit</b>: chiude l'applicazione.</li>
 * </ul>
 *
 * <p>Viene utilizzato un {@link Controller} per gestire la logica applicativa.
 *
 */
public class Home {
    private JPanel homePanel;
    private JButton exitButton;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel loginIcon;
    private static JFrame frame;
    private final Controller controller;

    /**
     * Costruttore della classe {@code Home}.
     * Inizializza i componenti grafici, imposta icone e bordi,
     * e definisce le azioni associate ai pulsanti.
     */
    public Home() {
        controller = new Controller();
        loginIcon.setIcon(ImageLoader.loadIcon("src/main/images/airplaneIcon.png"));
        UtilFunctionsForGUI.setupLayoutAndBackground(frame, homePanel);
        registerButton.setBorder(new LineBorder(Color.black, 3, false));
        loginButton.setBorder(new LineBorder(Color.black, 3, false));
        exitButton.setBorder(new LineBorder(Color.black, 3, false));
        UtilFunctionsForGUI.addHoverEffect(exitButton);
        UtilFunctionsForGUI.addHoverEffect(registerButton);
        UtilFunctionsForGUI.addHoverEffect(loginButton);

        exitButton.addActionListener(_ -> {
            frame.dispose();
            System.exit(0);
        });
        loginButton.addActionListener(_ -> {
            LoginGUI loginGUI = new LoginGUI(frame, controller);
            loginGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
        registerButton.addActionListener(_ -> {
            RegisterGUI registerGUI = new RegisterGUI(frame, controller);
            registerGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
    }

    /**
     * Metodo principale di avvio dell'applicazione.
     * Crea e visualizza la finestra della schermata home.
     *
     * @param args argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        frame = new JFrame("Home");
        frame.setContentPane(new Home().homePanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
