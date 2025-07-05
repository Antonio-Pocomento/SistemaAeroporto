package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * La classe {@code VoliGUI} rappresenta la schermata di visualizzazione di tutti i voli disponibili da parte dell'utente
 */
public class VoliGUI {
    public final JFrame frame = new JFrame("Voli");
    private JPanel voliPanel;
    private JButton prenotaButton;
    private JScrollPane tablePanel;
    private JTable table1;
    private JButton returnButton;
    private JButton searchButton;
    private JPanel tableBackgroundPanel;
    private static final String DEFAULT_ERROR_TEXT = "Errore Prenotazione";

    /**
     * Costruttore VoliGUI
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    VoliGUI(Frame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,voliPanel);
        prenotaButton.setBorder(new LineBorder(Color.black,3,false));
        returnButton.setBorder(new LineBorder(Color.black,3,false));
        searchButton.setBorder(new LineBorder(Color.black,3,false));
        TableSetter.setupFlightTable(table1, tablePanel, tableBackgroundPanel, controller.getFlightsModel(),10);
        UtilFunctionsForGUI.addHoverEffect(searchButton);
        UtilFunctionsForGUI.addHoverEffect(returnButton);
        UtilFunctionsForGUI.addHoverEffect(prenotaButton);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prenotaButton.setEnabled(false);
        UtilFunctionsForGUI.setupFrame(frame);

        prenotaButton.addActionListener(_ -> {
            if((int)table1.getValueAt(table1.getSelectedRow(), 1) <= 0)
            {
                ErrorPanel.showErrorDialog(null,"I posti disponibili per questo volo sono esauriti.",DEFAULT_ERROR_TEXT);
            } else if (!table1.getValueAt(table1.getSelectedRow(), 3).equals("Napoli")) {
                ErrorPanel.showErrorDialog(null,"Non è possibile prenotarsi per voli in arrivo.",DEFAULT_ERROR_TEXT);
            } else if(table1.getValueAt(table1.getSelectedRow(), 8).equals("Cancellato") ||
                    table1.getValueAt(table1.getSelectedRow(), 8).equals("Decollato")) {
                ErrorPanel.showErrorDialog(null,"Non è possibile prenotarsi per voli cancellati o decollati.",DEFAULT_ERROR_TEXT);
            } else {
                    CheckInGUI checkGUI = new CheckInGUI(frame, controller);
                    controller.iniziaPrenotazione((String) table1.getValueAt(table1.getSelectedRow(), 0));
                    checkGUI.frame.setVisible(true);
                    frame.setVisible(false);
            }
        });
        searchButton.addActionListener(_ -> {
            CercaVoloGUI cercaGUI = new CercaVoloGUI(frame, controller);
            cercaGUI.frame.setVisible(true);
            frame.setVisible(false);
        });
        returnButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });

        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Per evitare eventi multipli
                int selectedRow = table1.getSelectedRow();
                prenotaButton.setEnabled(selectedRow != -1);
            }
        });
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                TableSetter.setupFlightTable(table1, tablePanel, tableBackgroundPanel, controller.getFlightsModel(),10);
            }
        });
    }
}
