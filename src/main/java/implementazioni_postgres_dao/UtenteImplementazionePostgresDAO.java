package implementazioni_postgres_dao;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.Amministratore;
import model.Utente;
import model.UtenteGenerico;

import java.sql.*;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    public boolean esisteUtente(String nomeUtente) throws SQLException {
        String sql = "SELECT 1 FROM Utente WHERE nomeUtente = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUtente);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean esisteEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM Utente WHERE email = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public Utente loginUtente(String login, String passwordInput) throws SQLException {
        String sql = "SELECT nomeUtente, email, passwordUtente, ruolo FROM Utente WHERE (nomeUtente = ? OR email = ?) AND passwordUtente = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, login);
            ps.setString(3, passwordInput);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nomeUtente = rs.getString("nomeUtente");
                    String email = rs.getString("email");
                    String password = rs.getString("passwordUtente");

                    // Determina il ruolo
                    if (rs.getString("ruolo").equals("amministratore")) {
                        return new Amministratore(nomeUtente, email, password);
                    } else if (rs.getString("ruolo").equals("generico")) {
                        return new UtenteGenerico(nomeUtente, email, password);
                    } else {
                        return null; // Nessun ruolo valido trovato
                    }
                } else {
                    return null; // Login fallito
                }
            }
        }
    }

    private boolean isAmministratore(String nomeUtente, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM Amministratore WHERE nomeUtente = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private boolean isUtenteGenerico(String nomeUtente, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM UtenteGenerico WHERE nomeUtente = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public void registraUtente(Utente utente) throws SQLException {
        String insertUtente = "INSERT INTO Utente (nomeUtente, email, passwordUtente, ruolo) VALUES (?, ?, ?, ?::ruolo_utente)";
        //String insertGenerico = "INSERT INTO UtenteGenerico (nomeUtente) VALUES (?)";

        try (Connection conn = ConnessioneDatabase.getInstance().connection) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(insertUtente);
                 /*PreparedStatement stmt2 = conn.prepareStatement(insertGenerico)*/) {

                stmt1.setString(1, utente.getNomeUtente());
                stmt1.setString(2, utente.getEmail());
                stmt1.setString(3, utente.getPasswordUtente());
                stmt1.setString(4, "generico");
                stmt1.executeUpdate();

                //stmt2.setString(1, utente.getNomeUtente());
                //stmt2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
