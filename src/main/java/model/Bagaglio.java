package model;

public class Bagaglio {
    private int codice;
    private StatoBagaglio stato;
    private Passeggero passeggero;

    public Bagaglio(int codice, Passeggero passeggero) {
        this.codice = codice;
        this.stato = StatoBagaglio.ritirabile;
        this.passeggero = passeggero;
    }

    public StatoBagaglio getStato(){return stato;}
    public int getCodice(){return codice;}

    public void setStato(StatoBagaglio stato) {
        this.stato = stato;
        System.out.println("Stato del bagaglio modificato a: "+stato);
    }

    public void printBagaglio(){
        System.out.println("Bagaglio: ");
        System.out.println(codice+" "+stato);
    }
}
