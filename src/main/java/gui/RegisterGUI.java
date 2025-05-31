package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
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

public class RegisterGUI {
    public JFrame frame;
    private JPanel registerPanel;
    private JPanel contentPanel;
    private JButton registerButton;
    private JButton returnButton;
    private JPanel backgroundContentPanel;
    private JPanel formPanel;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPanel usernamePanel;
    private JLabel usernameObb;
    private JLabel usernameIcon;
    private JLabel emailIcon;
    private JLabel passwordIcon;
    private JPasswordField confirmPasswordField;
    private JLabel confirmPasswordIcon;
    private JPanel errorPanel;
    private JLabel errorMessage;
    private JLabel passwordEyeIcon;
    private JLabel confirmPasswordEyeIcon;
    private JLabel passwordRating;
    private JLabel usernameErrMessage;
    private JLabel emailErrMessage;
    private JPanel emailPanel;
    private JPanel passwordPanel;
    private JPanel confirmPasswordPanel;
    private JLabel confirmPasswordErrMessage;
    private JLabel emailObb;

    public RegisterGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Registrazione");
        frame.setContentPane(registerPanel);
        registerPanel.setLayout(new OverlayLayout(registerPanel));
        registerPanel.add(contentPanel);
        registerPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));

        usernameIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userNameIcon.png"))));
        emailIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/emailIcon.png"))));
        passwordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        confirmPasswordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        errorPanel.setBorder(new LineBorder(Color.BLACK,2,false));
        confirmPasswordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
        passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
        registerButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));

        passwordField.setEchoChar((char) 0);
        confirmPasswordField.setEchoChar((char) 0);
        //registerButton.setEnabled(false);

        confirmPasswordField.setBorder(new LineBorder(Color.black,2,false));
        usernameField.setBorder(new LineBorder(Color.black,2,false));
        passwordField.setBorder(new LineBorder(Color.black,2,false));
        emailField.setBorder(new LineBorder(Color.black,2,false));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        /* ************************************************* */
        registerButton.addActionListener(e -> {
            try {
                controller.registraUtente(usernameField.getText(),emailField.getText(),passwordField.getText());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(registerButton, "Nome utente già esistente.");
            }
            // INSERT NEL DATABASE
        });


        returnButton.addActionListener(e -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
        /* ************************************************* */

        /* ************************************************* */
        Document doc = passwordField.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aggiornaLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aggiornaLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aggiornaLabel(); // usato per campi di testo styled, non serve spesso
            }

            private void aggiornaLabel() {
                char[] testo = passwordField.getPassword();
                if (testo.length < 5 || Arrays.equals(testo, "Password".toCharArray())) {
                    passwordRating.setText("La Password deve contenere almeno 5 caratteri");
                    passwordRating.setForeground(Color.red);
                } else if (testo.length < 10) {
                    passwordRating.setText("Password: Vulnerabile");
                    passwordRating.setForeground(Color.black);
                } else if (testo.length < 14) {
                    passwordRating.setText("Password: Sicura");
                    passwordRating.setForeground(Color.black);
                } else {
                    passwordRating.setText("Password: Molto Sicura");
                    passwordRating.setForeground(Color.black);
                }
                if(Arrays.equals(testo, confirmPasswordField.getPassword())){
                    confirmPasswordErrMessage.setVisible(false);
                }
                else confirmPasswordErrMessage.setVisible(true);
            }
        });

        Document doc2 = usernameField.getDocument();
        doc2.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aggiornaLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aggiornaLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aggiornaLabel(); // usato per campi di testo styled, non serve spesso
            }

            private void aggiornaLabel() {
                String testo = usernameField.getText();
                if (testo.length() < 5 || testo.equals("Nome Utente")) {
                    usernameErrMessage.setText("Il Nome Utente deve contenere almeno 5 caratteri");
                    usernameErrMessage.setVisible(true);
                } else {
                    usernameErrMessage.setVisible(false);
                }
            }
        });

        Document doc3 = confirmPasswordField.getDocument();
        doc3.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aggiornaLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aggiornaLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aggiornaLabel(); // usato per campi di testo styled, non serve spesso
            }

            private void aggiornaLabel() {
                String testo = confirmPasswordField.getText();
                if(testo.equals(passwordField.getText())){
                    confirmPasswordErrMessage.setVisible(false);
                }
                else confirmPasswordErrMessage.setVisible(true);
            }
        });
        /* ************************************************* */

        /* ************************************************* */
        confirmPasswordEyeIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!confirmPasswordField.getText().equals("Conferma Password")) {
                    if(confirmPasswordField.getEchoChar() == '\u25CF') {
                        confirmPasswordField.setEchoChar((char) 0);
                        try {
                            confirmPasswordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/closedEye.png"))));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else {confirmPasswordField.setEchoChar('\u25CF');
                        try {
                            confirmPasswordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
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
        /* ************************************************* */

        /* ************************************************* */
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registerButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(null);
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
        /* ************************************************* */

        /* ************************************************* */
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(usernameField.getText().equals("Nome Utente")) {
                    usernameField.setForeground(Color.black);
                    usernameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(usernameField.getText().equals("")) {
                    usernameField.setForeground(Color.gray);
                    usernameField.setText("Nome Utente");
                }
            }
        });
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(emailField.getText().equals("Email")) {
                    emailField.setForeground(Color.black);
                    emailField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(emailField.getText().equals("")) {
                    emailField.setForeground(Color.gray);
                    emailField.setText("Email");
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
        confirmPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(confirmPasswordField.getText().equals("Conferma Password")) {
                    confirmPasswordField.setForeground(Color.black);
                    confirmPasswordField.setText("");
                    confirmPasswordField.setEchoChar('\u25CF');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(confirmPasswordField.getText().equals("")) {
                    confirmPasswordField.setForeground(Color.gray);
                    confirmPasswordField.setText("Conferma Password");
                    confirmPasswordField.setEchoChar((char) 0);
                }
            }
        });
        /* ************************************************* */
    }
}
