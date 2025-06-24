package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class TableSetter {

    TableSetter(){}

    private static void genericSetter(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows){
        table.setModel(model);

        // Header
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 28));

        scrollPane.setViewportView(table);
        table.setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Bordo pannello di sfondo
        backgroundPanel.setBorder(new LineBorder(Color.black, 10, false));

        // Editor di default con font coerente
        DefaultCellEditor editor = new DefaultCellEditor(new JTextField());
        editor.getComponent().setFont(table.getFont());
        table.setDefaultEditor(Object.class, editor);

        // Dimensioni preferite per pannello e scroll
        int visibleRows = Math.min(table.getRowCount(), maxRows);
        scrollPane.setPreferredSize(new Dimension(2100, table.getRowHeight() * (visibleRows + 1)));
        backgroundPanel.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width + 100, scrollPane.getPreferredSize().height + 100));
    }

    private static void setFlightSorter(JTable table, DefaultTableModel model)
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

        // Comparator for Integer columns stored as String
        Comparator<Object> intComparator = (o1, o2) -> {
            int n1 = (o1 == null || o1.toString().isEmpty()) ? 0 : Integer.parseInt(o1.toString());
            int n2 = (o2 == null || o2.toString().isEmpty()) ? 0 : Integer.parseInt(o2.toString());
            return Integer.compare(n1, n2);
        };

        // Comparator for LocalDate stored as String
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Comparator<Object> dateComparator = (s1, s2) -> {
            LocalDate d1 = LocalDate.parse(s1.toString(), dateFormatter);
            LocalDate d2 = LocalDate.parse(s2.toString(), dateFormatter);
            return d1.compareTo(d2);
        };

        // Comparator for LocalTime stored as String with null/empty checks
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Comparator<Object> timeComparator = (s1, s2) -> {
            if (s1 == null || s1.toString().isEmpty()) return -1;
            if (s2 == null || s2.toString().isEmpty()) return 1;

            LocalTime t1 = LocalTime.parse(s1.toString(), timeFormatter);
            LocalTime t2 = LocalTime.parse(s2.toString(), timeFormatter);
            return t1.compareTo(t2);
        };

        sorter.setComparator(1, intComparator);
        sorter.setComparator(5, dateComparator);
        sorter.setComparator(6, timeComparator);
        sorter.setComparator(7, timeComparator);
        sorter.setComparator(9, intComparator);

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    public static void setupFlightTable(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows) {
        genericSetter(table, scrollPane, backgroundPanel, model, maxRows);
        setFlightSorter(table, model);

        TableColumnModel columnModel = table.getColumnModel();
        CustomFlightCellRenderer customRenderer = new CustomFlightCellRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }
    }

    public static void setupReservationsTable(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows) {
        genericSetter(table, scrollPane, backgroundPanel, model, maxRows);

        TableColumnModel columnModel = table.getColumnModel();
        SimpleCenteredCellRenderer customRenderer = new SimpleCenteredCellRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }
    }

    public static void setupLuggageTable(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows) {
        genericSetter(table, scrollPane, backgroundPanel, model, maxRows);

        TableColumnModel columnModel = table.getColumnModel();
        CustomLuggageCellRenderer customRenderer = new CustomLuggageCellRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }
    }
}
