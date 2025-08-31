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
 * La classe {@code BagagliAdminGUI} rappresenta la schermata di visualizzazione dei bagagli da parte dell'admin.
 *
 */
public class BagagliAdminGUI {
    private JPanel bagagliAdminPanel;
    private JPanel contentPanel;
    private JButton returnButton;
    private JTable table1;
    private JButton modifyButton;
    private JComboBox<String> modifyBox;
    private JButton searchButton;
    private JScrollPane tablePanel;
    private JPanel tableBackgroundPanel;
    private JTextField codeField;
    private JComboBox<String> stateBox;
    private JComboBox<String> typeBox;
    private JPanel buttonsBackgroundPanel;
    private JPanel modifyPanel;
    public final JFrame frame = new JFrame("Bagagli Admin");
    private static final String DEFAULT_CODEFIELD_TEXT = "Codice Bagaglio";

    /**
     * Costruttore BagagliAdminGUI
     *
     * @param frameChiamante il frame chiamante
     * @param controller     il controller
     */
    public BagagliAdminGUI(JFrame frameChiamante, Controller controller) {
        modifyPanel.setBackground(null);

        UtilFunctionsForGUI.setupLayoutAndBackground(frame,bagagliAdminPanel);

        modifyButton.setBorder(new LineBorder(Color.black,3));
        searchButton.setBorder(new LineBorder(Color.black,3));
        returnButton.setBorder(new LineBorder(Color.black,3));
        modifyBox.setBorder(new LineBorder(Color.black,2));
        stateBox.setBorder(new LineBorder(Color.black,2));
        typeBox.setBorder(new LineBorder(Color.black,2));
        codeField.setBorder(new LineBorder(Color.black,2));
        buttonsBackgroundPanel.setBorder(new LineBorder(Color.black,10));
        modifyButton.setEnabled(false);

        TableSetter.setupLuggageTable(table1,tablePanel,tableBackgroundPanel,controller.getBagsAdminTableModel(),5);
        UtilFunctionsForGUI.addHoverEffect(returnButton);
        UtilFunctionsForGUI.addHoverEffect(modifyButton);
        UtilFunctionsForGUI.addHoverEffect(searchButton);
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
        searchButton.addActionListener(_ -> {
            try {
                controller.cercaBagaglioAdmin(codeField.getText().trim(), Objects.requireNonNull(typeBox.getSelectedItem()).toString().trim(), Objects.requireNonNull(stateBox.getSelectedItem()).toString().trim());
            } catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Ricerca Bagaglio");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Ricerca Bagaglio", ex);
            }
        });
        modifyButton.addActionListener(_ -> {
            String modifyText = Objects.requireNonNull(modifyBox.getSelectedItem()).toString().trim();
            if(modifyText.isBlank()) {
                ErrorPanel.showErrorDialog(null,"Seleziona un'opzione valida per la modifica.","Seleziona un'opzione");
                return;
            }
            ConfirmDialog conferma = new ConfirmDialog(null, "Sei sicuro di procedere con le modifiche?", "Conferma Modifiche");
            if (conferma.showDialog()) {
                for (int row : table1.getSelectedRows()) {
                    String codice = (String) table1.getValueAt(row, 0);
                    try {
                        controller.modificaBagaglio(codice.trim(),modifyText);
                        table1.getModel().setValueAt(modifyText,table1.convertRowIndexToModel(row),3);
                    } catch (SQLException ex) {
                        ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Modifica Bagaglio");
                        Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Modifica Bagaglio", ex);
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
