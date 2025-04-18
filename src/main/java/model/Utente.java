package model;

public class Utente {
    private String nomeUtente;
    private String email;
    private String passwordUtente;

    public boolean loginUtente(String login, String password) {
        return (nomeUtente.equals(login) || email.equals(login)) && passwordUtente.equals(password);
    }

    public Utente(String nomeUtente, String email, String passwordUtente) {
        // Registrazione nuovo utente
        this.nomeUtente = nomeUtente;
        this.email = email;
        this.passwordUtente = passwordUtente;
    }

    public void visualizzaVoli(){

    }
}
