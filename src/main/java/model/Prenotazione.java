package model;

public class Prenotazione {
    private int numeroBiglietto;
    private int postoAssegnato;
    private StatoPrenotazione stato;
    private Passeggero passeggero;
    private Volo volo;
    private UtenteGenerico utente;

    public Prenotazione(int numeroBiglietto, int postoAssegnato, StatoPrenotazione stato, Passeggero passeggero, Volo volo, UtenteGenerico utente) {
        this.numeroBiglietto = numeroBiglietto;
        this.postoAssegnato = postoAssegnato;
        this.stato = stato;
        this.passeggero = passeggero;
        this.volo = volo;
        this.utente = utente;
    }

    public void setStato(StatoPrenotazione stato) {
        this.stato = stato;
    }

    public Volo getVolo() {
        return volo;
    }

    public static Passeggero checkIn(){
        return new Passeggero("Ciro", "", "Rampone","CRewiqueuoq",null);
    }

    public Passeggero getPasseggero() {
        return passeggero;
    }
    public StatoPrenotazione getStatoPrenotazione() {
        return stato;
    }

    public void printPrenotazione() {
        System.out.println(numeroBiglietto+" "+postoAssegnato+" "+stato);
    }
}
