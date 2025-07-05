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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe {@code Controller}
 */
public class Controller {

    private Utente utenteAutenticato;
    private final UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();
    private final AmministratoreDAO amministratoreDAO = new AmministratoreImplementazionePostgresDAO();
    private final UtenteGenericoDAO utenteGenericoDAO = new UtenteGenericoImplementazionePostgresDAO();
    private String codiceVoloDaPrenotare;
    private static final Random random = new Random();
    private static final char[] sedili = {'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    /**
     * i nomi delle colonne dei voli
     */
    String[] flightColumnNames = {
            "Codice", "Posti Disponibili", "Comp. Aerea", "Origine",
            "Destinazione", "Data", "Orario", "Ritardo", "Stato", "Numero Gate"
    };
    /**
     * Il model della tabella dei voli admin
     */
    DefaultTableModel flightsAdminTableModel = new DefaultTableModel(flightColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0;
        }
    };
    /**
     * Il model della tabella dei voli
     */
    DefaultTableModel flightsTableModel = new DefaultTableModel(flightColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * I nomi delle colonne di prenotazione
     */
    String[] bookingColumnNames = {
            "Numero Biglietto", "Posto Assegnato", "Nome Passeggero", "Codice Volo",
            "Stato"
    };
    /**
     * Il model della tabella delle pronotazioni
     */
    DefaultTableModel bookingTableModel = new DefaultTableModel(bookingColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Nome delle colonne dei bagagli.
     */
    String[] luggageColumnNames = {
            "Codice", "Tipo", "Nome Passeggero", "Stato", "Codice Volo",
    };
    /**
     * Nomi delle colonne dell'admin dei bagagli
     */
    String[] luggageAdminColumnNames = {
            "Codice", "Tipo", "Codice Fiscale Passeggero", "Stato", "Codice Volo",
    };
    /**
     * Il model della tabella del bagaglio admin.
     */
    DefaultTableModel luggageAdminTableModel = new DefaultTableModel(luggageAdminColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    /**
     * Il model della tabella del bagaglio.
     */
    DefaultTableModel luggageTableModel = new DefaultTableModel(luggageColumnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Getter flights model.
     *
     * @return il model dei voli
     */
    public DefaultTableModel getFlightsModel() {
        try{
        utenteGenericoDAO.showFlights(flightsTableModel);
        return flightsTableModel;
        } catch (SQLException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, "Errore Creazione Tabella Voli per Utente Generico", ex);
            return null;
        }
    }

    /**
     * Getter flights admin model.
     *
     * @return il model dei voli admin
     * */
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

    /**
     * Getter bags table model.
     *
     * @return model delle borse dell'amministratore
     */
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

    /**
     * Getter bags admin table model.
     *
     * @return il model delle borse dell'amministratore
     */
    public DefaultTableModel getBagsAdminTableModel() {
        try{
        amministratoreDAO.showLuggage(luggageAdminTableModel, (Amministratore) utenteAutenticato);
        return luggageAdminTableModel;
        } catch (SQLException _) {return null;}}

    /**
     * Getter booking table model.
     *
     * @return il model della tabella delle prenotazioni
     */
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

    /**
     * Registra utente.
     *
     * @param nomeUtente    il nome utente
     * @param email         l'email
     * @param password      la password
     * @throws SQLException l'eccezione sql
     */
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

    /**
     * Login utente.
     *
     * @param login         login
     * @param password      la password
     * @return              il valore intero
     * @throws SQLException l'eccezione sql
     */
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

    /**
     * Logout.
     */
    public void logout() {
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        utente.getPrenotazioniUtente().clear();
        utenteAutenticato = null;
        codiceVoloDaPrenotare = null;
    }

    /**
     * Inizia prenotazione.
     *
     * @param codiceVolo il codice volo
     */
    public void iniziaPrenotazione(String codiceVolo){
        codiceVoloDaPrenotare = codiceVolo;
    }

    /**
     * Conferma prenotazione.
     *
     * @param nome          il nome
     * @param secNome       il secondo nome
     * @param cognome       il cognome
     * @param cf            il codicefiscale
     * @param table         la tabella
     * @throws SQLException l'eccezione sql
     */
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

    /**
     * Modifica prenotazione.
     *
     * @param numeroBiglietto il numero biglietto
     * @param stato           lo stato
     * @throws SQLException   l'eccezione sql
     */
    public void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException{
        utenteGenericoDAO.modificaPrenotazione(numeroBiglietto, stato);
        UtenteGenerico utente = (UtenteGenerico) utenteAutenticato;
        for(Prenotazione p : utente.getPrenotazioniUtente()) {
            if(p.getNumeroBiglietto() == numeroBiglietto){
                p.setStato(StatoPrenotazione.valueOf(stato.replace(" ", "_").toUpperCase()));
                if(stato.equals("Cancellata")){
                    Iterator<Bagaglio> it = p.getPasseggero().getBagagli().iterator();
                    while (it.hasNext()) {
                        it.next();
                        it.remove();
                    }
                }
            }
        }
    }

    /**
     * Segnala bagaglio.
     *
     * @param codice        il codice
     * @throws SQLException l'eccezione sql
     */
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

    /**
     * Cerca volo.
     *
     * @param codice                il codice
     * @param posti                 i posti
     * @param compagnia             la compagnia
     * @param aeroportoOrigine      l'aeroporto origine
     * @param aeroportoDestinazione l'aeroporto destinazione
     * @param data                  la data
     * @param orario                l'orario
     * @param ritardo               il ritardo
     * @param stato                 lo stato
     * @param numeroGate            il numero del gate
     * @throws SQLException         l'eccezione sql
     */
    public void cercaVolo(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                          String data, String orario, String ritardo, String stato, String numeroGate) throws SQLException {
        utenteGenericoDAO.ricercaVolo(flightsTableModel, codice, posti,compagnia,
                aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
    }

    /**
     * Cerca bagaglio.
     *
     * @param codice        il codice
     * @param tipo          il tipo
     * @param stato         lo stato
     * @throws SQLException l'eccezione sql
     */
    public void cercaBagaglio(String codice, String tipo, String stato) throws SQLException {
        utenteGenericoDAO.searchLuggage(luggageTableModel, (UtenteGenerico) utenteAutenticato,codice, tipo, stato);
    }

    /**
     * Cerca prenotazione.
     *
     * @param codiceVolo     il codice volo
     * @param nomePasseggero il nome passeggero
     * @throws SQLException  l'eccezione sql
     */
    public void cercaPrenotazione(String codiceVolo, String nomePasseggero) throws SQLException {
        utenteGenericoDAO.ricercaPrenotazione(bookingTableModel, (UtenteGenerico) utenteAutenticato, codiceVolo, nomePasseggero);
    }

    /* *******************************++++++++++++++++++++++++++++++++++ */
    // ADMIN
    /* *******************************++++++++++++++++++++++++++++++++++ */

    /**
     * Inserisci volo.
     *
     * @param codice                il codice
     * @param posti                 i posti
     * @param compagnia             la compagnia
     * @param aeroportoOrigine      l'aeroporto origine
     * @param aeroportoDestinazione l'aeroporto destinazione
     * @param data                  la data
     * @param orario                l'orario
     * @param numeroGate            il numero del gate
     * @throws SQLException         l'eccezione sql
     */
    public void inserisciVolo(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                              String data, String orario, String numeroGate) throws SQLException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

        Volo volo = new Volo(codice,safeParseInteger(posti),safeParseInteger(posti),compagnia,aeroportoOrigine,aeroportoDestinazione,LocalDate.parse(data, dateFormatter),
                safeParseLocalTime(orario),null,StatoVolo.PROGRAMMATO,safeParseInteger(numeroGate));
        Amministratore admin = (Amministratore) utenteAutenticato;
        amministratoreDAO.inserisciVolo(volo,admin);
        admin.gestisciVolo(volo);
    }

    /**
     * Logout admin.
     */
    public void logoutAdmin() {
        Amministratore admin = (Amministratore) utenteAutenticato;
        admin.getVoliGestiti().clear();
        utenteAutenticato = null;
    }

    /**
     * Salva modifiche da tabella.
     *
     * @param table                 la tabella
     * @throws ModifyTableException l'eccezione della tabella modificata
     */
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

    /**
     * Modifica bagaglio.
     *
     * @param codice        il codice
     * @param nuovoStato    il nuovo stato
     * @throws SQLException l'eccezione sql
     */
    public void modificaBagaglio(String codice, String nuovoStato) throws SQLException{
        amministratoreDAO.aggiornaStatoBagaglio(codice,nuovoStato);
    }

    /**
     * Cerca volo admin.
     *
     * @param codice                il codice
     * @param posti                 i posti
     * @param compagnia             la compagnia
     * @param aeroportoOrigine      l'aeroporto origine
     * @param aeroportoDestinazione l'aeroporto destinazione
     * @param data                  la data
     * @param orario                l'orario
     * @param ritardo               il ritardo
     * @param stato                 lo stato
     * @param numeroGate            il numero del gate
     * @throws SQLException         l'eccezione sql
     */
    public void cercaVoloAdmin(String codice, String posti, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                               String data, String orario, String ritardo, String stato, String numeroGate) throws SQLException {
        amministratoreDAO.ricercaVolo(flightsAdminTableModel,(Amministratore) utenteAutenticato, codice, posti,compagnia,
                aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
    }

    /**
     * Cerca bagaglio admin.
     *
     * @param codice        il codice
     * @param tipo          il tipo
     * @param stato         lo stato
     * @throws SQLException l'eccezione sql
     */
    public void cercaBagaglioAdmin(String codice, String tipo, String stato) throws SQLException {
        amministratoreDAO.searchLuggage(luggageAdminTableModel, (Amministratore) utenteAutenticato,codice, tipo, stato);
    }

    /* *******************************++++++++++++++++++++++++++++++++++ */
    // UTILITY
    /* *******************************++++++++++++++++++++++++++++++++++ */

    /**
     * Genera posto.
     *
     * @return la stringa
     */
    public static String generaPosto() {
        int fila = random.nextInt(30) + 1; // file da 1 a 30
        char sedile = sedili[random.nextInt(sedili.length)];
        return String.format("%d%c", fila, sedile);
    }

    /**
     * Valuta se puoi premere il pulsante di registrazione.
     *
     * @param nomeUtente      il nome utente
     * @param email           l'email
     * @param password        la password
     * @param confirmPassword conferma la password
     * @return                il valore booleano
     */
    public boolean canPressRegister(String nomeUtente, String email, char[] password, char[] confirmPassword) {
        return isNameValid(nomeUtente) && isEmailValid(email) && isPasswordValid(new String(password).trim(), new String(confirmPassword).trim());
    }

    /**
     * Valuta se l'email è valida.
     *
     * @param email l'email
     * @return      il valore booleano
     */
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
