package implementazioni_postgres_dao;

import dao.UtenteGenericoDAO;
import database.ConnessioneDatabase;
import model.*;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

/**
 * La classe {@code UtenteGenericoImplementazionePostgresDAO}
 */
public class UtenteGenericoImplementazionePostgresDAO extends UserUtilFunctionsForDAO implements UtenteGenericoDAO {

    public void showFlights(DefaultTableModel flightsModel) throws SQLException {
        String sql = "SELECT codice, posti_disponibili, compagnia_aerea, aeroporto_origine, " +
                "aeroporto_destinazione, data, orario, ritardo, stato, numero_gate " +
                "FROM volo";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            flightsModel.setRowCount(0);
            addFlightRows(stmt, flightsModel);
        }
    }

    public void segnalaBagaglio(String codice) throws SQLException {
        String sql = "UPDATE bagaglio SET stato = ?::stato_bagaglio WHERE codice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "SMARRITO");
            stmt.setInt(2, Integer.parseInt(codice.replace("BAG-",""), 36));
            stmt.executeUpdate();
        }
    }

    public void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException{
        String updatePrenotazione = "UPDATE prenotazione SET stato = ?::stato_prenotazione WHERE numero_biglietto = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(updatePrenotazione)) {
            stmt.setString(1, stato.replace(" ", "_").toUpperCase());
            stmt.setInt(2, numeroBiglietto);
            stmt.executeUpdate();
        }
    }

    public void searchLuggage(DefaultTableModel model, UtenteGenerico utente, String codice, String tipo, String stato) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT B.codice, B.stato, B.tipo, PA.nome, P.codice_volo " +
                "FROM bagaglio B JOIN prenotazione P ON B.codice_fiscale_passeggero = P.codice_fiscale " +
                "JOIN passeggero PA ON PA.codice_fiscale = B.codice_fiscale_passeggero " +
                "WHERE P.nome_utente = ?");
        List<Object> params = List.of(utente.getNomeUtente());
        searchLuggageInternal(model, query, params, codice, tipo, stato);
    }

    public Volo voloDaPrenotare(String codiceVolo) throws SQLException{
        String sql = "SELECT codice, n_posti, posti_disponibili, compagnia_aerea, " +
                "aeroporto_origine, aeroporto_destinazione, data, orario, ritardo, stato, numero_gate " +
                "FROM volo " +
                "WHERE codice = ? ";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codiceVolo);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new Volo(
                        rs.getString("codice"),
                        rs.getInt("n_posti"),
                        rs.getInt("posti_disponibili"),
                        rs.getString("compagnia_aerea"),
                        rs.getString("aeroporto_origine"),
                        rs.getString("aeroporto_destinazione"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("orario").toLocalTime(),
                        rs.getTime("ritardo") != null ? rs.getTime("ritardo").toLocalTime() : null,
                        StatoVolo.valueOf(rs.getString("stato")),
                        rs.getInt("numero_gate")
                );
            }
        }
        return null;
    }

    public void ricercaVolo(DefaultTableModel model, String codice, String posti,
                            String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                            String data, String orario, String ritardo, String  stato, String numGate) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM volo WHERE 1=1");
        List<Object> params = new ArrayList<>();
        ricercaVoloInternal(model, query, params, codice, posti, compagnia, aeroportoOrigine, aeroportoDestinazione,
                data, orario, ritardo, stato, numGate);
    }

    public void ricercaPrenotazione(DefaultTableModel model, UtenteGenerico utente, String codiceVolo, String nomePasseggero) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM prenotazione PR JOIN passeggero PA ON PR.codice_fiscale = PA.codice_fiscale " +
                "WHERE PR.nome_utente = ?");
        List<Object> parametri = new ArrayList<>();
        parametri.add(utente.getNomeUtente());

        if (!codiceVolo.isBlank() && !codiceVolo.equals("Codice Volo Prenotato")) {
            query.append(" AND PR.codice_volo = ?");
            parametri.add(codiceVolo);
        }
        if (!nomePasseggero.isBlank() && !nomePasseggero.equals("Nome Passeggero")) {
            query.append(" AND PA.nome = ?");
            parametri.add(nomePasseggero);
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
                            rs.getInt("numero_biglietto"),
                            rs.getString("posto_assegnato"),
                            rs.getString("nome"),
                            rs.getString("codice_volo"),
                            StatoPrenotazione.valueOf(rs.getString("stato")).toString(),
                    };
                    model.addRow(row);
                }
            }
        }
    }

    public void confirmReservation(String nome, String secondoNome, String cognome, String codiceFiscale, List<String> tipiBagagli, String posto, UtenteGenerico utente, String codiceVolo) throws SQLException{
        try (Connection conn = ConnessioneDatabase.getInstance().connection) {
            conn.setAutoCommit(false);
            try {
                if(!passeggeroExists(conn,codiceFiscale))
                {
                    addPassenger(conn, nome, secondoNome, cognome, codiceFiscale);
                }
                addReservation(conn, codiceVolo, utente.getNomeUtente(), codiceFiscale, posto);
                for(String tipo : tipiBagagli) {
                    addBagaglio(conn,tipo,codiceFiscale, codiceVolo);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        }
    }

    private boolean passeggeroExists(Connection conn, String codiceFiscale) throws SQLException {
        String query = "SELECT 1 FROM passeggero WHERE codice_fiscale = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codiceFiscale);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true se almeno una riga esiste
            }
        }
    }

    private void addPassenger(Connection conn, String nome, String secondoNome, String cognome, String codiceFiscale) throws SQLException {
        String sql = "INSERT INTO passeggero (codice_fiscale, nome, secondo_nome, cognome) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codiceFiscale);
            stmt.setString(2, nome);
            if(secondoNome.isBlank() || secondoNome.equals("Secondo Nome")) {
                stmt.setNull(3, Types.VARCHAR);
            }
            else
                stmt.setString(3, secondoNome);
            stmt.setString(4, cognome);
            stmt.executeUpdate();
        }
    }

    private void addReservation(Connection conn, String codiceVolo, String nomeUtente, String codiceFiscale, String posto) throws SQLException {
        String sql = "INSERT INTO prenotazione (posto_assegnato, stato, codice_volo, nome_utente, codice_fiscale) VALUES (?, ?::stato_prenotazione, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, posto);
            stmt.setString(2, "IN_ATTESA");
            stmt.setString(3, codiceVolo);
            stmt.setString(4, nomeUtente);
            stmt.setString(5, codiceFiscale);
            stmt.executeUpdate();
        }
    }

    private void addBagaglio(Connection conn, String tipo, String codiceFiscale, String codiceVolo) throws SQLException {
        String sql = "INSERT INTO bagaglio (stato, tipo ,codice_fiscale_passeggero, codice_volo) VALUES (?::stato_bagaglio, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "REGISTRATO");
            stmt.setString(2, tipo);
            stmt.setString(3, codiceFiscale);
            stmt.setString(4, codiceVolo);
            stmt.executeUpdate();
        }
    }

    public int currentLuggageCode() throws SQLException {
        String sql = "SELECT last_value FROM bagaglio_codice_seq";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public int currentReservationCode() throws SQLException {
        String sql = "SELECT last_value FROM prenotazione_numerobiglietto_seq";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void caricaPrenotazioniUtente(UtenteGenerico utente) throws SQLException {
        String sqlPrenotazioni =
                "SELECT pr.numero_biglietto, pr.posto_assegnato, pr.stato AS stato_prenotazione, " +
                        "pa.nome, pa.secondo_nome, pa.cognome, pa.codice_fiscale, " +
                        "v.codice AS codice_volo, v.n_posti, v.posti_disponibili, v.compagnia_aerea, " +
                        "v.aeroporto_origine, v.aeroporto_destinazione, v.data, v.orario, v.ritardo, v.stato AS stato_volo, v.numero_gate, " +
                        "b.codice AS codice_bagaglio, b.stato AS stato_bagaglio, b.tipo, b.codice_volo AS codice_volo_bagaglio " +
                        "FROM prenotazione pr " +
                        "JOIN passeggero pa ON pr.codice_fiscale = pa.codice_fiscale " +
                        "JOIN volo v ON pr.codice_volo = v.codice " +
                        "LEFT JOIN bagaglio b ON b.codice_fiscale_passeggero = pa.codice_fiscale AND b.codice_volo = pr.codice_volo " +
                        "WHERE pr.nome_utente = ? " +
                        "ORDER BY pr.numero_biglietto";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sqlPrenotazioni)) {

            stmt.setString(1, utente.getNomeUtente());
            ResultSet rs = stmt.executeQuery();

            Map<Integer, Prenotazione> prenotazioniMappa = new HashMap<>();

            while (rs.next()) {
                int numeroBiglietto = rs.getInt("numero_biglietto");

                Prenotazione prenotazione = prenotazioniMappa.get(numeroBiglietto);
                if (prenotazione == null) {
                    // Passeggero
                    Passeggero passeggero = new Passeggero(
                            rs.getString("nome"),
                            rs.getString("secondo_nome"),
                            rs.getString("cognome"),
                            rs.getString("codice_fiscale")
                    );

                    // Volo
                    Volo volo = new Volo(
                            rs.getString("codice_volo"),
                            rs.getInt("n_posti"),
                            rs.getInt("posti_disponibili"),
                            rs.getString("compagnia_aerea"),
                            rs.getString("aeroporto_origine"),
                            rs.getString("aeroporto_destinazione"),
                            rs.getDate("data").toLocalDate(),
                            rs.getTime("orario").toLocalTime(),
                            rs.getTime("ritardo") != null ? rs.getTime("ritardo").toLocalTime() : null,
                            StatoVolo.valueOf(rs.getString("stato_volo")),
                            rs.getInt("numero_gate")
                    );

                    // Prenotazione
                    prenotazione = new Prenotazione(
                            rs.getInt("numero_biglietto"),
                            rs.getString("posto_assegnato"),
                            volo,
                            passeggero,
                            utente
                    );
                    prenotazione.setStato(StatoPrenotazione.valueOf(rs.getString("stato_prenotazione")));

                    int codiceBagaglio = rs.getInt("codice_bagaglio");
                    if (codiceBagaglio > 0 && !rs.getString("stato_prenotazione").equals("CANCELLATA")) {
                        Bagaglio bagaglio = new Bagaglio(
                                codiceBagaglio,
                                passeggero,
                                rs.getString("tipo")
                        );
                        passeggero.getBagagli().add(bagaglio);
                        bagaglio.setStato(StatoBagaglio.valueOf(rs.getString("stato_bagaglio")));
                    }


                    prenotazioniMappa.put(numeroBiglietto, prenotazione);
                }
                else
                {
                    // Bagagli (se presenti)
                    int codiceBagaglio = rs.getInt("codice_bagaglio");
                    if(codiceBagaglio > 0 && !rs.getString("stato_prenotazione").equals("CANCELLATA")) {
                        Bagaglio bagaglio = new Bagaglio(
                                codiceBagaglio,
                                prenotazione.getPasseggero(),
                                rs.getString("tipo")
                        );
                        prenotazione.getPasseggero().getBagagli().add(bagaglio);
                        bagaglio.setStato(StatoBagaglio.valueOf(rs.getString("stato_bagaglio")));
                    }
                }
            }

            utente.getPrenotazioniUtente().clear();
            utente.getPrenotazioniUtente().addAll(prenotazioniMappa.values());
        }
    }
}
