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
            for (int row : table1.getSelectedRows()) {
                String codice = (String) table1.getValueAt(row, 0);
                try {
                    controller.modificaBagaglio(codice.trim(),Objects.requireNonNull(modifyBox.getSelectedItem()).toString().trim());
                    // ??
                    TableSetter.setupLuggageTable(table1,tablePanel,tableBackgroundPanel,controller.getBagsAdminTableModel(),5);
                    controller.cercaBagaglioAdmin(codeField.getText().trim(), Objects.requireNonNull(typeBox.getSelectedItem()).toString().trim(), Objects.requireNonNull(stateBox.getSelectedItem()).toString().trim());
                } catch (SQLException ex) {
                    ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Modifica Bagaglio");
                    Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Modifica Bagaglio", ex);
                }
            }
        });
    }
}
