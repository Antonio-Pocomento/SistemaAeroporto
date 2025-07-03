package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * La classe {@code CustomReservationCellRenderer} si occupa di evidenziare nella GUI le prenotazioni cancellate.
 */
public class CustomReservationCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(SwingConstants.CENTER);
        c.setFont(table.getFont());

        if (column == 4 && value != null && value.toString().equals("Cancellata")) {
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        }

        return c;
    }
}
