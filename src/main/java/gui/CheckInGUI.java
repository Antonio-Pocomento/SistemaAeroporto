package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckInGUI {
    public final JFrame frame = new JFrame("CheckIn");
    private JPanel checkInPanel;
    private JPanel contentPanel;
    private JPanel passengerPanel;
    private JLabel nameIcon;
    private JLabel secondNameIcon;
    private JLabel surnameIcon;
    private JLabel cfIcon;
    private JTable table1;
    private JButton insertButton;
    private JComboBox<String> typeBox;
    private JScrollPane tablePanel;
    private JPanel tableBackgroundPanel;
    private JTextField nameField;
    private JTextField secondNameField;
    private JTextField surnameField;
    private JTextField cfField;
    private JButton removeButton;
    private JButton confermaButton;
    private JButton tornaIndietroButton;
    private static final String USER_ICON_PATH = "src/main/images/userNameIcon.png";

    public CheckInGUI(JFrame frameChiamante, Controller controller) {
        UtilFunctionsForGUI.setupLayoutAndBackground(frame,checkInPanel);

        cfIcon.setIcon(ImageLoader.loadIcon("src/main/images/idIcon.png"));
        nameIcon.setIcon(ImageLoader.loadIcon(USER_ICON_PATH));
        secondNameIcon.setIcon(ImageLoader.loadIcon(USER_ICON_PATH));
        surnameIcon.setIcon(ImageLoader.loadIcon(USER_ICON_PATH));

        nameField.setBorder(new LineBorder(Color.black,2));
        secondNameField.setBorder(new LineBorder(Color.black,2));
        surnameField.setBorder(new LineBorder(Color.black,2));
        cfField.setBorder(new LineBorder(Color.black,2));
        typeBox.setBorder(new LineBorder(Color.black,2));
        passengerPanel.setBorder(new LineBorder(Color.black,10));
        removeButton.setBorder(new LineBorder(Color.black,3,false));
        confermaButton.setBorder(new LineBorder(Color.black,3,false));
        tornaIndietroButton.setBorder(new LineBorder(Color.black,3,false));
        insertButton.setBorder(new LineBorder(Color.black,3,false));
        removeButton.setEnabled(false);
        UtilFunctionsForGUI.disallowSpaces(cfField);

        String[] columnNames = {"Tipo"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table1.setModel(model);
        tablePanel.setPreferredSize(new Dimension(1500,table1.getRowHeight()*4));
        tableBackgroundPanel.setPreferredSize(new Dimension(tablePanel.getPreferredSize().width+100,tablePanel.getPreferredSize().height+100));
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 28));
        tablePanel.setViewportView(table1);
        table1.setOpaque(false);
        tablePanel.setOpaque(false);
        tablePanel.getViewport().setOpaque(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        tableBackgroundPanel.setBorder(new LineBorder(Color.black,10,false));

        DefaultCellEditor editor = new DefaultCellEditor(new JTextField());
        editor.getComponent().setFont(table1.getFont());
        table1.setDefaultEditor(Object.class, editor);

        UtilFunctionsForGUI.addHoverEffect(insertButton);
        UtilFunctionsForGUI.addHoverEffect(confermaButton);
        UtilFunctionsForGUI.addHoverEffect(tornaIndietroButton);
        UtilFunctionsForGUI.addHoverEffect(removeButton);
        UtilFunctionsForGUI.addTextFieldPlaceholder(cfField, "Codice Fiscale");
        UtilFunctionsForGUI.addTextFieldPlaceholder(nameField, "Nome");
        UtilFunctionsForGUI.addTextFieldPlaceholder(surnameField, "Cognome");
        UtilFunctionsForGUI.addTextFieldPlaceholder(secondNameField, "Secondo Nome");
        confermaButton.setEnabled(false);

        Map<JTextField, String> fields = new HashMap<>();
        fields.put(nameField, "Nome");
        fields.put(surnameField, "Cognome");
        fields.put(cfField, "Codice Fiscale");
        Set<JTextField> optional = new HashSet<>();
        optional.add(secondNameField);
        FormHelper.bindButtonToTextFields(confermaButton,fields,optional);

        UtilFunctionsForGUI.setupFrame(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                contentPanel.requestFocusInWindow();
            }
        });

        insertButton.addActionListener(_ -> model.addRow(new Object[]{typeBox.getSelectedItem()}));
        removeButton.addActionListener(_ -> {
            if (table1.getRowCount() > 0 && table1.getSelectedRow() != -1) {
                int[] selectedRows = table1.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    model.removeRow(selectedRows[i]);
                }
            }
        });
        tornaIndietroButton.addActionListener(_ -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });
        confermaButton.addActionListener(_ -> {
            ConfirmDialog conferma = new ConfirmDialog(null, "Sei sicuro di procedere con la prenotazione?", "Conferma Prenotazione");
            if (conferma.showDialog()) {
                try {
                    controller.confermaPrenotazione(nameField.getText().trim(), secondNameField.getText().trim(), surnameField.getText().trim(), cfField.getText().trim(), table1);
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(1000, 100));
                    JLabel label = new JLabel("Prenotazione avvenuta con successo!", SwingConstants.CENTER);
                    label.setFont(new Font("Times New Roman", Font.BOLD, 36));
                    panel.add(label);
                    JOptionPane.showMessageDialog(null, panel, "Prenotazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ErrorPanel.showErrorDialog(null,"Qualcosa è andato storto","Errore CheckIn");
                    Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore CheckIn", ex);
                }
            }
        });
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Per evitare eventi multipli
                int selectedRow = table1.getSelectedRow();
                removeButton.setEnabled(selectedRow != -1);
            }
        });
    }
}
