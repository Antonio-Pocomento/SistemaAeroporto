package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class VoliAdminGUI {
    public JFrame frame;
    private JPanel voliAdminPanel;
    private JPanel contentPanel;
    private JScrollPane tablePanel;
    private JButton modifyButton;
    private JButton returnButton;
    private JTable table1;
    private JPanel tableBackgroundPanel;
    private JLabel modifyErrMessage;
    private JPanel modifyErrPanel;
    private JButton insertButton;
    private JButton cercaVoloButton;

    public VoliAdminGUI(JFrame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Voli");
        frame.setContentPane(voliAdminPanel);
        voliAdminPanel.setLayout(new OverlayLayout(voliAdminPanel));
        contentPanel.setOpaque(false);

        table1.setModel(controller.getFlightsAdminModel());
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 28));
        tablePanel.setViewportView(table1);
        table1.setOpaque(false);
        tablePanel.setOpaque(false);
        tablePanel.getViewport().setOpaque(false);

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

        voliAdminPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        modifyErrPanel.setBorder(new LineBorder(Color.black,2,false));
        insertButton.setBorder(new LineBorder(Color.black,3,false));
        modifyButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));
        cercaVoloButton.setBorder(new LineBorder(Color.black,3,false));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO modifiche nel DB
                modifyErrPanel.setVisible(true);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
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
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InserisciVoloGUI insertVolGUI = null;
                try {
                    insertVolGUI = new InserisciVoloGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                insertVolGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        cercaVoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaVoloAdminGUI cercVolAdmGUI = null;
                try {
                    cercVolAdmGUI = new CercaVoloAdminGUI(frame, controller);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cercVolAdmGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        cercaVoloButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cercaVoloButton.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cercaVoloButton.setBackground(null);
            }
        });

    }
}
