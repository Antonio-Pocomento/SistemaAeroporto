package gui;

import controller.Controller;
import custom_exceptions.ModifyTableException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CercaVoloAdminGUI {
    private JPanel cercaVoloPanel;
    private JPanel contentPanel;
    private JButton salvaModificheButton;
    private JButton tornaIndietroButton;
    private JTable table1;
    private JButton cercaButton;
    private JTextField codeField;
    private JTextField aerArrivoField;
    private JTextField aerOrigineField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField companyField;
    private JTextField gateField;
    private JTextField delayField;
    private JTextField seatField;
    private JComboBox<String> stateBox;
    private JScrollPane tablePanel;
    private JPanel tableBackgroundPanel;
    private JPanel fieldsPanel;
    public final JFrame frame = new JFrame("Ricerca Voli Admin");
    private static final String DEFAULT_CODEFIELD_TEXT = "Codice Volo";
    private static final String DEFAULT_AERORIGINE_TEXT = "Aeroporto Di Origine";
    private static final String DEFAULT_AERARRIVO_TEXT = "Aeroporto Di Arrivo";
    private static final String DEFAULT_DATEFIELD_TEXT = "Data Volo (DD/MM/YYYY)";
    private static final String DEFAULT_TIMEFIELD_TEXT = "Orario Volo (HH:MM)";
    private static final String DEFAULT_COMPANYFIELD_TEXT = "Compagnia Aerea";
    private static final String DEFAULT_SEATFIELD_TEXT = "Posti";
    private static final String DEFAULT_DELAYFIELD_TEXT = "Ritardo Volo (HH:MM)";
    private static final String DEFAULT_GATEFIELD_TEXT = "Numero Gate";

    public CercaVoloAdminGUI(JFrame frameChiamante,Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,cercaVoloPanel);

        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        salvaModificheButton.setBorder(new LineBorder(Color.BLACK,3));
        tornaIndietroButton.setBorder(new LineBorder(Color.BLACK,3));
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

        TableSetter.setupFlightTable(table1, tablePanel, tableBackgroundPanel, controller.getFlightsAdminModel(),5);
        UtilFunctionsForGUI.addHoverEffect(salvaModificheButton);
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
                controller.cercaVoloAdmin(codeField.getText().trim(),seatField.getText().trim(),companyField.getText().trim(),aerOrigineField.getText().trim(),
                        aerArrivoField.getText().trim(), dateField.getText().trim(), timeField.getText().trim(), delayField.getText().trim(),
                        Objects.requireNonNull(stateBox.getSelectedItem()).toString().trim(), gateField.getText().trim());
            } catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Ricerca Volo");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Ricerca Volo", ex);
            }
        });
        salvaModificheButton.addActionListener(_ -> {
            try {
                ConfirmDialog conferma = new ConfirmDialog(null, "Sei sicuro di procedere con le modifiche effettuate?", "Conferma Modifiche");
                if (conferma.showDialog()) {
                    controller.salvaModificheDaTabella(table1);
                }
            } catch (ModifyTableException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Modifica Volo");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Modifica Volo", ex);
            }
        });
    }
}
