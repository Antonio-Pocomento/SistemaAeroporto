package dao;

import model.Utente;
import java.sql.*;

public interface UtenteDAO {
    boolean esisteUtente(String nomeUtente) throws SQLException;
    boolean esisteEmail(String email) throws SQLException;
    void registraUtente(Utente utente) throws SQLException;
    Utente loginUtente(String login, String passwordInput) throws SQLException;
}
