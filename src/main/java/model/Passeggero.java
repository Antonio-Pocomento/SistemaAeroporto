package model;

import java.util.ArrayList;
import java.util.List;

public class Passeggero {
    private final String nome;
    private final String secondoNome;
    private final String cognome;
    private final String codiceFiscale;
    private final ArrayList<Bagaglio> bagagli = new ArrayList<>();

    public Passeggero(String nome, String secondoNome, String cognome, String codiceFiscale){
        this.nome = nome;
        this.secondoNome = secondoNome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
    }

    public void addBagaglio(Bagaglio b){
        bagagli.add(b);
    }

    public String getNome() {
        return nome;
    }
    public List<Bagaglio> getBagagli() {return bagagli;}

    public String getSecondoNome() {
        return secondoNome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
}
