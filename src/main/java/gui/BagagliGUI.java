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

public class BagagliGUI {
    public final JFrame frame = new JFrame("Bagagli");
    private JPanel bagagliPanel;
    private JPanel contentPanel;
    private JButton tornaIndietroButton;
    private JScrollPane tablePanel;
    private JTable table1;
    private JButton segnalaSmarrimentoButton;
    private JPanel tableBackgroundPanel;
    private JButton cercaButton;
    private JComboBox<String> typeBox;
    private JComboBox<String> stateBox;
    private JTextField codeField;
    private JPanel fieldsPanel;
    private static final String DEFAULT_CODEFIELD_TEXT = "Codice Bagaglio";

    BagagliGUI(Frame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,bagagliPanel);

        TableSetter.setupLuggageTable(table1,tablePanel, tableBackgroundPanel, controller.getBagsTableModel(), 5);
        fieldsPanel.setBorder(new LineBorder(Color.BLACK,10));
        cercaButton.setBorder(new LineBorder(Color.BLACK,3));
        tornaIndietroButton.setBorder(new LineBorder(Color.BLACK,3));
        segnalaSmarrimentoButton.setBorder(new LineBorder(Color.BLACK,3));
        typeBox.setBorder(new LineBorder(Color.black,2));
        stateBox.setBorder(new LineBorder(Color.black,2));
        codeField.setBorder(new LineBorder(Color.black,2));

        UtilFunctionsForGUI.addHoverEffect(cercaButton);
        UtilFunctionsForGUI.addHoverEffect(tornaIndietroButton);
        UtilFunctionsForGUI.addHoverEffect(segnalaSmarrimentoButton);
        UtilFunctionsForGUI.addTextFieldPlaceholder(codeField,DEFAULT_CODEFIELD_TEXT);

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
                controller.cercaBagaglio(codeField.getText().trim(), Objects.requireNonNull(typeBox.getSelectedItem()).toString().trim(), Objects.requireNonNull(stateBox.getSelectedItem()).toString().trim());
            } catch (SQLException ex) {
                ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Ricerca Bagaglio");
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Ricerca Bagaglio", ex);
            }
        });
        segnalaSmarrimentoButton.addActionListener(_ -> {
            for (int row : table1.getSelectedRows()) {
                String codice = (String) table1.getValueAt(row, 0);
                try {
                    controller.segnalaBagaglio(codice);
                    TableSetter.setupLuggageTable(table1,tablePanel,tableBackgroundPanel,controller.getBagsTableModel(),5);
                    controller.cercaBagaglio(codeField.getText().trim(), Objects.requireNonNull(typeBox.getSelectedItem()).toString().trim(), Objects.requireNonNull(stateBox.getSelectedItem()).toString().trim());
                } catch (SQLException ex) {
                    ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore Segnalazione Bagaglio");
                    Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Segnalazione Bagaglio", ex);
                }
            }
        });
    }
}
