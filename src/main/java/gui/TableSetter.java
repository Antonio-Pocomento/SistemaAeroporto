package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.List;

/**
 * la classe {@code TableSetter} si occupa della creazione delle tabelle
 */
public class TableSetter {

    private static final String DEFAULT_FONT = "Times New Roman";

    /**
     * costruttore TableSetter
     */
    TableSetter(){}

    private static void genericSetter(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows){
        if(model != null){
            table.setModel(model);

            // Header
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);
            table.getTableHeader().setFont(new Font(DEFAULT_FONT, Font.BOLD, 28));

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
        else {
            // Crea modello con messaggio placeholder
            DefaultTableModel emptyModel = new DefaultTableModel(
                    new Object[][]{{"Nessun dato disponibile"}},
                    new Object[]{"Informazione"}
            );

            table.setModel(emptyModel);

            // Imposta font per header
            JTableHeader header = table.getTableHeader();
            header.setFont(new Font(DEFAULT_FONT, Font.BOLD, 28));
            header.setReorderingAllowed(false);
            header.setResizingAllowed(false);

            // Imposta font per le celle
            table.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 24));
            table.setRowHeight(30); // adatta l'altezza delle righe al font

            scrollPane.setViewportView(table);

            // Opacità per un look coerente
            table.setOpaque(false);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            // Bordo e dimensioni minime
            backgroundPanel.setBorder(new LineBorder(Color.gray, 5, false));
            scrollPane.setPreferredSize(new Dimension(500, 100));
            backgroundPanel.setPreferredSize(new Dimension(600, 150));
        }
    }

    /**
     * imposta la tabella volo
     *
     * @param table           la tabella
     * @param scrollPane      lo scroll pane
     * @param backgroundPanel il background panel
     * @param model           il model
     * @param maxRows         il massimo di righe
     */
    public static void setupFlightTable(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows) {
        genericSetter(table, scrollPane, backgroundPanel, model, maxRows);
        if(model != null)
            setFlightSorter(table, model);

        TableColumnModel columnModel = table.getColumnModel();
        CustomFlightCellRenderer customRenderer = new CustomFlightCellRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }
    }

    /**
     * Imposta la tabella delle prenotazioni.
     *
     * @param table           la tabella
     * @param scrollPane      lo scroll pane
     * @param backgroundPanel il background panel
     * @param model           il model
     * @param maxRows         il massimo di righe
     */
    public static void setupReservationsTable(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows) {
        genericSetter(table, scrollPane, backgroundPanel, model, maxRows);
        if(model != null)
            setReservationSorter(table, model);

        TableColumnModel columnModel = table.getColumnModel();
        CustomReservationCellRenderer customRenderer = new CustomReservationCellRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }
    }

    /**
     * Imposta la tabella per i bagagli.
     *
     * @param table           la tabella
     * @param scrollPane      lo scroll pane
     * @param backgroundPanel il background panel
     * @param model           il model
     * @param maxRows         il massimo di righe
     */
    public static void setupLuggageTable(JTable table, JScrollPane scrollPane, JPanel backgroundPanel, DefaultTableModel model, int maxRows) {
        genericSetter(table, scrollPane, backgroundPanel, model, maxRows);
        if(model != null)
            setLuggageSorter(table, model);

        TableColumnModel columnModel = table.getColumnModel();
        CustomLuggageCellRenderer customRenderer = new CustomLuggageCellRenderer();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }
    }

    private static void setFlightSorter(JTable table, DefaultTableModel model)
    {
        configureSorter(table, model, List.of(1, 9), Map.of(5, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                Map.of(6, DateTimeFormatter.ofPattern("HH:mm"), 7, DateTimeFormatter.ofPattern("HH:mm")));
    }

    private static void setReservationSorter(JTable table, DefaultTableModel model)
    {
        configureSorter(table, model, List.of(0), null, null);
    }

    private static void setLuggageSorter(JTable table, DefaultTableModel model)
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private static void configureSorter(JTable table, DefaultTableModel model, List<Integer> intColumns, Map<Integer, DateTimeFormatter> dateColumns,
                                        Map<Integer, DateTimeFormatter> timeColumns)
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Comparator for Integer columns stored as String
        Comparator<Object> intComparator = (o1, o2) -> {
            int n1 = (o1 == null || o1.toString().isEmpty()) ? 0 : Integer.parseInt(o1.toString());
            int n2 = (o2 == null || o2.toString().isEmpty()) ? 0 : Integer.parseInt(o2.toString());
            return Integer.compare(n1, n2);
        };

        // Set integer comparators
        if (intColumns != null) {
            for (int col : intColumns) {
                sorter.setComparator(col, intComparator);
            }
        }

        // Set date comparators
        if (dateColumns != null) {
            for (Map.Entry<Integer, DateTimeFormatter> entry : dateColumns.entrySet()) {
                Comparator<Object> dateComparator = (s1, s2) -> {
                    LocalDate d1 = LocalDate.parse(s1.toString(), entry.getValue());
                    LocalDate d2 = LocalDate.parse(s2.toString(), entry.getValue());
                    return d1.compareTo(d2);
                };
                sorter.setComparator(entry.getKey(), dateComparator);
            }
        }

        // Set time comparators
        if (timeColumns != null) {
            for (Map.Entry<Integer, DateTimeFormatter> entry : timeColumns.entrySet()) {
                Comparator<Object> timeComparator = (s1, s2) -> {
                    if (s1 == null || s1.toString().isEmpty()) return -1;
                    if (s2 == null || s2.toString().isEmpty()) return 1;
                    LocalTime t1 = LocalTime.parse(s1.toString(), entry.getValue());
                    LocalTime t2 = LocalTime.parse(s2.toString(), entry.getValue());
                    return t1.compareTo(t2);
                };
                sorter.setComparator(entry.getKey(), timeComparator);
            }
        }

        // Default sort by column 0 ascending
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        sorter.sort();
    }
}
