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

public class BagagliGUI {
    public JFrame frame;
    private JPanel bagagliPanel;
    private JPanel contentPanel;
    private JButton tornaIndietroButton;
    private JScrollPane tablePanel;
    private JTable table1;
    private JButton segnalaSmarrimentoButton;
    private JPanel tableBackgroundPanel;
    private JButton cercaButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField codeField;
    private JPanel fieldsPanel;

    BagagliGUI(Frame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Voli");
        frame.setContentPane(bagagliPanel);
        bagagliPanel.setLayout(new OverlayLayout(bagagliPanel));
        contentPanel.setOpaque(false);

        table1.setModel(controller.getBagsAdminTableModel());
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 28));
        tablePanel.setViewportView(table1);
        tablePanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-100, (Math.min(table1.getRowCount(), 5)+1) * table1.getRowHeight()));
        table1.setOpaque(false);
        tablePanel.setOpaque(false);
        tablePanel.getViewport().setOpaque(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        tablePanel.setPreferredSize(new Dimension(2100,table1.getRowHeight()*Math.min(table1.getRowCount(), 6)));
        tableBackgroundPanel.setPreferredSize(new Dimension(tablePanel.getPreferredSize().width+100,tablePanel.getPreferredSize().height+100));

        tableBackgroundPanel.setBorder(new LineBorder(Color.BLACK,10));
        fieldsPanel.setBorder(new LineBorder(Color.BLACK,10));
        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        tornaIndietroButton.setBorder(new LineBorder(Color.BLACK,3));
        segnalaSmarrimentoButton.setBorder(new LineBorder(Color.BLACK,3));


        bagagliPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
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
        segnalaSmarrimentoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                segnalaSmarrimentoButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                segnalaSmarrimentoButton.setBackground(null);
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
