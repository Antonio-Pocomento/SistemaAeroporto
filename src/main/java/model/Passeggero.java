package model;

import java.util.ArrayList;

public class Passeggero {
    private String nome;
    private String secondoNome;
    private String cognome;
    private String codiceFiscale;
    private ArrayList<Bagaglio> Bagagli = new ArrayList<Bagaglio>();
    private Prenotazione prenotazione;

    public Passeggero(String nome, String secondoNome, String cognome, String codiceFiscale,Prenotazione prenotazione){
        this.nome = nome;
        this.secondoNome = secondoNome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.prenotazione = prenotazione;
    }

    public void addBagaglio(Bagaglio b){
        Bagagli.add(b);
    }

    public String getNome() {
        return nome;
    }
    public ArrayList<Bagaglio> getBagagli() {return Bagagli;}

    public Bagaglio getBagaglio(int codiceBagaglio){
        for(Bagaglio b : Bagagli){
            if(b.getCodice() == codiceBagaglio){return b;}
        }
        return null;
    }
}
