package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * La classe {@code CustomLuggageCellRenderer} si occupa di evidenziare nella GUI i bagagli smarriti
 */
public class CustomLuggageCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(SwingConstants.CENTER);
        c.setFont(table.getFont());

        if (column == 3 && value != null && value.toString().equals("Smarrito")) {
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        }

        return c;
    }
}
