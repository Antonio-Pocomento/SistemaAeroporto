package gui;

import controller.Controller;
import custom_exceptions.EmailAlreadyExistsException;
import custom_exceptions.ImageReadException;
import custom_exceptions.UserAlreadyExistsException;

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
    public JFrame frame = new JFrame("Registrazione");
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

    public RegisterGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame.setContentPane(registerPanel);
        registerPanel.setLayout(new OverlayLayout(registerPanel));
        registerPanel.add(contentPanel);
        registerPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));

        usernameIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userNameIcon.png"))));
        emailIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/emailIcon.png"))));
        passwordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        confirmPasswordIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/lock.png"))));
        confirmPasswordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
        passwordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
        registerButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));

        passwordField.setEchoChar((char) 0);
        confirmPasswordField.setEchoChar((char) 0);

        confirmPasswordField.setBorder(new LineBorder(Color.black,2,false));
        usernameField.setBorder(new LineBorder(Color.black,2,false));
        passwordField.setBorder(new LineBorder(Color.black,2,false));
        emailField.setBorder(new LineBorder(Color.black,2,false));

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

        /* ************************************************* */
        registerButton.addActionListener(_ -> {
            try {
                controller.registraUtente(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getPassword());
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(1000, 100));
                JLabel label = new JLabel("Registrazione avvenuta con successo!", SwingConstants.CENTER);
                label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                panel.add(label);
                JOptionPane.showMessageDialog(null, panel, "Registrazione riuscita", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(1000, 150));
                JLabel label = new JLabel("<html>Qualcosa è andato storto.<br>Assicurati di aver inserito correttamente i dati.</html>", SwingConstants.CENTER);
                label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                panel.add(label);
                JOptionPane.showMessageDialog(null, panel, "Errore registrazione", JOptionPane.ERROR_MESSAGE);
            }
              catch (UserAlreadyExistsException ex) {
                  JPanel panel = new JPanel();
                  panel.setPreferredSize(new Dimension(1000, 100));
                  JLabel label = new JLabel("Nome Utente già in uso!", SwingConstants.CENTER);
                  label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                  panel.add(label);
                  JOptionPane.showMessageDialog(null, panel, "Errore registrazione", JOptionPane.ERROR_MESSAGE);
              }
              catch (EmailAlreadyExistsException ex) {
                  JPanel panel = new JPanel();
                  panel.setPreferredSize(new Dimension(1000, 100));
                  JLabel label = new JLabel("È già presente un account che usa questa email!", SwingConstants.CENTER);
                  label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                  panel.add(label);
                  JOptionPane.showMessageDialog(null, panel, "Errore registrazione", JOptionPane.ERROR_MESSAGE);
              }
        });


        returnButton.addActionListener(_ -> {
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
                aggiornaLabel();
            }

            private void aggiornaLabel() {
                char[] testo = passwordField.getPassword();
                if (testo.length < 8 || Arrays.equals(testo, "Password".toCharArray())) {
                    passwordRating.setText("La Password deve contenere almeno 8 caratteri");
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
                confirmPasswordErrMessage.setVisible(!Arrays.equals(testo, confirmPasswordField.getPassword()));
                registerButton.setEnabled(controller.canPressRegister(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getPassword(),confirmPasswordField.getPassword()));
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
                aggiornaLabel();
            }

            private void aggiornaLabel() {
                String testo = usernameField.getText();
                if (testo.length() < 3 || testo.equals("Nome Utente")) {
                    usernameErrMessage.setText("Il Nome Utente deve contenere almeno 3 caratteri");
                    usernameErrMessage.setVisible(true);
                } else {
                    usernameErrMessage.setVisible(false);
                }
                registerButton.setEnabled(controller.canPressRegister(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getPassword(),confirmPasswordField.getPassword()));
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
                aggiornaLabel();
            }

            private void aggiornaLabel() {
                confirmPasswordErrMessage.setVisible(!Arrays.equals(confirmPasswordField.getPassword(), passwordField.getPassword()));
                registerButton.setEnabled(controller.canPressRegister(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getPassword(),confirmPasswordField.getPassword()));
            }
        });

        Document doc4 = emailField.getDocument();
        doc4.addDocumentListener(new DocumentListener() {
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
                aggiornaLabel();
            }

            private void aggiornaLabel() {
                String testo = emailField.getText();
                emailErrMessage.setVisible(!controller.isEmailValid(testo));
                registerButton.setEnabled(controller.canPressRegister(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getPassword(),confirmPasswordField.getPassword()));
            }
        });
        /* ************************************************* */

        /* ************************************************* */
        confirmPasswordEyeIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!Arrays.equals(confirmPasswordField.getPassword(), "Conferma Password".toCharArray())) {
                    if(confirmPasswordField.getEchoChar() == '●') {
                        confirmPasswordField.setEchoChar((char) 0);
                        try {
                            confirmPasswordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/closedEye.png"))));
                        } catch (IOException ex) {
                            throw new ImageReadException(ex.getMessage());
                        }
                    }
                    else {confirmPasswordField.setEchoChar('●');
                        try {
                            confirmPasswordEyeIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/openEye.png"))));
                        } catch (IOException ex) {
                            throw new ImageReadException(ex.getMessage());
                        }
                    }
                }
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
        /* ************************************************* */

        /* ************************************************* */
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(registerButton.isEnabled())
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
                if(usernameField.getText().isBlank()) {
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
                if(emailField.getText().isBlank()) {
                    emailField.setForeground(Color.gray);
                    emailField.setText("Email");
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
        confirmPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(Arrays.equals(confirmPasswordField.getPassword(), "Conferma Password".toCharArray())) {
                    confirmPasswordField.setForeground(Color.black);
                    confirmPasswordField.setText("");
                    confirmPasswordField.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(new String(confirmPasswordField.getPassword()).isBlank()) {
                    confirmPasswordField.setForeground(Color.gray);
                    confirmPasswordField.setText("Conferma Password");
                    confirmPasswordField.setEchoChar((char) 0);
                }
            }
        });
        /* ************************************************* */
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });
        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });
        emailField.addKeyListener(new KeyAdapter() {
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
