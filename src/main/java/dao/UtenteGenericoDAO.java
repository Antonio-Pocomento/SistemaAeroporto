package dao;

import model.UtenteGenerico;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public interface UtenteGenericoDAO {
    void showFlights(DefaultTableModel flightsModel) throws SQLException;
    void ricercaVolo(DefaultTableModel model, String codice, String posti,
                            String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                            String data, String orario, String ritardo, String  stato, String numGate) throws SQLException;
    void ricercaPrenotazione(DefaultTableModel model, UtenteGenerico utente, String codiceVolo, String nomePasseggero) throws SQLException;
    void searchLuggage(DefaultTableModel model, UtenteGenerico utente, String codice, String tipo, String stato) throws SQLException;
    void segnalaBagaglio(String codice) throws SQLException;
    void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException;
    void confirmReservation(String nome, String secondoNome, String cognome, String codiceFiscale, List<String> tipiBagagli, String posto, UtenteGenerico utente, String codiceVolo) throws SQLException;
    void caricaPrenotazioniUtente(UtenteGenerico utente) throws SQLException;
    Volo voloDaPrenotare(String codiceVolo) throws SQLException;
    int currentLuggageCode() throws SQLException;
    int currentReservationCode() throws SQLException;
}
