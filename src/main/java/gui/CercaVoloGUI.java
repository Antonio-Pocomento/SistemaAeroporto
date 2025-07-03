package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe {@code CercaVoloGUI}
 */
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
    private JComboBox<String> stateBox;
    private JScrollPane tablePanel;
    public final JFrame frame = new JFrame("Ricerca Voli");
    private static final String DEFAULT_CODEFIELD_TEXT = "Codice Volo";
    private static final String DEFAULT_AERORIGINE_TEXT = "Aeroporto Di Origine";
    private static final String DEFAULT_AERARRIVO_TEXT = "Aeroporto Di Arrivo";
    private static final String DEFAULT_DATEFIELD_TEXT = "Data Volo (DD/MM/YYYY)";
    private static final String DEFAULT_TIMEFIELD_TEXT = "Orario Volo (HH:MM)";
    private static final String DEFAULT_COMPANYFIELD_TEXT = "Compagnia Aerea";
    private static final String DEFAULT_SEATFIELD_TEXT = "Posti";
    private static final String DEFAULT_DELAYFIELD_TEXT = "Ritardo Volo (HH:MM)";
    private static final String DEFAULT_GATEFIELD_TEXT = "Numero Gate";

    /**
     * Costruttore della classe {@code CercaVoloGUI}
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    public CercaVoloGUI(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,cercaVoloPanel);

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
        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        prenotaButton.setBorder(new LineBorder(Color.BLACK,3));
        tornaIndietroButton.setBorder(new LineBorder(Color.BLACK,3));
        fieldsPanel.setBorder(new LineBorder(Color.black,10,false));
        TableSetter.setupFlightTable(table1, tablePanel, tableBackgroundPanel, controller.getFlightsModel(),5);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prenotaButton.setEnabled(false);

        UtilFunctionsForGUI.addHoverEffect(prenotaButton);
        UtilFunctionsForGUI.addHoverEffect(tornaIndietroButton);
        UtilFunctionsForGUI.addHoverEffect(cercaButton);
        UtilFunctionsForGUI.addTextFieldPlaceholder(codeField,DEFAULT_CODEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(aerArrivoField,DEFAULT_AERARRIVO_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(aerOrigineField,DEFAULT_AERORIGINE_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(dateField,DEFAULT_DATEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(timeField,DEFAULT_TIMEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(companyField,DEFAULT_COMPANYFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(gateField,DEFAULT_GATEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(seatField,DEFAULT_SEATFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(delayField,DEFAULT_DELAYFIELD_TEXT);

        UtilFunctionsForGUI.setupFrame(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        tornaIndietroButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
        cercaButton.addActionListener(_ -> {
            try {
                controller.cercaVolo(codeField.getText().trim(),seatField.getText().trim(),companyField.getText().trim(),aerOrigineField.getText().trim(),
                        aerArrivoField.getText().trim(), dateField.getText().trim(), timeField.getText().trim(), delayField.getText().trim(),
                        Objects.requireNonNull(stateBox.getSelectedItem()).toString().trim(), gateField.getText().trim());
            } catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Ricerca Volo");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Ricerca Volo", ex);
            }
        });
        prenotaButton.addActionListener(_ -> {
            if((int)table1.getValueAt(table1.getSelectedRow(), 1) <= 0)
            {
                ErrorPanel.showErrorDialog(null,"I posti disponibili per questo volo sono esauriti.","Errore Prenotazione");
            } else if (!table1.getValueAt(table1.getSelectedRow(), 3).equals("Napoli")) {
                ErrorPanel.showErrorDialog(null,"Non è possibile prenotarsi per voli in arrivo.","Errore Prenotazione");
            } else{
                CheckInGUI checkGUI = new CheckInGUI(frame, controller);
                controller.iniziaPrenotazione((String) table1.getValueAt(table1.getSelectedRow(), 0));
                checkGUI.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Per evitare eventi multipli
                int selectedRow = table1.getSelectedRow();
                prenotaButton.setEnabled(selectedRow != -1);
            }
        });
    }
}
