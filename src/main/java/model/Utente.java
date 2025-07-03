package model;

/**
 * La classe {@code Utente}
 */
public class Utente {
    private final String nomeUtente;
    private final String email;
    private final String passwordUtente;

    /**
     * Costruttore della classe Utente.
     *
     * @param nomeUtente     il nome utente
     * @param email          l' email
     * @param passwordUtente la password dell'utente
     */
    public Utente(String nomeUtente, String email, String passwordUtente) {
        // Registrazione nuovo utente
        this.nomeUtente = nomeUtente;
        this.email = email;
        this.passwordUtente = passwordUtente;
    }

    /**
     * Getter nome utente.
     *
     * @return il nome utente
     */

    public String getNomeUtente() { return nomeUtente; }

    /**
     * Getter email.
     *
     * @return l'email
     */
    public String getEmail() { return email; }

    /**
     * Getter password utente.
     *
     * @return la password utente
     */
    public String getPasswordUtente() { return passwordUtente; }

}
