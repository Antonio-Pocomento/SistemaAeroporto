package model;

import java.sql.Time;
import java.util.Random;
import java.util.Scanner;

public class Prenotazione {
    private int numeroBiglietto = 1;
    private int postoAssegnato = 1;
    private StatoPrenotazione stato;
    private Passeggero passeggero;
    private Volo volo;
    private UtenteGenerico utente;

    private static int prenotazioniEffettuate = 0;

    public Prenotazione(Volo volo, UtenteGenerico utente) {
        this.numeroBiglietto = numeroBiglietto+prenotazioniEffettuate;
        prenotazioniEffettuate++;
        this.postoAssegnato = postoAssegnato;
        this.stato = StatoPrenotazione.inAttesa;
        // VALORI TEST
        checkIn("Ciro","","Rampone","TestCodice",this);
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

    public void checkIn(String nome, String secondoNome, String cognome, String codiceFiscale, Prenotazione prenotazione) {
        Scanner sc = new Scanner(System.in);
        this.passeggero = new Passeggero(nome, secondoNome, cognome,codiceFiscale,prenotazione);
        System.out.println("\nQuanti bagagli trasporta?\nNumero di bagagli: ");
        int nBag = sc.nextInt();
        for(int i = 1; i < nBag+1; i++){
            Bagaglio b = new Bagaglio(i, prenotazione.getPasseggero());
            passeggero.addBagaglio(b);
        }
    }

    public void printPrenotazione() {
        System.out.println("Prenotazione #" + numeroBiglietto);
        System.out.println("Posto: " +postoAssegnato+" Stato: "+stato);
    }
}
