package gui;

import controller.Controller;
import custom_exceptions.ImageReadException;
import custom_exceptions.UserNotFoundException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginGUI {
    public JFrame frame;
    private JPanel loginPanel;
    private JPanel contentPanel;
    private JTextField userField;
    private JLabel userIcon;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton returnButton;
    private JLabel passwordIcon;
    private JLabel passwordEyeIcon;

    private void caricaHomePage(JFrame frameChiamante, Controller controller)
    {
        HomePage homePage;
        try {
            homePage = new HomePage(frameChiamante, controller);
        } catch (IOException ex) {
            throw new ImageReadException("Errore nella lettura di un'immagine");
        }
        homePage.frame.setVisible(true);
        frame.setVisible(false);
    }

    private void caricaAdminHomePage(JFrame frameChiamante, Controller controller)
    {
        HomePageAdmin homePageAdmin;
        try {
            homePageAdmin = new HomePageAdmin(frameChiamante, controller);
        } catch (IOException ex) {
            throw new ImageReadException("Errore nella lettura di un'immagine");
        }
        homePageAdmin.frame.setVisible(true);
        frame.setVisible(false);
    }

    public LoginGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Login");
        frame.setContentPane(loginPanel);

        loginPanel.setLayout(new OverlayLayout(loginPanel));
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

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });
        /* ********************************** */

        /* ********************************** */
        loginButton.addActionListener(_ -> {
            try {
                if(controller.loginUtente(userField.getText().trim(),passwordField.getPassword()) == 1)
                    caricaAdminHomePage(frameChiamante, controller);
                else
                    caricaHomePage(frameChiamante, controller);
            } catch (SQLException ex) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(1000, 150));
                JLabel label = new JLabel("<html>Qualcosa è andato storto.<br>Assicurati di aver inserito correttamente i dati.</html>", SwingConstants.CENTER);
                label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                panel.add(label);
                JOptionPane.showMessageDialog(null, panel, "Errore login", JOptionPane.ERROR_MESSAGE);
            }
              catch (UserNotFoundException ex) {
                  JPanel panel = new JPanel();
                  panel.setPreferredSize(new Dimension(1000, 100));
                  JLabel label = new JLabel("Nessun utente corrisponde ai dati inseriti.", SwingConstants.CENTER);
                  label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                  panel.add(label);
                  JOptionPane.showMessageDialog(null, panel, "Errore login", JOptionPane.ERROR_MESSAGE);
              }
        });
        returnButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
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
                if(!Arrays.equals(passwordField.getPassword(), "Password".toCharArray())) {
                    if(passwordField.getEchoChar() == '●') {
                        passwordField.setEchoChar((char) 0);
                        try {
                            passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/closedEye.png"))));
                        } catch (IOException ex) {
                            throw new ImageReadException(ex.getMessage());
                        }
                    }
                    else {passwordField.setEchoChar('●');
                        try {
                            passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
                        } catch (IOException ex) {
                            throw new ImageReadException(ex.getMessage());
                        }
                    }
                }
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(Arrays.equals(passwordField.getPassword(), "Password".toCharArray())) {
                    passwordField.setForeground(Color.black);
                    passwordField.setText("");
                    passwordField.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(new String(passwordField.getPassword()).isBlank()) {
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
                if(userField.getText().isBlank()) {
                    userField.setText("Nome Utente o Email");
                    userField.setForeground(Color.gray);
                }
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });
        userField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });
    }
}
