package dao;

import model.Amministratore;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public interface AmministratoreDAO {
    void addFlights(Amministratore admin) throws SQLException;
    void aggiornaVolo(Volo volo) throws SQLException;
    void inserisciVolo(Volo volo, Amministratore admin) throws SQLException;
    void ricercaVolo(DefaultTableModel model, Amministratore admin, String codice, String posti,
                          String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                          String data, String orario, String ritardo, String  stato, String numGate) throws SQLException;
    void showLuggage(DefaultTableModel luggageModel, Amministratore admin) throws SQLException;
    void searchLuggage(DefaultTableModel model, Amministratore admin, String codice, String tipo, String stato) throws SQLException;
    void aggiornaStatoBagaglio(String codice, String nuovoStato) throws SQLException;
}
