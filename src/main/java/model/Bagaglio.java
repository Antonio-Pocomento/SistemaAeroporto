package model;

public class Bagaglio {
    private final int codice;
    private StatoBagaglio stato;
    private final String tipo;
    private final Passeggero passeggero;

    public Bagaglio(int codice, Passeggero passeggero, String tipo) {
        this.codice = codice;
        this.stato = StatoBagaglio.CARICATO;
        this.tipo = tipo;
        this.passeggero = passeggero;
    }

    public StatoBagaglio getStato(){return stato;}
    public int getCodice(){return codice;}

    public void setStato(StatoBagaglio stato) {
        this.stato = stato;
    }

    public String getCodiceVisualizzato() {
        // Base36 in maiuscolo
        return String.format("BAG-%s", Integer.toString(codice, 36).toUpperCase());
    }

    public String getTipo() {return tipo;}

    public Passeggero getPasseggero() {
        return passeggero;
    }
}
