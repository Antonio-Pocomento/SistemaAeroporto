package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class CheckInGUI {
    public JFrame frame;
    private JPanel checkInPanel;
    private JPanel contentPanel;
    private JPanel passengerPanel;
    private JPanel luggagePanel;
    private JLabel nameIcon;
    private JLabel secondNameIcon;
    private JLabel surnameIcon;
    private JLabel cfIcon;
    private JTable table1;
    private JButton insertButton;
    private JComboBox typeBox;
    private JScrollPane tablePanel;
    private JPanel tableBackgroundPanel;
    private JTextField nameField;
    private JTextField secondNameField;
    private JTextField surnameField;
    private JTextField cfField;
    private JButton removeButton;
    private JButton confermaButton;
    private JButton tornaIndietroButton;

    public CheckInGUI(JFrame frameChiamante, Controller controller) throws IOException  {
        frame = new JFrame("Voli");
        frame.setContentPane(checkInPanel);
        checkInPanel.setLayout(new OverlayLayout(checkInPanel));
        contentPanel.setOpaque(false);
        checkInPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
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

        String[] columnNames = {"Tipo"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table1.setModel(model);

        tablePanel.setPreferredSize(new Dimension(1500,table1.getRowHeight()*4));
        tableBackgroundPanel.setPreferredSize(new Dimension(tablePanel.getPreferredSize().width+100,tablePanel.getPreferredSize().height+100));

        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 28));
        tablePanel.setViewportView(table1);
        table1.setOpaque(false);
        tablePanel.setOpaque(false);
        tablePanel.getViewport().setOpaque(false);

        nameField.setBorder(new LineBorder(Color.black,2));
        secondNameField.setBorder(new LineBorder(Color.black,2));
        surnameField.setBorder(new LineBorder(Color.black,2));
        cfField.setBorder(new LineBorder(Color.black,2));
        typeBox.setBorder(new LineBorder(Color.black,2));
        passengerPanel.setBorder(new LineBorder(Color.black,10));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        tableBackgroundPanel.setBorder(new LineBorder(Color.black,10,false));

        DefaultCellEditor editor = new DefaultCellEditor(new JTextField());
        editor.getComponent().setFont(table1.getFont());
        table1.setDefaultEditor(Object.class, editor);

        removeButton.setBorder(new LineBorder(Color.black,3,false));
        confermaButton.setBorder(new LineBorder(Color.black,3,false));
        tornaIndietroButton.setBorder(new LineBorder(Color.black,3,false));
        insertButton.setBorder(new LineBorder(Color.black,3,false));

        cfIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/idIcon.png"))));
        nameIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userNameIcon.png"))));
        secondNameIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userNameIcon.png"))));
        surnameIcon.setIcon(new ImageIcon(ImageIO.read(new File("src/main/images/userNameIcon.png"))));
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{typeBox.getSelectedItem()});
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table1.getRowCount()>0 && table1.getSelectedRow()!=-1){
                    model.removeRow(table1.getSelectedRow());
                }

            }
        });
        confermaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                confermaButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                confermaButton.setBackground(null);
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
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                removeButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                removeButton.setBackground(null);
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
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(nameField.getText().equals("Nome")){
                    nameField.setText("");
                    nameField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(nameField.getText().equals("")){
                    nameField.setText("Nome");
                    nameField.setForeground(Color.gray);
                }
            }
        });
        secondNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(secondNameField.getText().equals("Secondo Nome")){
                    secondNameField.setText("");
                    secondNameField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(secondNameField.getText().equals("")){
                    secondNameField.setText("Secondo Nome");
                    secondNameField.setForeground(Color.gray);
                }
            }
        });
        surnameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(surnameField.getText().equals("Cognome")){
                    surnameField.setText("");
                    surnameField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(surnameField.getText().equals("")){
                    surnameField.setText("Cognome");
                    surnameField.setForeground(Color.gray);
                }
            }
        });
        cfField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(cfField.getText().equals("Codice Fiscale")){
                    cfField.setText("");
                    cfField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(cfField.getText().equals("")){
                    cfField.setText("Codice Fiscale");
                    cfField.setForeground(Color.gray);
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
    }
}
