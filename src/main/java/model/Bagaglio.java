package model;

public class Bagaglio {
    private int codice;
    private StatoBagaglio stato;
    private Passeggero passeggero;

    public Bagaglio(int codice, StatoBagaglio stato, Passeggero passeggero) {
        this.codice = codice;
        this.stato = stato;
        this.passeggero = passeggero;
    }

    public StatoBagaglio getStato(){
        return stato;
    }

    public void setStato(StatoBagaglio stato) {
        this.stato = stato;
    }

    public void printBagaglio(){
        System.out.println(codice+" "+stato);
    }
}
