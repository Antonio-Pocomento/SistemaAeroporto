package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Home {
    private JPanel homePanel;
    private JButton exitButton;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel loginIcon;
    private static JFrame frame;
    private final Controller controller;

    public Home() {
        controller = new Controller();
        loginIcon.setIcon(ImageLoader.loadIcon("src/main/images/airplaneIcon.png"));
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,homePanel);
        registerButton.setBorder(new LineBorder(Color.black,3,false));
        loginButton.setBorder(new LineBorder(Color.black,3,false));
        exitButton.setBorder(new LineBorder(Color.black,3,false));
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
