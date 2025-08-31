package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe {@code ConnessioneDatabase} si occupa della connessione con il database
 */
public class ConnessioneDatabase {

    // ATTRIBUTI
    private static ConnessioneDatabase instance;
    public Connection connection = null;
    private static final String NOME = "postgres";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:postgresql://localhost:5432/Aeroporto2";
    private static final String DRIVER = "org.postgresql.Driver";

    // COSTRUTTORE
    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, NOME, PASSWORD);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    /**
     * Getter instance.
     *
     * @return l'istanza
     * @throws SQLException l'eccezione sql
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }
}