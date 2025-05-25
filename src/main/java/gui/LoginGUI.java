package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class LoginGUI {
    public JFrame frame;
    private JPanel loginPanel;
    private JPanel contentPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton returnButton;
    private JLabel passwordIcon;
    private JLabel textIcon;

    public LoginGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Login");
        frame.setContentPane(loginPanel);

        loginPanel.setLayout(new OverlayLayout(loginPanel));
        contentPanel.setOpaque(false);
        loginPanel.add(contentPanel);
        loginPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));

        passwordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        passwordIcon.setBorder(new EmptyBorder(0,100,0,0));
        textIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userIcon.png"))));

        loginButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                returnButton.setBorder(new LineBorder(Color.darkGray,3,false));
                returnButton.setBackground(Color.lightGray);
            }
        });
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                returnButton.setBorder(new LineBorder(Color.black,3,false));
                returnButton.setBackground(null);
                returnButton.setForeground(Color.black);
                returnButton.setOpaque(true);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();


            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.isAdmin(loginField.getText())) {
                    HomePageAdmin adminHPGUI= null;
                    try {
                        adminHPGUI = new HomePageAdmin(frame, controller);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    adminHPGUI.frame.setVisible(true);
                    frame.setVisible(false);
                }
                else {
                    HomePage HomePageGUI= null;
                    try {
                        HomePageGUI = new HomePage(frame, controller);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    HomePageGUI.frame.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });
    }
}
