package dao;

import model.UtenteGenerico;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public interface UtenteGenericoDAO {
    void showFlights(DefaultTableModel flightsModel) throws SQLException;
    void showReservations(DefaultTableModel reservationModel, UtenteGenerico utente) throws SQLException;
    void ricercaVolo(DefaultTableModel model, String codice, String posti,
                            String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                            String data, String orario, String ritardo, String  stato, String numGate) throws SQLException;
    void ricercaPrenotazione(DefaultTableModel model, UtenteGenerico utente, String codiceVolo, String nomePasseggero) throws SQLException;
    void showLuggage(DefaultTableModel luggageModel, UtenteGenerico utente) throws SQLException;
    void searchLuggage(DefaultTableModel model, UtenteGenerico utente, String codice, String tipo, String stato) throws SQLException;
    void segnalaBagaglio(String codice) throws SQLException;
    void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException;
    void confirmReservation(String nome, String secondoNome, String cognome, String codiceFiscale, List<String> tipiBagagli, UtenteGenerico utente, String codiceVolo) throws SQLException;
}
