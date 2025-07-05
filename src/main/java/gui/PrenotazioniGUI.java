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
 * La classe {@code PrenotazioniGUI} rappresenta la schermata di visualizzazione per prenotare un volo
 */
public class PrenotazioniGUI {
    public final JFrame frame = new JFrame("Prenotazioni");
    private JPanel prenotazioniPanel;
    private JPanel contentPanel;
    private JScrollPane bookingPanel;
    private JTable table1;
    private JButton modifyButton;
    private JComboBox<String> modifyBox;
    private JButton returnButton;
    private JPanel bookingBackgroundPanel;
    private JTextField passengerField;
    private JTextField codeField;
    private JButton cercaButton;
    private JPanel fieldPanel;
    private static final String DEFAULT_PASSENGER_TEXT = "Nome Passeggero";
    private static final String DEFAULT_CODEFIELD_TEXT = "Codice Volo Prenotato";
    private static final String DEFAULT_MODIFYERROR_TEXT = "Errore Modifica Prenotazione";


    /**
     * Costruttore PrenotazioniGUI
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    public PrenotazioniGUI(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,prenotazioniPanel);

        TableSetter.setupReservationsTable(table1,bookingPanel,bookingBackgroundPanel,controller.getBookingTableModel(),5);

        fieldPanel.setBorder(new LineBorder(Color.BLACK,10));
        modifyBox.setBorder(new LineBorder(Color.BLACK,2));
        modifyButton.setBorder(new LineBorder(Color.BLACK,3));
        returnButton.setBorder(new LineBorder(Color.BLACK,3));
        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        codeField.setBorder(new LineBorder(Color.BLACK,2));
        passengerField.setBorder(new LineBorder(Color.BLACK,2));
        modifyButton.setEnabled(false);

        UtilFunctionsForGUI.addHoverEffect(returnButton);
        UtilFunctionsForGUI.addHoverEffect(modifyButton);
        UtilFunctionsForGUI.addHoverEffect(cercaButton);
        UtilFunctionsForGUI.addTextFieldPlaceholder(passengerField,DEFAULT_PASSENGER_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(codeField,DEFAULT_CODEFIELD_TEXT);
        UtilFunctionsForGUI.setupFrame(frame);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });
        returnButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
        cercaButton.addActionListener(_ -> {
            try {
                controller.cercaPrenotazione(codeField.getText().trim(),passengerField.getText().trim());
            } catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Ricerca Prenotazione");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Ricerca Prenotazione", ex);
            }
        });
        modifyButton.addActionListener(_ -> {
            String modifyText = Objects.requireNonNull(modifyBox.getSelectedItem()).toString().trim();
            if(modifyText.isBlank()) {
                ErrorPanel.showErrorDialog(null,"Seleziona come modificare la prenotazione.",DEFAULT_MODIFYERROR_TEXT);
                return;
            }
            int[] selectedRows = table1.getSelectedRows();
            for(int row : selectedRows) {
                if(table1.getValueAt(row,4).toString().trim().equals("Cancellata")) {
                    ErrorPanel.showErrorDialog(null,"Non puoi modificare una prenotazione che hai cancellato!",DEFAULT_MODIFYERROR_TEXT);
                    return;
                }
            }
            ConfirmDialog conferma = new ConfirmDialog(null, "Sei sicuro di procedere con la modifica?", modifyText + " Prenotazione");
            if (conferma.showDialog()) {
                for (int row : selectedRows) {
                    int numBiglietto = (int) table1.getValueAt(row, 0);
                    try {
                        controller.modificaPrenotazione(numBiglietto, modifyText+"ta");
                        table1.getModel().setValueAt(modifyText+"ta",table1.convertRowIndexToModel(row),4);
                    } catch (SQLException ex) {
                        ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto",DEFAULT_MODIFYERROR_TEXT);
                        Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, DEFAULT_MODIFYERROR_TEXT, ex);
                    }
                }
            }
        });
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Per evitare eventi multipli
                int selectedRow = table1.getSelectedRow();
                modifyButton.setEnabled(selectedRow != -1);
            }
        });
    }
}
