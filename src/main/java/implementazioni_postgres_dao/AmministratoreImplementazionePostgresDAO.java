package implementazioni_postgres_dao;

import controller.Controller;
import dao.AmministratoreDAO;
import database.ConnessioneDatabase;
import model.Amministratore;
import model.StatoBagaglio;
import model.StatoVolo;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AmministratoreImplementazionePostgresDAO implements AmministratoreDAO {

    private void addRows(PreparedStatement ps, DefaultTableModel model) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiornaStatoBagaglio(String codice, String nuovoStato) throws SQLException{
        String sql = "UPDATE bagaglio SET stato = ?::statobagaglio WHERE codice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, nuovoStato.replace(" ", "_").toUpperCase());
            stmt.setString(2, codice);
            stmt.executeUpdate();
        }
    }

    public void showFlights(DefaultTableModel flightsModel, Amministratore admin) throws SQLException {
        String sql = "SELECT codice, posti_disponibili, compagnia_aerea, aeroporto_origine, " +
                "aeroporto_destinazione, data, orario, ritardo, stato, numero_gate " +
                "FROM volo WHERE Amministratore = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNomeUtente());
            flightsModel.setRowCount(0);
            addRows(stmt, flightsModel);
        }
    }

    public void searchLuggage(DefaultTableModel model, Amministratore admin, String codice, String tipo, String stato) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT B.codice, B.stato, B.tipo, B.codicefiscalepasseggero " +
                "FROM bagaglio B JOIN prenotazione P ON B.codicefiscalepasseggero = P.codicefiscale JOIN volo V ON P.codicevolo = V.codice " +
                "WHERE V.amministratore = ?");
        List<Object> parametri = new ArrayList<>();
        parametri.add(admin.getNomeUtente());

        if (!codice.isBlank() && !codice.equals("Codice Bagaglio")) {
            query.append(" AND B.codice = ?");
            parametri.add(codice);
        }
        if (!tipo.isBlank()) {
            query.append(" AND B.tipo = ?");
            parametri.add(tipo);
        }
        if (!stato.isBlank()) {
            query.append(" AND B.stato = ?::statobagaglio");
            parametri.add(stato.replace(" ", "_").toUpperCase());
        }

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            addLuggageRows(ps, model);
        }
    }

    public void showLuggage(DefaultTableModel luggageModel, Amministratore admin) throws SQLException {
        String sql = "SELECT B.codice, B.stato, B.tipo, B.codicefiscalepasseggero " +
                "FROM bagaglio B JOIN prenotazione P ON B.codicefiscalepasseggero = P.codicefiscale JOIN volo V ON P.codicevolo = V.codice " +
                "WHERE V.amministratore = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getNomeUtente());
            luggageModel.setRowCount(0);
            addLuggageRows(stmt, luggageModel);
        }
    }

    private void addLuggageRows(PreparedStatement ps, DefaultTableModel model) {
        model.setRowCount(0);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                        rs.getString("codice"),
                        rs.getString("tipo"),
                        rs.getString("codicefiscalepasseggero"),
                        StatoBagaglio.valueOf(rs.getString("stato")).toString()
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        List<Object> parametri = new ArrayList<>();
        parametri.add(admin.getNomeUtente());
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
            parametri.add(LocalDate.parse(data, dateFormatter)); // Se usi java.sql.Date
        }
        if (!orario.isBlank() && !orario.equals("Orario Volo (HH:MM)")) {
            query.append(" AND orario = ?");
            parametri.add(LocalTime.parse(orario).truncatedTo(ChronoUnit.MINUTES)); // HH:mm → HH:mm:ss
        }
        if(!ritardo.isBlank() && !ritardo.equals("Ritardo Volo (HH:MM)")) {
            query.append(" AND ritardo = ?");
            parametri.add(LocalTime.parse(ritardo).truncatedTo(ChronoUnit.MINUTES));
        }
        if(!stato.isBlank()) {
            query.append(" AND stato = ?::stato_volo");
            parametri.add(stato.replace(" ", "_").toUpperCase());
        }
        if(!numGate.isBlank() && !numGate.equals("Numero Gate")) {
            query.append(" AND numero_gate = ?");
            parametri.add(Integer.parseInt(numGate));
        }


        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            addRows(ps,model);
        }
    }
}
