package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class LoginGUI {
    public JFrame frame;
    private JPanel loginPanel;
    private JPanel contentPanel;
    private JPanel backgroundPanel;
    private JPanel formPanel;
    private JTextField userField;
    private JLabel userIcon;
    private JPanel userPanel;
    private JPasswordField passwordField;
    private JPanel passwordPanel;
    private JButton loginButton;
    private JButton returnButton;
    private JLabel passwordIcon;
    private JLabel passwordEyeIcon;
    private JPanel errorPanel;
    private JLabel errorMessage;

    public LoginGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Login");
        frame.setContentPane(loginPanel);

        loginPanel.setLayout(new OverlayLayout(loginPanel));
        contentPanel.setOpaque(false);
        loginPanel.add(contentPanel);
        loginPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));

        userIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userIcon.png"))));
        passwordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
        passwordEyeIcon.setBorder(new EmptyBorder(0,0,0,120));

        userField.setBorder(new LineBorder(Color.black,2,false));
        passwordField.setBorder(new LineBorder(Color.black,2,false));

        loginButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));

        passwordField.setEchoChar((char) 0);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        errorPanel.setBorder(new LineBorder(Color.black,2,false));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifica con Database
                if(userField.getText().equals("admin")) {
                    HomePageAdmin adminHomeGUI = null;
                    try {
                        adminHomeGUI = new HomePageAdmin(frameChiamante, controller);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    adminHomeGUI.frame.setVisible(true);
                    frame.dispose();
                }
                else {
                    HomePage userHomeGUI = null;
                    try {
                        userHomeGUI = new HomePage(frameChiamante, controller);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    userHomeGUI.frame.setVisible(true);
                    frame.dispose();
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
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                returnButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                returnButton.setBackground(null);
            }
        });
        passwordEyeIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!passwordField.getText().equals("Password")) {
                    if(passwordField.getEchoChar() == '\u25CF') {
                        passwordField.setEchoChar((char) 0);
                        try {
                            passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/closedEye.png"))));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else {passwordField.setEchoChar('\u25CF');
                        try {
                            passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passwordField.getText().equals("Password")) {
                    passwordField.setForeground(Color.black);
                    passwordField.setText("");
                    passwordField.setEchoChar('\u25CF');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(passwordField.getText().equals("")) {
                    passwordField.setForeground(Color.gray);
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
        userField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(userField.getText().equals("Nome Utente o Email")) {
                    userField.setText("");
                    userField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(userField.getText().equals("")) {
                    userField.setText("Nome Utente o Email");
                    userField.setForeground(Color.gray);
                }
            }
        });
    }
}
