package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Home {
    private JPanel homePanel;
    private JButton exitButton;
    private JButton registerButton;
    private JButton loginButton;
    private JPanel contentPanel;
    private JLabel loginIcon;
    private static JFrame frame;
    private Controller controller;

    public Home() throws IOException {
        controller = new Controller();
        loginIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/airplaneIcon.png"))));
        homePanel.setLayout(new OverlayLayout(homePanel));
        homePanel.add(contentPanel);
        homePanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        registerButton.setBorder(new LineBorder(Color.black,3,false));
        loginButton.setBorder(new LineBorder(Color.black,3,false));
        exitButton.setBorder(new LineBorder(Color.black,3,false));


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginGUI = null;
                try {
                    loginGUI = new LoginGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                loginGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterGUI registerGUI = null;
                try {
                    registerGUI = new RegisterGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                registerGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registerButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registerButton.setBackground(null);
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                loginButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                loginButton.setBackground(null);
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                exitButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                exitButton.setBackground(null);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.uiScale", "1.0");
        frame = new JFrame("Home");
        frame.setContentPane(new Home().homePanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
