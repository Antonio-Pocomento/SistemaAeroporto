package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * La classe {@code CustomFlightCellRenderer} si occupa di evidenziare nella GUI i voli in ritardo o Cancellati.
 */
class CustomFlightCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Imposta allineamento centrale
        setHorizontalAlignment(SwingConstants.CENTER);

        // Reset font di default
        c.setFont(table.getFont());

        // Controlla valore "ritardo" (colonna 7)
        if (column == 7 && value != null && !value.toString().equals("00:00")) {
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        }

        // Controlla valore "stato" (colonna 8)
        if (column == 8 && value != null) {
            String stato = value.toString();
            if (stato.equals("Cancellato") || stato.equals("In Ritardo")) {
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            }
        }

        return c;
    }
}
