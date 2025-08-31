package dao;

import model.UtenteGenerico;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia {@code UtenteGenericoDAO}
 */
public interface UtenteGenericoDAO {
    /**
     * Mostra voli.
     *
     * @param flightsModel  model dei voli
     * @throws SQLException l'eccezione sql
     */
    void showFlights(DefaultTableModel flightsModel) throws SQLException;

    /**
     * Ricerca volo.
     *
     * @param model                 il model
     * @param codice                il codice
     * @param posti                 i posti
     * @param compagnia             la compagnia
     * @param aeroportoOrigine      l'aeroporto origine
     * @param aeroportoDestinazione l'aeroporto destinazione
     * @param data                  la data
     * @param orario                l'orario
     * @param ritardo               il ritardo
     * @param stato                 lo stato
     * @param numGate               il numero del gate
     * @throws SQLException         l'eccezione sql
     */
    void ricercaVolo(DefaultTableModel model, String codice, String posti,
                            String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                            String data, String orario, String ritardo, String  stato, String numGate) throws SQLException;

    /**
     * Ricerca prenotazione.
     *
     * @param model          il model
     * @param utente         l'utente
     * @param codiceVolo     il codice volo
     * @param nomePasseggero il nome del passeggero
     * @throws SQLException  l'eccezione sql
     */
    void ricercaPrenotazione(DefaultTableModel model, UtenteGenerico utente, String codiceVolo, String nomePasseggero) throws SQLException;

    /**
     * cerca bagaglio.
     *
     * @param model         il model
     * @param utente        l'utente
     * @param codice        il codice
     * @param tipo          il tipo
     * @param stato         lo stato
     * @throws SQLException l'eccezione sql
     */
    void searchLuggage(DefaultTableModel model, UtenteGenerico utente, String codice, String tipo, String stato) throws SQLException;

    /**
     * Segnala bagaglio.
     *
     * @param codice        il codice
     * @throws SQLException l'eccezione sql
     */
    void segnalaBagaglio(String codice) throws SQLException;

    /**
     * Modifica prenotazione.
     *
     * @param numeroBiglietto il numero biglietto
     * @param stato           lo stato
     * @throws SQLException   l'eccezione sql
     */
    void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException;

    /**
     * Conferma la prenotazione.
     *
     * @param nome          il nome
     * @param secondoNome   il secondo nome
     * @param cognome       il cognome
     * @param codiceFiscale il codice fiscale
     * @param tipiBagagli   i tipi dei bagagli
     * @param posto         il posto
     * @param utente        l'utente
     * @param codiceVolo    il codice volo
     * @throws SQLException l'eccezione sql
     */
    void confirmReservation(String nome, String secondoNome, String cognome, String codiceFiscale, List<String> tipiBagagli, String posto, UtenteGenerico utente, String codiceVolo) throws SQLException;

    /**
     * Carica prenotazioni utente.
     *
     * @param utente        l'utente
     * @throws SQLException l'eccezione sql
     */
    void caricaPrenotazioniUtente(UtenteGenerico utente) throws SQLException;

    /**
     * Volo da prenotare.
     *
     * @param codiceVolo    il codice volo
     * @return              il volo
     * @throws SQLException l'eccezione sql
     */
    Volo voloDaPrenotare(String codiceVolo) throws SQLException;

    /**
     * Codice bagaglio attuale.
     *
     * @return              l'int
     * @throws SQLException l'eccezione sql
     */
    int currentLuggageCode() throws SQLException;

    /**
     * Codice di prenotazione attuale.
     *
     * @return              l'int
     * @throws SQLException l'eccezione sql
     */
    int currentReservationCode() throws SQLException;
}
