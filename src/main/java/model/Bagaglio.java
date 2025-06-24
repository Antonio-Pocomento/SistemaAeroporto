package model;

public class Bagaglio {
    private String codice;
    private StatoBagaglio stato;
    private String tipo;
    private Passeggero passeggero;

    public Bagaglio(String codice, Passeggero passeggero, String tipo) {
        this.codice = codice;
        this.stato = StatoBagaglio.RITIRABILE;
        this.tipo = tipo;
        this.passeggero = passeggero;
    }

    public StatoBagaglio getStato(){return stato;}
    public String getCodice(){return codice;}

    public void setStato(StatoBagaglio stato) {
        this.stato = stato;
        System.out.println("Stato del bagaglio modificato a: "+stato);
    }
}
