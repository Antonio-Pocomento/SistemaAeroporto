package model;

public class Utente {
    private final String nomeUtente;
    private final String email;
    private final String passwordUtente;

    public Utente(String nomeUtente, String email, String passwordUtente) {
        // Registrazione nuovo utente
        this.nomeUtente = nomeUtente;
        this.email = email;
        this.passwordUtente = passwordUtente;
    }

    public String getNomeUtente() { return nomeUtente; }
    public String getEmail() { return email; }
    public String getPasswordUtente() { return passwordUtente; }

}
