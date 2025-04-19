package model;

import java.util.Scanner;

public class Prenotazione {
    private int numeroBiglietto = 1;
    private int postoAssegnato;
    private StatoPrenotazione stato;
    private Passeggero passeggero;
    private Volo volo;
    private UtenteGenerico utente;

    private static int prenotazioniEffettuate = 0;

    public Prenotazione(Volo volo, UtenteGenerico utente) {
        this.numeroBiglietto = numeroBiglietto+prenotazioniEffettuate;
        prenotazioniEffettuate++;
        this.postoAssegnato = volo.getNPosti()-volo.getPostiDisponibili()+1;
        this.stato = StatoPrenotazione.inAttesa;
        checkIn();
        this.volo = volo;
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
    public StatoPrenotazione getStatoPrenotazione() {
        return stato;
    }

    public void checkIn() {
        // Valori di prova, da sostituire con input e interfaccia
        passeggero = new Passeggero("Ciro", "", "Rampone","TestCodice",this);
        for(int i = 1; i < 3; i++){
            Bagaglio b = new Bagaglio(i, passeggero);
            passeggero.addBagaglio(b);
        }
    }

    public void printPrenotazione() {
        System.out.println("[#Biglietto]: " + numeroBiglietto);
        System.out.println("Posto: " +postoAssegnato+" Stato: "+stato);
    }
}
