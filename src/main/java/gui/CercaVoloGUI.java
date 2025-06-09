package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class CercaVoloGUI {
    private JPanel cercaVoloPanel;
    private JPanel contentPanel;
    private JPanel fieldsPanel;
    private JPanel tableBackgroundPanel;
    private JButton cercaButton;
    private JButton prenotaButton;
    private JButton tornaIndietroButton;
    private JTable table1;
    private JTextField codeField;
    private JTextField aerOrigineField;
    private JTextField aerArrivoField;
    private JTextField companyField;
    private JTextField timeField;
    private JTextField dateField;
    private JTextField seatField;
    private JTextField delayField;
    private JTextField gateField;
    private JComboBox stateBox;
    private JScrollPane tablePanel;
    public JFrame frame;

    public CercaVoloGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Ricerca Voli");
        frame.setContentPane(cercaVoloPanel);
        cercaVoloPanel.setLayout(new OverlayLayout(cercaVoloPanel));
        contentPanel.setOpaque(false);
        cercaVoloPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        table1.setModel(controller.getFlightsModel());

        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 28));
        tablePanel.setViewportView(table1);
        table1.setOpaque(false);
        tablePanel.setOpaque(false);
        tablePanel.getViewport().setOpaque(false);

        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        prenotaButton.setBorder(new LineBorder(Color.BLACK,3));
        tornaIndietroButton.setBorder(new LineBorder(Color.BLACK,3));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        tableBackgroundPanel.setBorder(new LineBorder(Color.black,10,false));
        fieldsPanel.setBorder(new LineBorder(Color.black,10,false));
        tablePanel.setPreferredSize(new Dimension(2100,table1.getRowHeight()*Math.min(table1.getRowCount(), 6)));
        tableBackgroundPanel.setPreferredSize(new Dimension(tablePanel.getPreferredSize().width+100,tablePanel.getPreferredSize().height+100));

        DefaultCellEditor editor = new DefaultCellEditor(new JTextField());
        editor.getComponent().setFont(table1.getFont());
        table1.setDefaultEditor(Object.class, editor);

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
        delayField.setBorder(new LineBorder(Color.BLACK,2,false));
        stateBox.setBorder(new LineBorder(Color.BLACK,2,false));

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
        delayField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(delayField.getText().equals("Ritardo Volo (HH:MM)")) {
                    delayField.setForeground(Color.black);
                    delayField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(delayField.getText().isEmpty()) {
                    delayField.setForeground(Color.gray);
                    delayField.setText("Ritardo Volo (HH:MM)");
                }
            }
        });
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
        cercaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cercaButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cercaButton.setBackground(null);
            }
        });
        prenotaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                prenotaButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                prenotaButton.setBackground(null);
            }
        });
        tornaIndietroButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                tornaIndietroButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                tornaIndietroButton.setBackground(null);
            }
        });
    }
}
