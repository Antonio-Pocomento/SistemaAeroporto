package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InserisciVoloGUI {
    private JPanel insertFlightPanel;
    private JPanel contentPanel;
    private JTextField codeField;
    private JTextField aerOrigineField;
    private JTextField aerArrivoField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField companyField;
    private JTextField seatField;
    private JTextField gateField;
    private JButton insertButton;
    private JButton returnButton;
    private JPanel fieldsPanel;
    public final JFrame frame = new JFrame("Inserisci Volo");
    private static final String DEFAULT_CODEFIELD_TEXT = "Codice Volo";
    private static final String DEFAULT_AERARRIVOFIELD_TEXT = "Aeroporto Di Arrivo";
    private static final String DEFAULT_AERORIGINEFIELD_TEXT = "Aeroporto Di Origine";
    private static final String DEFAULT_DATEFIELD_TEXT = "Data Volo (DD/MM/YYYY)";
    private static final String DEFAULT_TIMEFIELD_TEXT = "Orario Volo (HH:MM)";
    private static final String DEFAULT_COMPANYFIELD_TEXT = "Compagnia Aerea";
    private static final String DEFAULT_SEATFIELD_TEXT = "Posti";
    private static final String DEFAULT_GATEFIELD_TEXT = "Numero Gate";

    public InserisciVoloGUI(Frame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,insertFlightPanel);

        insertButton.setBorder(new LineBorder(Color.black,3));
        returnButton.setBorder(new LineBorder(Color.black,3));

        Map<JTextField, String> fields = new HashMap<>();
        fields.put(codeField, DEFAULT_CODEFIELD_TEXT);
        fields.put(aerArrivoField, DEFAULT_AERARRIVOFIELD_TEXT);
        fields.put(aerOrigineField, DEFAULT_AERORIGINEFIELD_TEXT);
        fields.put(dateField, DEFAULT_DATEFIELD_TEXT);
        fields.put(timeField, DEFAULT_TIMEFIELD_TEXT);
        fields.put(companyField, DEFAULT_COMPANYFIELD_TEXT);
        fields.put(seatField, DEFAULT_SEATFIELD_TEXT);
        Set<JTextField> optional = new HashSet<>();
        optional.add(gateField);

        fieldsPanel.setBorder(new LineBorder(Color.black,10));
        aerArrivoField.setBorder(new LineBorder(Color.BLACK,2,false));
        aerOrigineField.setBorder(new LineBorder(Color.BLACK,2,false));
        dateField.setBorder(new LineBorder(Color.BLACK,2,false));
        timeField.setBorder(new LineBorder(Color.BLACK,2,false));
        companyField.setBorder(new LineBorder(Color.BLACK,2,false));
        gateField.setBorder(new LineBorder(Color.BLACK,2,false));
        seatField.setBorder(new LineBorder(Color.BLACK,2,false));
        codeField.setBorder(new LineBorder(Color.BLACK,2,false));

        UtilFunctionsForGUI.addTextFieldPlaceholder(codeField, DEFAULT_CODEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(aerArrivoField, DEFAULT_AERARRIVOFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(aerOrigineField, DEFAULT_AERORIGINEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(dateField, DEFAULT_DATEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(timeField, DEFAULT_TIMEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(companyField, DEFAULT_COMPANYFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(gateField, DEFAULT_GATEFIELD_TEXT);
        UtilFunctionsForGUI.addTextFieldPlaceholder(seatField, DEFAULT_SEATFIELD_TEXT);
        UtilFunctionsForGUI.addHoverEffect(insertButton);
        UtilFunctionsForGUI.addHoverEffect(returnButton);

        FormHelper.bindButtonToTextFields(insertButton,fields,optional);
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
        insertButton.addActionListener(_ -> {
            try {
                ConfirmDialog conferma = new ConfirmDialog(null, "Sei sicuro di aggiungere questo volo?", "Conferma Inserimento");
                if (conferma.showDialog()) {
                    controller.inserisciVolo(codeField.getText().trim(),seatField.getText().trim(),companyField.getText().trim(),aerOrigineField.getText().trim(),
                            aerArrivoField.getText().trim(),dateField.getText().trim(),timeField.getText().trim(),gateField.getText().trim());
                }
            } catch (Exception ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Inserimento");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Inserimento Volo", ex);
            }
        });
    }
}
