package controller;

import dao.UtenteDAO;
import implementazioni_postgres_dao.UtenteImplementazionePostgresDAO;
import model.Utente;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

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

    DefaultTableModel bagsAdminTableModel = new DefaultTableModel(
            new Object[][] {
                    {false, "BG123", "Caricato"},
                    {false, "BG456", "Ritirabile"},
                    {false, "BG789", "Ritirabile"},
                    {false, "BG123", "Caricato"},
                    {false, "BG456", "Ritirabile"},
                    {false, "BG789", "Ritirabile"},
                    {false, "BG123", "Caricato"},
                    {false, "BG456", "Ritirabile"},
                    {false, "BG789", "Ritirabile"},
                    {false, "BG456", "Ritirabile"},
                    {false, "BG789", "Ritirabile"},

            },
            new Object[] {"Modifica", "Codice", "Stato"}
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
                    {"AZ123", "A","In Attesa"},
                    {"AZ132", "A","In Attesa"},
                    {"AZ125", "A","In Attesa"},
                    {"AZ123", "A","In Attesa"},
                    {"AZ132", "A","In Attesa"},
                    {"AZ125", "A","In Attesa"},
                    {"AZ123", "A","In Attesa"},
                    {"AZ132", "A","In Attesa"},
                    {"AZ125", "A", "In Attesa"},
            },
            new Object[] {"Codice Volo", "Nome Passeggero", "Stato Prenotazione"}
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel flightsAdminTableModel = new DefaultTableModel(
            new Object[][] {
                    {"AZ123", "1200", "ITALIAN AIRLINES", "Palermo", "Napoli", "12/04/2020", "12:55", "00:00", "Programmato", "1"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
                    {"LH456", "500", "Voli Italia", "Napoli", "Torino", "13/04/2020", "15:52", "00:30", "Programmato", "6"},
                    {"BA789", "999", "Italico", "Napoli", "Palermo", "11/04/2020", "10:34", "01:00", "Programmato", "2"},
            },
            new Object[] {"Codice", "Posti Disp.", "Compagnia Aerea", "Aer. Di Partenza", "Aer. Di Arrivo",
                    "Data", "Ora", "Ritardo", "Stato", "Gate"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0;
        }
    };

    public boolean isAdmin(String login) {
        if(login.equals("admin")) {
            return true;
        }
        return false;
    }

    public DefaultTableModel getFlightsModel() { return flightsTableModel; }
    public DefaultTableModel getFlightsAdminModel() { return flightsAdminTableModel; }
    public DefaultTableModel getBagsTableModel() { return bagsTableModel; }
    public DefaultTableModel getBagsAdminTableModel() { return bagsAdminTableModel; }
    public DefaultTableModel getBookingTableModel() { return bookingTableModel; }

    public void registraUtente(String nomeUtente, String email, String password) throws SQLException {
        Utente utente = new Utente(nomeUtente, email, password); //in memoria
        UtenteDAO rDAO = new UtenteImplementazionePostgresDAO();
        rDAO.registraUtente(utente); //su DB

    }
}
