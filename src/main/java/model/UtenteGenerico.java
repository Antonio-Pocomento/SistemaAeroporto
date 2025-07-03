package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code UtenteGenerico} che estende Utente
 */
public class UtenteGenerico extends Utente {
    private final ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<>();

    /**
     * Costruttore di UtenteGenerico.
     *
     * @param nomeUtente     il nome utente
     * @param email          l' email
     * @param passwordUtente la password dell'utente
     */
    public UtenteGenerico(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    /**
     * Getter prenotazioniUtente.
     *
     * @return le prenotazioni dell'utente
     */
    public List<Prenotazione> getPrenotazioniUtente() {
        return prenotazioniUtente;
    }
}
