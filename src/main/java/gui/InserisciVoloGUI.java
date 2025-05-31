package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class InserisciVoloGUI {
    private JPanel insertFlightPanel;
    private JPanel contentPanel;
    private JTextField codeField;
    private JTextField aerOrigineField;
    private JTextField aerArrivoField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField companyField;
    private JTextField seatField;
    private JTextField gateField;
    private JButton insertButton;
    private JButton returnButton;
    private JPanel fieldsPanel;
    public JFrame frame;

    public InserisciVoloGUI(Frame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Login");
        frame.setContentPane(insertFlightPanel);

        insertFlightPanel.setLayout(new OverlayLayout(insertFlightPanel));
        contentPanel.setOpaque(false);
        insertFlightPanel.add(contentPanel);
        insertFlightPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        insertButton.setBorder(new LineBorder(Color.black,3));
        returnButton.setBorder(new LineBorder(Color.black,3));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });
        fieldsPanel.setBorder(new LineBorder(Color.black,10));
        aerArrivoField.setBorder(new LineBorder(Color.BLACK,2,false));
        aerOrigineField.setBorder(new LineBorder(Color.BLACK,2,false));
        dateField.setBorder(new LineBorder(Color.BLACK,2,false));
        timeField.setBorder(new LineBorder(Color.BLACK,2,false));
        companyField.setBorder(new LineBorder(Color.BLACK,2,false));
        gateField.setBorder(new LineBorder(Color.BLACK,2,false));
        seatField.setBorder(new LineBorder(Color.BLACK,2,false));
        codeField.setBorder(new LineBorder(Color.BLACK,2,false));

        codeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(codeField.getText().equals("Codice Volo")) {
                    codeField.setForeground(Color.black);
                    codeField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(codeField.getText().isEmpty()) {
                    codeField.setForeground(Color.gray);
                    codeField.setText("Codice Volo");
                }
            }
        });
        aerOrigineField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(aerOrigineField.getText().equals("Aeroporto Di Origine")) {
                    aerOrigineField.setForeground(Color.black);
                    aerOrigineField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(aerOrigineField.getText().isEmpty()) {
                    aerOrigineField.setForeground(Color.gray);
                    aerOrigineField.setText("Aeroporto Di Origine");
                }
            }
        });
        aerArrivoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(aerArrivoField.getText().equals("Aeroporto Di Arrivo")) {
                    aerArrivoField.setForeground(Color.black);
                    aerArrivoField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(aerArrivoField.getText().isEmpty()) {
                    aerArrivoField.setForeground(Color.gray);
                    aerArrivoField.setText("Aeroporto Di Arrivo");
                }
            }
        });
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(dateField.getText().equals("Data Volo (DD/MM/YYYY)")) {
                    dateField.setForeground(Color.black);
                    dateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(dateField.getText().isEmpty()) {
                    dateField.setForeground(Color.gray);
                    dateField.setText("Data Volo (DD/MM/YYYY)"); // TODO Controllare che sia effettivamente DD/MM
                }
            }
        });
        timeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(timeField.getText().equals("Orario Volo (HH:MM)")) {
                    timeField.setForeground(Color.black);
                    timeField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(timeField.getText().isEmpty()) {
                    timeField.setForeground(Color.gray);
                    timeField.setText("Orario Volo (HH:MM)");
                }
            }
        });
        companyField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(companyField.getText().equals("Compagnia Aerea")) {
                    companyField.setForeground(Color.black);
                    companyField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(companyField.getText().isEmpty()) {
                    companyField.setForeground(Color.gray);
                    companyField.setText("Compagnia Aerea");
                }
            }
        });
        seatField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(seatField.getText().equals("Posti")) {
                    seatField.setForeground(Color.black);
                    seatField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(seatField.getText().isEmpty()) {
                    seatField.setForeground(Color.gray);
                    seatField.setText("Posti");
                }
            }
        });
        gateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(gateField.getText().equals("Numero Gate")) {
                    gateField.setForeground(Color.black);
                    gateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(gateField.getText().isEmpty()) {
                    gateField.setForeground(Color.gray);
                    gateField.setText("Numero Gate");
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
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO DATABASE
            }
        });
        insertButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                insertButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                insertButton.setBackground(null);
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
    }
}
