package model;

import java.util.ArrayList;
import java.util.List;

public class UtenteGenerico extends Utente {
    private final ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<>();

    public UtenteGenerico(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public List<Prenotazione> getPrenotazioniUtente() {
        return prenotazioniUtente;
    }
}
