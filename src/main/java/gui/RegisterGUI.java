package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class RegisterGUI {
    public JFrame frame;
    private JPanel registerPanel;
    private JPanel contentPanel;
    private JTextField usernameField;
    private JLabel usernameObb;
    private JPanel usernamePanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton returnButton;
    private JPanel emailPanel;
    private JPanel passwordPanel;
    private JLabel usernameIcon;
    private JLabel emailIcon;
    private JLabel passwordIcon;
    private JLabel passwordObb;
    private JLabel eyeIcon;

    public RegisterGUI(JFrame frameChiamante, Controller controlle) throws IOException {
        frame = new JFrame("Register");
        frame.setContentPane(registerPanel);

        registerPanel.setLayout(new OverlayLayout(registerPanel));
        contentPanel.setOpaque(false);
        usernamePanel.setOpaque(false);
        emailPanel.setOpaque(false);
        passwordPanel.setOpaque(false);
        registerPanel.add(contentPanel);
        registerPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));

        registerButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));
        emailField.setBorder(new LineBorder(Color.black,2,false));
        usernameField.setBorder(new LineBorder(Color.black,2,false));
        passwordField.setBorder(new LineBorder(Color.black,2,false));

        usernameIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userNameIcon.png"))));
        usernameIcon.setBorder(new EmptyBorder(0,700,0,0));
        usernameObb.setBorder(new EmptyBorder(0,0,0,800));

        emailIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/emailIcon.png"))));
        emailIcon.setBorder(new EmptyBorder(0,700,0,0));
        eyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
        eyeIcon.setBorder(new EmptyBorder(0,0,0,700));
        passwordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        passwordIcon.setBorder(new EmptyBorder(0,700,0,0));
        passwordField.setEchoChar('\u25CF');
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);



        emailField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(emailField.getText().equals("Email"))
                    emailField.setText("");
            }
        });
        usernameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(usernameField.getText().equals("Nome Utente"))
                    usernameField.setText("");
            }
        });
        eyeIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(passwordField.getEchoChar() == (char)0)
                {
                    passwordField.setEchoChar('\u25CF');
                    try {
                        eyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    passwordField.setEchoChar((char)0);
                    try {
                        eyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/closedEye.png"))));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
