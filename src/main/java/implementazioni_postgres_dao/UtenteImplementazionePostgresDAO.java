package implementazioni_postgres_dao;

import dao.UtenteDAO;
import model.Utente;
import java.sql.*;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/Aeroporto";
    private final String USER = "postgres";
    private final String PASSWORD = "password";

    public boolean esisteUtente(String nomeUtente) throws SQLException {
        String sql = "SELECT nomeUtente FROM Utente WHERE nomeUtente = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUtente);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void registraUtente(Utente utente) throws SQLException {
        String insertUtente = "INSERT INTO Utente (nomeUtente, email, passwordUtente) VALUES (?, ?, ?)";
        //String insertGenerico = "INSERT INTO UtenteGenerico (nomeUtente) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(insertUtente)) {

                stmt1.setString(1, utente.getNomeUtente());
                stmt1.setString(2, utente.getEmail());
                stmt1.setString(3, utente.getPasswordUtente());
                stmt1.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
