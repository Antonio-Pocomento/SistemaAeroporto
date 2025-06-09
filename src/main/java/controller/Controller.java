package controller;

import custom_exceptions.EmailAlreadyExistsException;
import custom_exceptions.UserAlreadyExistsException;
import custom_exceptions.UserNotFoundException;
import dao.UtenteDAO;
import dao.AmministratoreDAO;
import implementazioni_postgres_dao.UtenteImplementazionePostgresDAO;
import implementazioni_postgres_dao.AmministratoreImplementazionePostgresDAO;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {

    private Utente utenteAutenticato;
    AmministratoreDAO amministratoreDAO = new AmministratoreImplementazionePostgresDAO();
    String[] flightColumnNames = {
            "Codice", "Posti Disponibili", "Comp. Aerea", "Origine",
            "Destinazione", "Data", "Orario", "Ritardo", "Stato", "Numero Gate"
    };
    DefaultTableModel flightsAdminTableModel = new DefaultTableModel(flightColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0;
        }
    };

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



    public DefaultTableModel getFlightsModel() { return null; }
    public DefaultTableModel getFlightsAdmin() {
        try{
        amministratoreDAO.showFlights(flightsAdminTableModel,(Amministratore) utenteAutenticato);
        return flightsAdminTableModel;
        } catch (SQLException ex) {return null;}
    }
    public DefaultTableModel getBagsTableModel() { return bagsTableModel; }
    public DefaultTableModel getBagsAdminTableModel() { return bagsAdminTableModel; }
    public DefaultTableModel getBookingTableModel() { return bookingTableModel; }

    public void registraUtente(String nomeUtente, String email, char[] password) throws SQLException {
        UtenteDAO rDAO = new UtenteImplementazionePostgresDAO();
        if(!rDAO.esisteUtente(nomeUtente)) {
            if(!rDAO.esisteEmail(email)) {
                UtenteGenerico utente = new UtenteGenerico(nomeUtente, email, new String(password).trim()); //in memoria
                rDAO.registraUtente(utente); //su DB
            }
            else throw new EmailAlreadyExistsException("È già presente un account che usa questa email");
        }
        else
            throw new UserAlreadyExistsException("Nome Utente già in uso");
    }

    public int loginUtente(String login, char[] password) throws SQLException {
        UtenteDAO rDAO = new UtenteImplementazionePostgresDAO();
        Utente utente = rDAO.loginUtente(login, new String(password).trim());
        if(utente != null) { utenteAutenticato = utente; }

        if (utente instanceof Amministratore amministratore) {
            amministratoreDAO.addFlights(amministratore);
            return 1;
        } else if (utente instanceof UtenteGenerico) {
            return 0;
        } else {
            throw new UserNotFoundException("Utente non trovato");
        }
    }

    public void salvaModificheDaTabella(JTable table) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Amministratore admin = (Amministratore) utenteAutenticato;
        List<Volo> voli = admin.getVoliGestiti();

        for (int i = 0; i < model.getRowCount(); i++) {
            int codice = safeParseInteger(model.getValueAt(i, 0));
            Volo v = null;
            for (Volo volo : voli) {
                v = volo;
                if (v.getCodice() == codice) {
                    // aggiorna i campi del volo esistente
                    v.setData(LocalDate.parse(model.getValueAt(i, 5).toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    v.setOrario(LocalTime.parse(model.getValueAt(i, 6).toString()));
                    v.setRitardo(parseLocalTimeSafe(model.getValueAt(i, 7)));
                    v.setStato(StatoVolo.valueOf(model.getValueAt(i, 8).toString().replace(" ", "_").toUpperCase()));
                    v.setNumeroGate(null);

                    amministratoreDAO.aggiornaVolo(v); // salvi lo stesso oggetto aggiornato
                    break;
                }
            }
            amministratoreDAO.aggiornaVolo(v);

            for (int j = 0; j < voli.size(); j++) {
                if (voli.get(j).getCodice() == codice) {
                    voli.set(j, v);
                    break;
                }
            }
        }
    }

    public boolean canPressRegister(String nomeUtente, String email, char[] password, char[] confirmPassword) {
        return isNameValid(nomeUtente) && isEmailValid(email) && isPasswordValid(new String(password).trim(), new String(confirmPassword).trim());
    }

    public boolean isEmailValid(String email) {
        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && !email.isBlank() && email.matches(regexEmail);
    }
    public boolean isPasswordValid(String password) {
        return password != null && !password.isBlank() && password.length() >= 8;
    }
    private boolean isPasswordValid(String password, String confirmPassword) {
        return password != null && !password.isBlank() && password.length() >= 8 && password.equals(confirmPassword);
    }
    private boolean isNameValid(String name) {
        return name != null && !name.isBlank() && name.length() >= 3 && !name.equals("Nome Utente");
    }

    private Integer safeParseInteger(Object value) {
        if (value != null && !value.toString().isBlank()) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                System.err.println("Valore numerico non valido: " + value);
            }
        }
        return null;
    }

    private LocalTime parseLocalTimeSafe(Object value) {
        if (value == null) return null;
        String s = value.toString();
        if (s.isBlank()) return null;
        return LocalTime.parse(s);
    }
}
