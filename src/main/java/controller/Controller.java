package controller;

import custom_exceptions.EmailAlreadyExistsException;
import custom_exceptions.ModifyTableException;
import custom_exceptions.UserAlreadyExistsException;
import custom_exceptions.UserNotFoundException;
import dao.UtenteDAO;
import dao.AmministratoreDAO;
import dao.UtenteGenericoDAO;
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

public class Controller {

    private Utente utenteAutenticato;
    AmministratoreDAO amministratoreDAO = new AmministratoreImplementazionePostgresDAO();
    UtenteGenericoDAO utenteGenericoDAO = new UtenteGenericoImplementazionePostgresDAO();
    String codiceVoloDaPrenotare;

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
            "Codice", "Tipo", "Codice Fiscale Passeggero", "Stato"
    };
    DefaultTableModel luggageAdminTableModel = new DefaultTableModel(luggageColumnNames, 0) {
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
        } catch (SQLException ex) {return null;}
    }
    public DefaultTableModel getFlightsAdminModel() {
        try{
        amministratoreDAO.showFlights(flightsAdminTableModel,(Amministratore) utenteAutenticato);
        return flightsAdminTableModel;
        } catch (SQLException ex) {return null;}
    }
    public DefaultTableModel getBagsTableModel() {
        try{
        utenteGenericoDAO.showLuggage(luggageTableModel, (UtenteGenerico) utenteAutenticato);
        return luggageTableModel;
        } catch (SQLException ex) {return null;}
    }
    public DefaultTableModel getBagsAdminTableModel() {
        try{
        amministratoreDAO.showLuggage(luggageAdminTableModel, (Amministratore) utenteAutenticato);
        return luggageAdminTableModel;
        } catch (SQLException ex) {return null;}}
    public DefaultTableModel getBookingTableModel() {
        try{
        utenteGenericoDAO.showReservations(bookingTableModel,(UtenteGenerico) utenteAutenticato);
        return bookingTableModel;
        } catch (SQLException ex) {return null;}
    }

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
        if(utente == null) throw new UserNotFoundException("Utente non trovato");

        utenteAutenticato = utente;
        if (utente instanceof Amministratore amministratore) {
            amministratoreDAO.addFlights(amministratore);
            return 1;
        } else if (utente instanceof UtenteGenerico) {
            return 0;
        }
        else throw new UserNotFoundException("Utente non trovato");
    }

    public void inserisciVolo(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                              String data, String orario, String numeroGate) throws SQLException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Volo volo = new Volo(codice,safeParseInteger(posti),safeParseInteger(posti),compagnia,aeroportoOrigine,aeroportoDestinazione,LocalDate.parse(data, dateFormatter),
                safeParseLocalTime(orario),null,StatoVolo.PROGRAMMATO,safeParseInteger(numeroGate));
        Amministratore admin = (Amministratore) utenteAutenticato;
        admin.gestisciVolo(volo);
        amministratoreDAO.inserisciVolo(volo,admin);
    }

    public void iniziaPrenotazione(String codiceVolo){
        codiceVoloDaPrenotare = codiceVolo;
    }

    public void confermaPrenotazione(String nome, String secNome, String cognome, String cf, JTable table) throws SQLException {
        List<String> tipiBagagli = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            Object value = table.getValueAt(i, 0);
            if (value != null) {
                tipiBagagli.add(value.toString());
            }
        }
        utenteGenericoDAO.confirmReservation(nome,secNome,cognome,cf,tipiBagagli,(UtenteGenerico) utenteAutenticato, codiceVoloDaPrenotare);
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
                        // todo aggiungere altro
                        admin.modificaPostiVolo(v,safeParseInteger(model.getValueAt(i, 1).toString()));
                        admin.modificaDataVolo(v,LocalDate.parse(model.getValueAt(i, 5).toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        admin.modificaOrarioVolo(v,LocalTime.parse(model.getValueAt(i, 6).toString()));
                        admin.modificaRitardoVolo(v,safeParseLocalTime(model.getValueAt(i, 7)));
                        admin.modificaStatoVolo(v,StatoVolo.valueOf(model.getValueAt(i, 8).toString().replace(" ", "_").toUpperCase()));
                        admin.modificaNumeroGateVolo(v,safeParseInteger(model.getValueAt(i, 9).toString()));

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

    public void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException{
        utenteGenericoDAO.modificaPrenotazione(numeroBiglietto, stato);
    }

    public void segnalaBagaglio(String codice) throws SQLException{
        utenteGenericoDAO.segnalaBagaglio(codice);
    }

    public DefaultTableModel cercaVoloAdmin(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                                       String data, String orario, String ritardo, String stato, String numeroGate) throws SQLException {
        amministratoreDAO.ricercaVolo(flightsAdminTableModel,(Amministratore) utenteAutenticato, codice, posti,compagnia,
                aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
        return flightsAdminTableModel;
    }

    public DefaultTableModel cercaVolo(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                                            String data, String orario, String ritardo, String stato, String numeroGate) throws SQLException {
        utenteGenericoDAO.ricercaVolo(flightsTableModel, codice, posti,compagnia,
                aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
        return flightsTableModel;
    }

    public DefaultTableModel cercaBagaglioAdmin(String codice, String tipo, String stato) throws SQLException {
        amministratoreDAO.searchLuggage(luggageAdminTableModel, (Amministratore) utenteAutenticato,codice, tipo, stato);
        return luggageAdminTableModel;
    }

    public DefaultTableModel cercaBagaglio(String codice, String tipo, String stato) throws SQLException {
        utenteGenericoDAO.searchLuggage(luggageTableModel, (UtenteGenerico) utenteAutenticato,codice, tipo, stato);
        return luggageTableModel;
    }

    public DefaultTableModel cercaPrenotazione(String codiceVolo, String nomePasseggero) throws SQLException {
        utenteGenericoDAO.ricercaPrenotazione(bookingTableModel, (UtenteGenerico) utenteAutenticato, codiceVolo, nomePasseggero);
        return bookingTableModel;
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
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
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
