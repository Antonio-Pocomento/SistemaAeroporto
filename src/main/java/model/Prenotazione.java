package model;

public class Prenotazione {
    private final int numeroBiglietto;
    private final String postoAssegnato;
    private StatoPrenotazione stato;
    private final Passeggero passeggero;
    private final Volo volo;
    private final UtenteGenerico utente;

    public Prenotazione(int num, String posto, Volo volo, Passeggero passeggero, UtenteGenerico utente) {
        this.numeroBiglietto = num;
        this.postoAssegnato = posto;
        this.stato = StatoPrenotazione.IN_ATTESA;
        this.volo = volo;
        this.passeggero = passeggero;
        this.utente = utente;
    }

    public void setStato(StatoPrenotazione stato) {this.stato = stato;}

    public int getNumeroBiglietto() {return numeroBiglietto;}
    public Volo getVolo() {
        return volo;
    }
    public Passeggero getPasseggero() {
        return passeggero;
    }
    public String getPostoAssegnato() {return postoAssegnato;}

    public String getStato() {return stato.toString();}

    public UtenteGenerico getUtente() {
        return utente;
    }
}
