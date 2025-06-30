package controller;

import custom_exceptions.EmailAlreadyExistsException;
import custom_exceptions.ModifyTableException;
import custom_exceptions.UserAlreadyExistsException;
import custom_exceptions.UserNotFoundException;
import dao.UtenteDAO;
import dao.AmministratoreDAO;
import dao.UtenteGenericoDAO;
import gui.LoginGUI;
import implementazioni_postgres_dao.UtenteGenericoImplementazionePostgresDAO;
import implementazioni_postgres_dao.UtenteImplementazionePostgresDAO;
import implementazioni_postgres_dao.AmministratoreImplementazionePostgresDAO;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private Utente utenteAutenticato;
    UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();
    AmministratoreDAO amministratoreDAO = new AmministratoreImplementazionePostgresDAO();
    UtenteGenericoDAO utenteGenericoDAO = new UtenteGenericoImplementazionePostgresDAO();
    String codiceVoloDaPrenotare;
    private static final Random random = new Random();
    private static final char[] sedili = {'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

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
    DefaultTableModel flightsTableModel = new DefaultTableModel(flightColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    String[] bookingColumnNames = {
            "Numero Biglietto", "Posto Assegnato", "Nome Passeggero", "Codice Volo",
            "Stato"
    };
    DefaultTableModel bookingTableModel = new DefaultTableModel(bookingColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    String[] luggageColumnNames = {
            "Codice", "Tipo", "Nome Passeggero", "Stato", "Codice Volo",
    };
    String[] luggageAdminColumnNames = {
            "Codice", "Tipo", "Codice Fiscale Passeggero", "Stato", "Codice Volo",
    };
    DefaultTableModel luggageAdminTableModel = new DefaultTableModel(luggageAdminColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    DefaultTableModel luggageTableModel = new DefaultTableModel(luggageColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DefaultTableModel getFlightsModel() {
        try{
        utenteGenericoDAO.showFlights(flightsTableModel);
        return flightsTableModel;
        } catch (SQLException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Creazione Tabella Voli per Utente Generico", ex);
            return null;
        }
    }

    public DefaultTableModel getFlightsAdminModel() {
        // Popola il modello con i dati da voliGestiti
        flightsAdminTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        Amministratore admin = (Amministratore) utenteAutenticato;
        for (Volo volo : admin.getVoliGestiti()) {
            Object[] rowData = new Object[] {
                    volo.getCodice(),
                    volo.getPostiDisponibili(),
                    volo.getCompagniaAerea(),
                    volo.getAeroportoOrigine(),
                    volo.getAeroportoDestinazione(),
                    volo.getData().format(formatter),
                    volo.getOrario(),
                    volo.getRitardo(),
                    volo.getStato(),
                    volo.getNumeroGate()
            };
            flightsAdminTableModel.addRow(rowData);
        }
        return flightsAdminTableModel;
    }

    public DefaultTableModel getBagsTableModel() {
        luggageTableModel.setRowCount(0);
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        for (Prenotazione p : utente.getPrenotazioniUtente()) {
            for(Bagaglio b : p.getPasseggero().getBagagli())
            {
                Object[] rowData = new Object[] {
                        b.getCodiceVisualizzato(),
                        b.getTipo(),
                        p.getPasseggero().getNome(),
                        b.getStato(),
                        p.getVolo().getCodice(),
                };
                luggageTableModel.addRow(rowData);
            }
        }
        return luggageTableModel;
    }

    public DefaultTableModel getBagsAdminTableModel() {
        try{
        amministratoreDAO.showLuggage(luggageAdminTableModel, (Amministratore) utenteAutenticato);
        return luggageAdminTableModel;
        } catch (SQLException _) {return null;}}

    public DefaultTableModel getBookingTableModel() {
        // Popola il modello con i dati prenotazioniUtente
        bookingTableModel.setRowCount(0);
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        for (Prenotazione p : utente.getPrenotazioniUtente()) {
            Object[] rowData = new Object[] {
                    p.getNumeroBiglietto(),
                    p.getPostoAssegnato(),
                    p.getPasseggero().getNome(),
                    p.getVolo().getCodice(),
                    p.getStato(),
            };
            bookingTableModel.addRow(rowData);
        }
        return bookingTableModel;
    }

    /* *******************************++++++++++++++++++++++++++++++++++ */
    // UTENTE
    /* *******************************++++++++++++++++++++++++++++++++++ */

    public void registraUtente(String nomeUtente, String email, char[] password) throws SQLException {
        if(!utenteDAO.esisteUtente(nomeUtente)) {
            if(!utenteDAO.esisteEmail(email)) {
                UtenteGenerico utente = new UtenteGenerico(nomeUtente, email, new String(password).trim()); //in memoria
                utenteDAO.registraUtente(utente); //su DB
            }
            else throw new EmailAlreadyExistsException("È già presente un account che usa questa email");
        }
        else
            throw new UserAlreadyExistsException("Nome Utente già in uso");
    }

    public int loginUtente(String login, char[] password) throws SQLException {
        Utente utente = utenteDAO.loginUtente(login, new String(password).trim());
        if(utente == null) throw new UserNotFoundException("Utente non trovato");

        utenteAutenticato = utente;
        switch (utente) {
            case Amministratore amministratore -> {
                amministratoreDAO.addFlights(amministratore);
                return 1;
            }
            case UtenteGenerico generico -> {
                utenteGenericoDAO.caricaPrenotazioniUtente(generico);
                return 0;
            }
            default -> throw new UserNotFoundException("Utente non trovato");
        }
    }

    /* *******************************++++++++++++++++++++++++++++++++++ */
    // UTENTE GENERICO
    /* *******************************++++++++++++++++++++++++++++++++++ */

    public void logout() {
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        utente.getPrenotazioniUtente().clear();
        utenteAutenticato = null;
        codiceVoloDaPrenotare = null;
    }

    public void iniziaPrenotazione(String codiceVolo){
        codiceVoloDaPrenotare = codiceVolo;
    }

    public void confermaPrenotazione(String nome, String secNome, String cognome, String cf, JTable table) throws SQLException {
        List<String> tipiBagagli = new ArrayList<>();
        Passeggero passeggero = new Passeggero(nome, secNome, cognome, cf);
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        int newLuggageCode = utenteGenericoDAO.currentLuggageCode() + 1;
        int newReservationCode = utenteGenericoDAO.currentReservationCode() + 1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Object value = table.getValueAt(i, 0);
            if (value != null) {
                tipiBagagli.add(value.toString());
                Bagaglio bag = new Bagaglio(newLuggageCode++,passeggero,value.toString());
                passeggero.addBagaglio(bag);
            }
        }
        String postoAssegnato = generaPosto();
        utenteGenericoDAO.confirmReservation(nome,secNome,cognome,cf,tipiBagagli, postoAssegnato,(UtenteGenerico) utenteAutenticato, codiceVoloDaPrenotare);
        Prenotazione p = new Prenotazione(newReservationCode,postoAssegnato,utenteGenericoDAO.voloDaPrenotare(codiceVoloDaPrenotare),passeggero,utente);
        utente.getPrenotazioniUtente().add(p);

    }

    public void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException{
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        for(Prenotazione p : utente.getPrenotazioniUtente()) {
            if(p.getNumeroBiglietto() == numeroBiglietto){
                p.setStato(StatoPrenotazione.valueOf(stato.replace(" ", "_").toUpperCase()));
            }
        }
        utenteGenericoDAO.modificaPrenotazione(numeroBiglietto, stato);
    }

    public void segnalaBagaglio(String codice) throws SQLException{
        utenteGenericoDAO.segnalaBagaglio(codice);
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        for(Prenotazione p : utente.getPrenotazioniUtente()) {
            for(Bagaglio b : p.getPasseggero().getBagagli()){
                if(b.getCodiceVisualizzato().equals(codice)){
                    b.setStato(StatoBagaglio.SMARRITO);
                }
            }
        }
    }

    public void cercaVolo(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                          String data, String orario, String ritardo, String stato, String numeroGate) throws SQLException {
        utenteGenericoDAO.ricercaVolo(flightsTableModel, codice, posti,compagnia,
                aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
    }

    public void cercaBagaglio(String codice, String tipo, String stato) throws SQLException {
        utenteGenericoDAO.searchLuggage(luggageTableModel, (UtenteGenerico) utenteAutenticato,codice, tipo, stato);
    }

    public void cercaPrenotazione(String codiceVolo, String nomePasseggero) throws SQLException {
        utenteGenericoDAO.ricercaPrenotazione(bookingTableModel, (UtenteGenerico) utenteAutenticato, codiceVolo, nomePasseggero);
    }

    /* *******************************++++++++++++++++++++++++++++++++++ */
    // ADMIN
    /* *******************************++++++++++++++++++++++++++++++++++ */

    public void inserisciVolo(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                              String data, String orario, String numeroGate) throws SQLException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

        Volo volo = new Volo(codice,safeParseInteger(posti),safeParseInteger(posti),compagnia,aeroportoOrigine,aeroportoDestinazione,LocalDate.parse(data, dateFormatter),
                safeParseLocalTime(orario),null,StatoVolo.PROGRAMMATO,safeParseInteger(numeroGate));
        Amministratore admin = (Amministratore) utenteAutenticato;
        amministratoreDAO.inserisciVolo(volo,admin);
        admin.gestisciVolo(volo);
    }

    public void logoutAdmin() {
        Amministratore admin = (Amministratore) utenteAutenticato;
        admin.getVoliGestiti().clear();
        utenteAutenticato = null;
    }

    public void salvaModificheDaTabella(JTable table) throws ModifyTableException {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Amministratore admin = (Amministratore) utenteAutenticato;
            List<Volo> voliGestiti = admin.getVoliGestiti();

            for (int i = 0; i < model.getRowCount(); i++) {
                String codice = model.getValueAt(i, 0).toString();
                Volo v;
                for (Volo volo : voliGestiti) {
                    v = volo;
                    if (Objects.equals(v.getCodice(), codice)) {
                        // aggiorna i campi del volo esistente
                        admin.modificaPostiVolo(v,safeParseInteger(model.getValueAt(i, 1)));
                        admin.modificaCompagniaVolo(v,model.getValueAt(i,2).toString());
                        admin.modificaAeroportoOrigine(v,model.getValueAt(i,3).toString());
                        admin.modificaAeroportoDestinazione(v,model.getValueAt(i,4).toString());
                        admin.modificaDataVolo(v,LocalDate.parse(model.getValueAt(i, 5).toString(), DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
                        admin.modificaOrarioVolo(v,LocalTime.parse(model.getValueAt(i, 6).toString()));
                        admin.modificaRitardoVolo(v,safeParseLocalTime(model.getValueAt(i, 7)));
                        admin.modificaStatoVolo(v,StatoVolo.valueOf(model.getValueAt(i, 8).toString().replace(" ", "_").toUpperCase()));
                        admin.modificaNumeroGateVolo(v, safeParseInteger(model.getValueAt(i, 9)));
                        amministratoreDAO.aggiornaVolo(v);
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new ModifyTableException(ex.getMessage());
        }
    }

    public void modificaBagaglio(String codice, String nuovoStato) throws SQLException{
        amministratoreDAO.aggiornaStatoBagaglio(codice,nuovoStato);
    }

    public void cercaVoloAdmin(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                               String data, String orario, String ritardo, String stato, String numeroGate) throws SQLException {
        amministratoreDAO.ricercaVolo(flightsAdminTableModel,(Amministratore) utenteAutenticato, codice, posti,compagnia,
                aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
    }

    public void cercaBagaglioAdmin(String codice, String tipo, String stato) throws SQLException {
        amministratoreDAO.searchLuggage(luggageAdminTableModel, (Amministratore) utenteAutenticato,codice, tipo, stato);
    }

    /* *******************************++++++++++++++++++++++++++++++++++ */
    // UTILITY
    /* *******************************++++++++++++++++++++++++++++++++++ */

    public static String generaPosto() {
        int fila = random.nextInt(30) + 1; // file da 1 a 30
        char sedile = sedili[random.nextInt(sedili.length)];
        return String.format("%d%c", fila, sedile);
    }

    public boolean canPressRegister(String nomeUtente, String email, char[] password, char[] confirmPassword) {
        return isNameValid(nomeUtente) && isEmailValid(email) && isPasswordValid(new String(password).trim(), new String(confirmPassword).trim());
    }

    public boolean isEmailValid(String email) {
        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && !email.isBlank() && email.matches(regexEmail);
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
                return Integer.valueOf(value.toString());
            } catch (NumberFormatException _) {
                return null;
            }
        }
        return null;
    }

    private LocalTime safeParseLocalTime(Object value) {
        if (value == null) return null;
        String s = value.toString();
        if (s.isBlank()) return null;
        return LocalTime.parse(s);
    }
}
