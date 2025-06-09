package dao;

import model.Amministratore;
import model.Volo;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public interface AmministratoreDAO {
    void showFlights(DefaultTableModel flightsModel, Amministratore admin) throws SQLException;
    void addFlights(Amministratore admin) throws SQLException;
    void aggiornaVolo(Volo volo) throws SQLException;
}
