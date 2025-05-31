package dao;

import model.Utente;
import java.sql.*;

public interface UtenteDAO {
    boolean esisteUtente(String nomeUtente) throws SQLException;
    void registraUtente(Utente utente) throws SQLException;
}
