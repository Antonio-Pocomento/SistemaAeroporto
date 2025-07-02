package implementazioni_postgres_dao;

import dao.AmministratoreDAO;
import database.ConnessioneDatabase;
import model.Amministratore;
import model.StatoBagaglio;
import model.StatoVolo;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;

public class AmministratoreImplementazionePostgresDAO extends UserUtilFunctionsForDAO implements AmministratoreDAO {

    public void aggiornaStatoBagaglio(String codice, String nuovoStato) throws SQLException{
        String sql = "UPDATE bagaglio SET stato = ?::stato_bagaglio WHERE codice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, nuovoStato.replace(" ", "_").toUpperCase());
            stmt.setInt(2, Integer.parseInt(codice.replace("BAG-",""), 36));
            stmt.executeUpdate();
        }
    }

    public void searchLuggage(DefaultTableModel model, Amministratore admin, String codice, String tipo, String stato) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT B.codice, B.stato, B.tipo, B.codice_fiscale_passeggero, P.codice_volo " +
                "FROM bagaglio B JOIN prenotazione P ON B.codice_fiscale_passeggero = P.codice_fiscale JOIN volo V ON P.codice_volo = V.codice " +
                "WHERE V.amministratore = ?");
        List<Object> params = List.of(admin.getNomeUtente());
        searchLuggageInternal(model, query, params, codice, tipo, stato);
    }

    public void showLuggage(DefaultTableModel luggageModel, Amministratore admin) throws SQLException {
        String sql = "SELECT B.codice, B.stato, B.tipo, B.codice_fiscale_passeggero, P.codice_volo " +
                "FROM bagaglio B JOIN prenotazione P ON B.codice_fiscale_passeggero = P.codice_fiscale AND B.codice_volo = P.codice_volo " +
                "JOIN volo V ON P.codice_volo = V.codice " +
                "WHERE V.amministratore = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getNomeUtente());
            luggageModel.setRowCount(0);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            String.format("BAG-%s", Integer.toString(rs.getInt("codice"), 36).toUpperCase()),
                            rs.getString("tipo"),
                            rs.getString("codice_fiscale_passeggero"),
                            StatoBagaglio.valueOf(rs.getString("stato")).toString(),
                            rs.getString("codice_volo")
                    };
                    luggageModel.addRow(row);
                }
            }
        }
    }

    public void addFlights(Amministratore admin) throws SQLException {
        String sql = "SELECT codice, n_posti, posti_disponibili, compagnia_aerea, aeroporto_origine, " +
                "aeroporto_destinazione, data, orario, ritardo, stato, numero_gate " +
                "FROM volo WHERE Amministratore = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNomeUtente());

            try (ResultSet rs = stmt.executeQuery()) {
                admin.getVoliGestiti().clear();
                while (rs.next()) {
                    Volo volo = new Volo(
                            rs.getString("codice"),
                            rs.getInt("n_posti"),
                            rs.getInt("posti_disponibili"),
                            rs.getString("compagnia_aerea"),
                            rs.getString("aeroporto_origine"),
                            rs.getString("aeroporto_destinazione"),
                            rs.getDate("data").toLocalDate(),
                            rs.getTime("orario").toLocalTime(),
                            rs.getTime("ritardo") != null ? rs.getTime("ritardo").toLocalTime() : null,
                            rs.getString("stato") != null ? StatoVolo.valueOf(rs.getString("stato")) : null,
                            rs.getInt("numero_gate") != 0 ? rs.getInt("numero_gate") : null
                    );
                    admin.gestisciVolo(volo);
                }
            }
        }
    }

    public void aggiornaVolo(Volo volo) throws SQLException {
        String sql = "UPDATE volo SET posti_disponibili = ?, compagnia_aerea = ?, aeroporto_origine = ?, aeroporto_destinazione = ?, data = ?, orario = ?, ritardo = ?, stato = ?::stato_volo, numero_gate = ? WHERE codice = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, volo.getPostiDisponibili());
            stmt.setString(2, volo.getCompagniaAerea());
            stmt.setString(3, volo.getAeroportoOrigine());
            stmt.setString(4, volo.getAeroportoDestinazione());
            stmt.setDate(5, Date.valueOf(volo.getData()));
            stmt.setTime(6, Time.valueOf(volo.getOrario()));
            stmt.setTime(7, volo.getRitardo() != null ? Time.valueOf(volo.getRitardo()) : null);
            stmt.setString(8, volo.getStato().name());
            if (volo.getNumeroGate() != null) {
                stmt.setInt(9, volo.getNumeroGate());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }
            stmt.setString(10, volo.getCodice());

            stmt.executeUpdate();
        }
    }

    public void inserisciVolo(Volo voloDaAggiungere, Amministratore admin) throws SQLException {
        String sql = "INSERT INTO volo (codice, n_posti, posti_disponibili, compagnia_aerea, aeroporto_origine, aeroporto_destinazione, " +
                "data, orario, ritardo, stato, numero_gate, amministratore) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::stato_volo, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, voloDaAggiungere.getCodice());
            stmt.setInt(2, voloDaAggiungere.getNPosti());
            stmt.setInt(3, voloDaAggiungere.getPostiDisponibili());
            stmt.setString(4, voloDaAggiungere.getCompagniaAerea());
            stmt.setString(5, voloDaAggiungere.getAeroportoOrigine());
            stmt.setString(6, voloDaAggiungere.getAeroportoDestinazione());
            stmt.setDate(7, Date.valueOf(voloDaAggiungere.getData()));
            stmt.setTime(8, Time.valueOf(voloDaAggiungere.getOrario()));
            stmt.setNull(9, Types.TIME);
            stmt.setString(10, voloDaAggiungere.getStato().name());
            if (voloDaAggiungere.getNumeroGate() != null) {
                stmt.setInt(11, voloDaAggiungere.getNumeroGate());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }
            stmt.setString(12, admin.getNomeUtente());
            stmt.executeUpdate();
        }
    }

    public void ricercaVolo(DefaultTableModel model, Amministratore admin, String codice, String posti,
                            String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                            String data, String orario, String ritardo, String  stato, String numGate) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM volo WHERE amministratore= ?");
        List<Object> params = List.of(admin.getNomeUtente());
        ricercaVoloInternal(model, query, params, codice, posti, compagnia, aeroportoOrigine, aeroportoDestinazione,
                data, orario, ritardo, stato, numGate);
    }
}
