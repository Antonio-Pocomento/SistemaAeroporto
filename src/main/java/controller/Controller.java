package controller;

import javax.swing.table.DefaultTableModel;

public class Controller {

    DefaultTableModel flightsTableModel = new DefaultTableModel(
                new Object[][] {
                        {false, "AZ123", "1200", "ITALIAN AIRLINES", "Palermo", "Napoli", "12/04/2020", "12:55", "00:00", "Programmato", "1"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                        {false, "LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                        {false, "BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
    },
            new Object[] {"Prenota", "Codice", "Posti Disp.", "Compagnia Aerea", "Aer. Di Partenza", "Aer. Di Arrivo",
            "Data", "Ora", "Ritardo", "Stato", "Gate"}
        ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Boolean.class : String.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };

    DefaultTableModel bagsTableModel = new DefaultTableModel(
            new Object[][] {
                    {false, "BG123", "Caricato"},
                    {false, "BG456", "Ritirabile"},
                    {false, "BG789", "Ritirabile"},
            },
            new Object[] {"Segnala", "Codice", "Stato"}
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Boolean.class : String.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };

    DefaultTableModel bookingTableModel = new DefaultTableModel(
            new Object[][] {
                    {false, "BG123", "Caricato"},
                    {false, "BG456", "Ritirabile"},
                    {false, "BG789", "Ritirabile"},
            },
            new Object[] {"Segnala", "Codice", "Stato"}
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Boolean.class : String.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };

    public DefaultTableModel getFlightsModel() { return flightsTableModel; }
    public DefaultTableModel getBagsTableModel() { return bagsTableModel; }
}
