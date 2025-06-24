package implementazioni_postgres_dao;

import dao.UtenteGenericoDAO;
import database.ConnessioneDatabase;
import model.*;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UtenteGenericoImplementazionePostgresDAO implements UtenteGenericoDAO {
    private static int testval = 21;

    public void showFlights(DefaultTableModel flightsModel) throws SQLException {
        String sql = "SELECT codice, posti_disponibili, compagnia_aerea, aeroporto_origine, " +
                "aeroporto_destinazione, data, orario, ritardo, stato, numero_gate " +
                "FROM volo";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            flightsModel.setRowCount(0);
            addRows(stmt, flightsModel);
        }
    }

    public void showLuggage(DefaultTableModel luggageModel, UtenteGenerico utente) throws SQLException{
        String sql = "SELECT B.codice, B.stato, B.tipo, B.codicefiscalepasseggero " +
                "FROM bagaglio B JOIN prenotazione P ON B.codicefiscalepasseggero = P.codicefiscale " +
                "WHERE P.nomeutente = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utente.getNomeUtente());
            luggageModel.setRowCount(0);
            addLuggageRows(stmt, luggageModel);
        }
    }

    public void segnalaBagaglio(String codice) throws SQLException {
        String sql = "UPDATE bagaglio SET stato = ?::statobagaglio WHERE codice = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "SMARRITO");
            stmt.setString(2, codice);
            stmt.executeUpdate();
        }
    }

    public void modificaPrenotazione(int numeroBiglietto, String stato) throws SQLException{
        String sql = "UPDATE prenotazione SET stato = ?::statoprenotazione WHERE numerobiglietto = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, stato.replace(" ", "_").toUpperCase());
            stmt.setInt(2, numeroBiglietto);
            stmt.executeUpdate();
        }

    }

    public void searchLuggage(DefaultTableModel model, UtenteGenerico utente, String codice, String tipo, String stato) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT B.codice, B.stato, B.tipo, B.codicefiscalepasseggero " +
                "FROM bagaglio B JOIN prenotazione P ON B.codicefiscalepasseggero = P.codicefiscale " +
                "WHERE P.nomeutente = ?");
        List<Object> parametri = new ArrayList<>();
        parametri.add(utente.getNomeUtente());

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

    public void ricercaVolo(DefaultTableModel model, String codice, String posti,
                            String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                            String data, String orario, String ritardo, String  stato, String numGate) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM volo WHERE 1=1");
        List<Object> parametri = new ArrayList<>();
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

    public void showReservations(DefaultTableModel reservationModel, UtenteGenerico utente) throws SQLException {
        String sql = "SELECT PR.numerobiglietto, PR.postoassegnato, PA.nome, PR.codicevolo, " +
                "PR.stato " + "FROM prenotazione PR JOIN passeggero PA ON PR.codicefiscale = PA.codicefiscale " +
                "WHERE PR.nomeutente = ?";

        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utente.getNomeUtente());

            reservationModel.setRowCount(0);
            addReservationRows(stmt, reservationModel);
        }
    }

    private void addReservationRows(PreparedStatement ps, DefaultTableModel model) {
        model.setRowCount(0);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("numerobiglietto"),
                        rs.getString("postoassegnato"),
                        rs.getString("nome"),
                        rs.getString("codicevolo"),
                        StatoPrenotazione.valueOf(rs.getString("stato")).toString(),
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ricercaPrenotazione(DefaultTableModel model, UtenteGenerico utente, String codiceVolo, String nomePasseggero) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM prenotazione PR JOIN passeggero PA ON PR.codicefiscale = PA.codicefiscale " +
                "WHERE PR.nomeutente = ?");
        List<Object> parametri = new ArrayList<>();
        parametri.add(utente.getNomeUtente());

        if (!codiceVolo.isBlank() && !codiceVolo.equals("Codice Volo Prenotato")) {
            query.append(" AND PR.codicevolo = ?");
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
            addReservationRows(ps,model);
        }
    }

    public void confirmReservation(String nome, String secondoNome, String cognome, String codiceFiscale, List<String> tipiBagagli, UtenteGenerico utente, String codiceVolo) throws SQLException{
        try (Connection conn = ConnessioneDatabase.getInstance().connection) {
            conn.setAutoCommit(false);
            try {
                addPassenger(conn, nome, secondoNome, cognome, codiceFiscale);
                addReservation(conn, codiceVolo, utente.getNomeUtente(), codiceFiscale);
                for(String tipo : tipiBagagli) {
                    addBagaglio(conn,tipo,codiceFiscale);
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Errore, eseguito rollback: " + e.getMessage());
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        }
    }

    private void addPassenger(Connection conn, String nome, String secondoNome, String cognome, String codiceFiscale) throws SQLException {
        String sql = "INSERT INTO passeggero (codicefiscale, nome, secnome, cognome) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codiceFiscale);
            stmt.setString(2, nome);
            stmt.setString(3, secondoNome);
            stmt.setString(4, cognome);
            stmt.executeUpdate();
        }
    }

    private void addReservation(Connection conn, String codiceVolo, String nomeUtente, String codiceFiscale) throws SQLException {
        String sql = "INSERT INTO prenotazione (postoassegnato, stato, codicevolo, nomeutente, codicefiscale) VALUES (?, ?::statoprenotazione, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "postoassegnato");
            stmt.setString(2, "IN_ATTESA");
            stmt.setString(3, codiceVolo);
            stmt.setString(4, nomeUtente);
            stmt.setString(5, codiceFiscale);
            stmt.executeUpdate();
        }
    }

    private void addBagaglio(Connection conn, String tipo, String codiceFiscale) throws SQLException {
        String sql = "INSERT INTO bagaglio (codice, stato, tipo ,codicefiscalepasseggero) VALUES (?, ?::statobagaglio, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(testval));
            stmt.setString(2, "CARICATO");
            stmt.setString(3, tipo);
            stmt.setString(4, codiceFiscale);
            stmt.executeUpdate();
            testval++;
        }
    }
}
