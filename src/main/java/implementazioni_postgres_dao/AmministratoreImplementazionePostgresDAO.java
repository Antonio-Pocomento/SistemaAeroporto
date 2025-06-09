package implementazioni_postgres_dao;

import dao.AmministratoreDAO;
import database.ConnessioneDatabase;
import model.Amministratore;
import model.StatoVolo;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AmministratoreImplementazionePostgresDAO implements AmministratoreDAO {

    public void showFlights(DefaultTableModel flightsModel, Amministratore admin) throws SQLException {
        String sql = "SELECT codice, posti_disponibili, compagnia_aerea, aeroporto_origine, " +
                "aeroporto_destinazione, data, orario, ritardo, stato, numero_gate " +
                "FROM volo WHERE Amministratore = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNomeUtente());
            flightsModel.setRowCount(0);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("codice"),
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
                    flightsModel.addRow(row);
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
                            rs.getInt("codice"),
                            rs.getInt("n_posti"),
                            rs.getInt("posti_disponibili"),
                            rs.getString("compagnia_aerea"),
                            rs.getString("aeroporto_origine"),
                            rs.getString("aeroporto_destinazione"),
                            rs.getDate("data").toLocalDate(),
                            rs.getTime("orario").toLocalTime(),
                            rs.getTime("ritardo") != null ? rs.getTime("ritardo").toLocalTime() : null,
                            rs.getString("stato") != null ? StatoVolo.valueOf(rs.getString("stato")) : null,
                            rs.getInt("numero_gate")
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
            stmt.setInt(10, volo.getCodice());

            stmt.executeUpdate();
        }
    }
}
