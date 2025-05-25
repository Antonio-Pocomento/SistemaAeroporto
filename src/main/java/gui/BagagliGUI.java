package gui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BagagliGUI {
    public JFrame frame;
    private JPanel bagagliPanel;
    private JPanel contentPanel;
    private JButton button1;
    private JScrollPane tablePanel;
    private JTable table1;
    private JButton button2;

    BagagliGUI(Frame frameChiamante, Controller controller) throws IOException {
        frame = new JFrame("Voli");
        frame.setContentPane(bagagliPanel);
        bagagliPanel.setLayout(new OverlayLayout(bagagliPanel));
        contentPanel.setOpaque(false);

        table1.setModel(controller.getBagsTableModel());
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


        bagagliPanel.add(new BasicBackgroundPanel(ImageIO.read(new File("src/main/images/simpleBackground.jpg"))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
