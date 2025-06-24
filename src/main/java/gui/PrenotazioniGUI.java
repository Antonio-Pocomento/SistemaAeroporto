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
            for (int row : table1.getSelectedRows()) {
                int numBiglietto = (int) table1.getValueAt(row, 0);
                try {
                    controller.modificaPrenotazione(numBiglietto, Objects.requireNonNull(modifyBox.getSelectedItem()).toString().trim());
                } catch (SQLException ex) {
                    ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Modifica Prenotazione");
                    Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "ERRORE: Modifica Prenotazione", ex);
                }
            }
        });
    }
}
