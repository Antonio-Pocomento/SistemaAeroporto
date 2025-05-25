package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
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
    private JPanel modifyPanel;

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
        modifyPanel.setOpaque(false);
        modifyBox.addItem("Conferma");
        modifyBox.addItem("Cancella");

        table1.setModel(controller.getBookingTableModel());
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 28));
        bookingPanel.setViewportView(table1);
        bookingPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-100, (Math.min(table1.getRowCount(), 5)+1) * table1.getRowHeight()));
        table1.setOpaque(false);
        bookingPanel.setOpaque(false);
        bookingPanel.getViewport().setOpaque(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }


        prenotazioniPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
    }
}
