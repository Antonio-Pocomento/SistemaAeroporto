package gui;

import controller.Controller;
import custom_exceptions.ModifyTableException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoliAdminGUI {
    public final JFrame frame = new JFrame("Voli Admin");
    private JPanel voliAdminPanel;
    private JScrollPane tablePanel;
    private JButton modifyButton;
    private JButton returnButton;
    private JTable table1;
    private JPanel tableBackgroundPanel;
    private JButton insertButton;
    private JButton cercaVoloButton;

    public VoliAdminGUI(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,voliAdminPanel);
        TableSetter.setupFlightTable(table1, tablePanel, tableBackgroundPanel, controller.getFlightsAdminModel(),10);
        insertButton.setBorder(new LineBorder(Color.black,3,false));
        modifyButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));
        cercaVoloButton.setBorder(new LineBorder(Color.black,3,false));
        UtilFunctionsForGUI.addHoverEffect(insertButton);
        UtilFunctionsForGUI.addHoverEffect(modifyButton);
        UtilFunctionsForGUI.addHoverEffect(returnButton);
        UtilFunctionsForGUI.addHoverEffect(cercaVoloButton);

        UtilFunctionsForGUI.setupFrame(frame);

        modifyButton.addActionListener(_ -> {
            try {
                controller.salvaModificheDaTabella(table1);
            } catch (ModifyTableException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Modifica Volo");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "ERRORE: Modifica Volo", ex);
            }
        });
        returnButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });

        insertButton.addActionListener(_ -> {
            InserisciVoloGUI insertVolGUI = new InserisciVoloGUI(frame, controller);
            insertVolGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
        cercaVoloButton.addActionListener(_ -> {
            CercaVoloAdminGUI cercaVolAdmGUI = new CercaVoloAdminGUI(frame, controller);
            cercaVolAdmGUI.frame.setVisible(true);
            frame.setVisible(false);
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                TableSetter.setupFlightTable(table1, tablePanel, tableBackgroundPanel, controller.getFlightsAdminModel(),10);
            }
        });
    }
}
