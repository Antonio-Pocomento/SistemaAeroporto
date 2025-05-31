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

public class PrenotazioniGUI {
    public JFrame frame;
    private JPanel prenotazioniPanel;
    private JPanel contentPanel;
    private JScrollPane bookingPanel;
    private JTable table1;
    private JButton modifyButton;
    private JComboBox modifyBox;
    private JButton returnButton;
    private JPanel bookingBackgroundPanel;
    private JTextField passengerField;
    private JTextField codeField;
    private JButton cercaButton;
    private JPanel fieldPanel;

    public PrenotazioniGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Prenotazioni");
        frame.setContentPane(prenotazioniPanel);
        prenotazioniPanel.setLayout(new OverlayLayout(prenotazioniPanel));
        contentPanel.setOpaque(false);
        prenotazioniPanel.add(contentPanel);
        prenotazioniPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        //modifyPanel.setOpaque(false);
        modifyBox.addItem("Conferma");
        modifyBox.addItem("Cancella");

        table1.setModel(controller.getBookingTableModel());
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 28));
        bookingPanel.setViewportView(table1);
        bookingPanel.setPreferredSize(new Dimension(2100,table1.getRowHeight()*Math.min(table1.getRowCount(), 6)));
        bookingBackgroundPanel.setPreferredSize(new Dimension(bookingPanel.getPreferredSize().width+100,bookingPanel.getPreferredSize().height+100));
        //table1.setOpaque(false);
        //bookingPanel.setOpaque(false);
        //bookingPanel.getViewport().setOpaque(false);

        bookingBackgroundPanel.setBorder(new LineBorder(Color.BLACK,10));
        fieldPanel.setBorder(new LineBorder(Color.BLACK,10));
        modifyBox.setBorder(new LineBorder(Color.BLACK,2));
        modifyButton.setBorder(new LineBorder(Color.BLACK,3));
        returnButton.setBorder(new LineBorder(Color.BLACK,3));
        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        codeField.setBorder(new LineBorder(Color.BLACK,2));
        passengerField.setBorder(new LineBorder(Color.BLACK,2));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        prenotazioniPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
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
        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                modifyButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                modifyButton.setBackground(null);
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
        passengerField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passengerField.getText().equals("Nome Passeggero")) {
                    passengerField.setForeground(Color.black);
                    passengerField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(passengerField.getText().equals("")) {
                    passengerField.setForeground(Color.gray);
                    passengerField.setText("Nome Passeggero");
                }
            }
        });
        codeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(codeField.getText().equals("Codice Volo Prenotato")) {
                    codeField.setForeground(Color.black);
                    codeField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(codeField.getText().equals("")) {
                    codeField.setForeground(Color.gray);
                    codeField.setText("Codice Volo Prenotato");
                }
            }
        });
    }
}
