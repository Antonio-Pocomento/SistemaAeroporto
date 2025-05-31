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

public class BagagliAdminGUI {
    private JPanel bagagliAdminPanel;
    private JPanel contentPanel;
    private JButton returnButton;
    private JTable table1;
    private JButton modifyButton;
    private JComboBox modifyBox;
    private JButton searchButton;
    private JScrollPane tablePanel;
    private JPanel tableBackgroundPanel;
    private JTextField codeField;
    private JComboBox stateBox;
    private JComboBox typeBox;
    private JPanel buttonsBackgroundPanel;
    private JPanel modifyPanel;
    public JFrame frame;

    public BagagliAdminGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Login");
        frame.setContentPane(bagagliAdminPanel);

        bagagliAdminPanel.setLayout(new OverlayLayout(bagagliAdminPanel));
        contentPanel.setOpaque(false);
        bagagliAdminPanel.add(contentPanel);
        bagagliAdminPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        modifyButton.setBorder(new LineBorder(Color.black,3));
        searchButton.setBorder(new LineBorder(Color.black,3));
        returnButton.setBorder(new LineBorder(Color.black,3));
        modifyBox.setBorder(new LineBorder(Color.black,2));
        stateBox.setBorder(new LineBorder(Color.black,2));
        typeBox.setBorder(new LineBorder(Color.black,2));
        codeField.setBorder(new LineBorder(Color.black,2));
        buttonsBackgroundPanel.setBorder(new LineBorder(Color.black,10));

        table1.setModel(controller.getBagsAdminTableModel());
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 28));
        tablePanel.setViewportView(table1);
        table1.setOpaque(false);
        tablePanel.setOpaque(false);
        tablePanel.getViewport().setOpaque(false);
        modifyPanel.setBackground(null);
        modifyPanel.setOpaque(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        tableBackgroundPanel.setBorder(new LineBorder(Color.black,10,false));

        tablePanel.setPreferredSize(new Dimension(2100,table1.getRowHeight()*Math.min(table1.getRowCount(), 6)));
        tableBackgroundPanel.setPreferredSize(new Dimension(tablePanel.getPreferredSize().width+100,tablePanel.getPreferredSize().height+100));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                searchButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                searchButton.setBackground(null);
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
        codeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(codeField.getText().equals("Codice Bagaglio")){
                    codeField.setText("");
                    codeField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(codeField.getText().equals("")){
                    codeField.setText("Codice Bagaglio");
                    codeField.setForeground(Color.gray);
                }
            }
        });
    }
}
