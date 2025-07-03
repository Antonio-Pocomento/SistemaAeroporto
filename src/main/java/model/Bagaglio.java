package model;

/**
 * La classe {@code Bagaglio}
 */
public class Bagaglio {
    private final int codice;
    private StatoBagaglio stato;
    private final String tipo;
    private final Passeggero passeggero;

    /**
     * Costruttore del Bagaglio.
     *
     * @param codice     il codice del bagaglio
     * @param passeggero il passeggero che possiede il bagaglio
     * @param tipo       il tipo del bagaglio
     */
    public Bagaglio(int codice, Passeggero passeggero, String tipo) {
        this.codice = codice;
        this.stato = StatoBagaglio.CARICATO;
        this.tipo = tipo;
        this.passeggero = passeggero;
    }

    /**
     * Getter stato.
     *
     * @return lo stato del bagaglio
     */
    public StatoBagaglio getStato(){return stato;}

    /**
     * Getter codice .
     *
     * @return il codice del bagaglio
     */
    public int getCodice(){return codice;}

    /**
     * Setter stato.
     *
     * @param stato lo stato del bagaglio
     */
    public void setStato(StatoBagaglio stato) {
        this.stato = stato;
    }

    /**
     * Getter codice visualizzato.
     * Converte il codice del bagaglio nel codice visibile nella GUI
     *
     *
     * @return il codice visualizzato
     */
    public String getCodiceVisualizzato() {
        // Base36 in maiuscolo
        return String.format("BAG-%s", Integer.toString(codice, 36).toUpperCase());
    }

    /**
     * Getter tipo.
     *
     * @return il tipo del Bagaglio
     */
    public String getTipo() {return tipo;}

    /**
     * Getter passeggero.
     *
     * @return il passeggero a cui appartiene il bagaglio
     */
    public Passeggero getPasseggero() {
        return passeggero;
    }
}
