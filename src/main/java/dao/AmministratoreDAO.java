package dao;

import model.Amministratore;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

/**
 * L'interfaccia {@code AmministratoreDAO}
 */
public interface AmministratoreDAO {
    /**
     * Aggiungi voli.
     *
     * @param admin         l'admin
     * @throws SQLException l'eccezione sql
     */
    void addFlights(Amministratore admin) throws SQLException;

    /**
     * Aggiorna volo.
     *
     * @param volo          il volo
     * @throws SQLException l'eccezione sql
     */
    void aggiornaVolo(Volo volo) throws SQLException;

    /**
     * Inserisci volo.
     *
     * @param volo          il volo
     * @param admin         l'admin
     * @throws SQLException l'eccezione sql
     */
    void inserisciVolo(Volo volo, Amministratore admin) throws SQLException;

    /**
     * Ricerca volo.
     *
     * @param model                 il model
     * @param admin                 l'admin
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
    void ricercaVolo(DefaultTableModel model, Amministratore admin, String codice, String posti,
                          String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                          String data, String orario, String ritardo, String  stato, String numGate) throws SQLException;

    /**
     * Mostra i bagagli.
     *
     * @param luggageModel  il model dei bagagli
     * @param admin         l'admin
     * @throws SQLException l'eccezione sql
     */
    void showLuggage(DefaultTableModel luggageModel, Amministratore admin) throws SQLException;

    /**
     * Cerca bagagli.
     *
     * @param model         il model
     * @param admin         l'admin
     * @param codice        il codice
     * @param tipo          il tipo
     * @param stato         lo stato
     * @throws SQLException l'eccezione sql
     */
    void searchLuggage(DefaultTableModel model, Amministratore admin, String codice, String tipo, String stato) throws SQLException;

    /**
     * Aggiorna stato bagaglio.
     *
     * @param codice        il codice
     * @param nuovoStato    il nuovo stato
     * @throws SQLException l'eccezione sql
     */
    void aggiornaStatoBagaglio(String codice, String nuovoStato) throws SQLException;
}
