package model;

/**
 * La classe {@code Prenotazione}.
 */
public class Prenotazione {
    private final int numeroBiglietto;
    private final String postoAssegnato;
    private StatoPrenotazione stato;
    private final Passeggero passeggero;
    private final Volo volo;
    private final UtenteGenerico utente;

    /**
     * Costruttore di Prenotazione.
     *
     * @param num        il num di prenotazione
     * @param posto      il posto della prenotazione
     * @param volo       il volo della prenotazione
     * @param passeggero il passeggero della prenotazione
     * @param utente     l'utente che ha eseguito la prenotazione
     */


    public Prenotazione(int num, String posto, Volo volo, Passeggero passeggero, UtenteGenerico utente) {
        this.numeroBiglietto = num;
        this.postoAssegnato = posto;
        this.stato = StatoPrenotazione.IN_ATTESA;
        this.volo = volo;
        this.passeggero = passeggero;
        this.utente = utente;
    }

    /**
     * Setter stato.
     *
     * @param stato lo stato
     */

    public void setStato(StatoPrenotazione stato) {this.stato = stato;}

    /**
     * Getter di numeroBiglietto.
     *
     * @return il numero del biglietto
     */
    public int getNumeroBiglietto() {return numeroBiglietto;}

    /**
     * Getter del volo.
     *
     * @return il volo
     */
    public Volo getVolo() {
        return volo;
    }

    /**
     * Getter del passeggero.
     *
     * @return il passeggero
     */
    public Passeggero getPasseggero() {
        return passeggero;
    }

    /**
     * Getter del posto assegnato.
     *
     * @return il posto assegnato
     */
    public String getPostoAssegnato() {return postoAssegnato;}

    /**
     * Getter dello Stato.
     *
     * @return lo stato della prenotazione
     */
    public String getStato() {return stato.toString();}

    /**
     * Getter di utente.
     *
     * @return l'utente
     */
    public UtenteGenerico getUtente() {
        return utente;
    }
}
