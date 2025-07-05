package implementazioni_postgres_dao;

import database.ConnessioneDatabase;
import model.StatoBagaglio;
import model.StatoVolo;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code UserUtilFunctionsForDAO}
 */
public abstract class UserUtilFunctionsForDAO {
    /**
     * aggiunge righe volo.
     *
     * @param ps            il ps
     * @param model         il model
     * @throws SQLException l'eccezione sql
     */
    protected void addFlightRows(PreparedStatement ps, DefaultTableModel model) throws SQLException {
        model.setRowCount(0);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                        rs.getString("codice"),
                        rs.getInt("posti_disponibili"),
                        rs.getString("compagnia_aerea"),
                        rs.getString("aeroporto_origine"),
                        rs.getString("aeroporto_destinazione"),
                        rs.getDate("data").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        rs.getTime("orario").toLocalTime().truncatedTo(ChronoUnit.MINUTES),
                        rs.getTime("ritardo") != null ? rs.getTime("ritardo").toLocalTime().truncatedTo(ChronoUnit.MINUTES) : null,
                        StatoVolo.valueOf(rs.getString("stato")).toString(),
                        rs.getInt("numero_gate") != 0 ? rs.getInt("numero_gate") : "",
                };
                model.addRow(row);
            }
        }
    }

    /**
     * Ricerca bagagli interni.
     *
     * @param model         il model
     * @param query         la query
     * @param initialParams il parametro iniziale
     * @param codice        il codice
     * @param tipo          il tipo
     * @param stato         lo stato
     * @throws SQLException l'eccezione sql
     */
    protected void searchLuggageInternal(DefaultTableModel model, StringBuilder query, List<Object> initialParams, String codice, String tipo, String stato) throws SQLException {
        List<Object> parametri = new ArrayList<>(initialParams);

        if (!codice.isBlank() && !codice.equals("Codice Bagaglio")) {
            query.append(" AND B.codice = ?");
            parametri.add(Integer.parseInt(codice.replace("BAG-", ""), 36));
        }
        if (!tipo.isBlank()) {
            query.append(" AND B.tipo = ?");
            parametri.add(tipo);
        }
        if (!stato.isBlank()) {
            query.append(" AND B.stato = ?::stato_bagaglio");
            parametri.add(stato.replace(" ", "_").toUpperCase());
        }

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            model.setRowCount(0);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            String.format("BAG-%s", Integer.toString(rs.getInt("codice"), 36).toUpperCase()),
                            rs.getString("tipo"),
                            rs.getString(4), // la colonna cambia (nome passeggero o codice fiscale)
                            StatoBagaglio.valueOf(rs.getString("stato")).toString(),
                            rs.getString("codice_volo")
                    };
                    model.addRow(row);
                }
            }
        }
    }

    /**
     * Ricerca volo interno.
     *
     * @param model                 il model
     * @param query                 la query
     * @param initialParams         il parametro iniziale
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
    protected void ricercaVoloInternal(DefaultTableModel model, StringBuilder query, List<Object> initialParams,
                                     String codice, String posti,
                                     String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                                     String data, String orario, String ritardo, String stato, String numGate) throws SQLException {
        List<Object> parametri = new ArrayList<>(initialParams);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (!codice.isBlank() && !codice.equals("Codice Volo")) {
            query.append(" AND codice = ?");
            parametri.add(codice);
        }
        if (!posti.isBlank() && !posti.equals("Posti")) {
            query.append(" AND posti_disponibili = ?");
            parametri.add(Integer.parseInt(posti));
        }
        if (!compagnia.isBlank() && !compagnia.equals("Compagnia Aerea")) {
            query.append(" AND compagnia_aerea = ?");
            parametri.add(compagnia);
        }
        if (!aeroportoOrigine.isBlank() && !aeroportoOrigine.equals("Aeroporto Di Origine")) {
            query.append(" AND aeroporto_origine = ?");
            parametri.add(aeroportoOrigine);
        }
        if (!aeroportoDestinazione.isBlank() && !aeroportoDestinazione.equals("Aeroporto Di Arrivo")) {
            query.append(" AND aeroporto_destinazione = ?");
            parametri.add(aeroportoDestinazione);
        }
        if (!data.isBlank() && !data.equals("Data Volo (DD/MM/YYYY)")) {
            query.append(" AND data = ?");
            parametri.add(LocalDate.parse(data, dateFormatter));
        }
        if (!orario.isBlank() && !orario.equals("Orario Volo (HH:MM)")) {
            query.append(" AND orario = ?");
            parametri.add(LocalTime.parse(orario).truncatedTo(ChronoUnit.MINUTES));
        }
        if (!ritardo.isBlank() && !ritardo.equals("Ritardo Volo (HH:MM)")) {
            query.append(" AND ritardo = ?");
            parametri.add(LocalTime.parse(ritardo).truncatedTo(ChronoUnit.MINUTES));
        }
        if (!stato.isBlank()) {
            query.append(" AND stato = ?::stato_volo");
            parametri.add(stato.replace(" ", "_").toUpperCase());
        }
        if (!numGate.isBlank() && !numGate.equals("Numero Gate")) {
            query.append(" AND numero_gate = ?");
            parametri.add(Integer.parseInt(numGate));
        }

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            addFlightRows(ps, model);
        }
    }

}
