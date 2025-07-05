package dao;

import model.Utente;
import java.sql.*;

/**
 * L'interfaccia {@code UtenteDAO}
 */
public interface UtenteDAO {
    /**
     * Esiste l'utente
     *
     * @param nomeUtente    il nome utente
     * @return              il booleano
     * @throws SQLException l'eccezione sql
     */
    boolean esisteUtente(String nomeUtente) throws SQLException;

    /**
     * Esiste l'email.
     *
     * @param email         l'email
     * @return              il booleano
     * @throws SQLException l'eccezione sql
     */
    boolean esisteEmail(String email) throws SQLException;

    /**
     * Registra utente.
     *
     * @param utente        l'utente
     * @throws SQLException l'eccezione sql
     */
    void registraUtente(Utente utente) throws SQLException;

    /**
     * Login utente.
     *
     * @param login         login
     * @param passwordInput l'input password
     * @return              l'utente
     * @throws SQLException l'eccezione sql
     */
    Utente loginUtente(String login, String passwordInput) throws SQLException;
}
