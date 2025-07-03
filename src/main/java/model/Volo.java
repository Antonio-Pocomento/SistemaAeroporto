package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Volo}
 */
public class Volo {
    private final String codice;
    private final int nPosti;
    private int postiDisponibili;
    private String compagniaAerea;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private LocalDate data;
    private LocalTime orario;
    private LocalTime ritardo;
    private StatoVolo stato;
    private Integer numeroGate;
    private final ArrayList<Prenotazione> prenotazioni = new ArrayList<>();

    /**
     * Costruttore della classe Volo.
     *
     * @param codice                il codice
     * @param nPosti                il numero di posti
     * @param postiDisponibili      il numero di posti disponibili
     * @param compagniaAerea        la compagnia aerea
     * @param aeroportoOrigine      l' aeroporto di origine
     * @param aeroportoDestinazione l' aeroporto di destinazione
     * @param data                  la data
     * @param orario                l' orario di partenza del volo
     * @param ritardo               il ritardo (eventuale)
     * @param stato                 lo stato del volo
     * @param numeroGate            il numero del gate
     */


    public Volo(String codice, int nPosti, int postiDisponibili, String compagniaAerea, String aeroportoOrigine, String aeroportoDestinazione, LocalDate data,
                LocalTime orario, LocalTime ritardo, StatoVolo stato, Integer numeroGate) {
        this.codice = codice;
        this.nPosti = nPosti;
        this.postiDisponibili = postiDisponibili;
        this.compagniaAerea = compagniaAerea;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.data = data;
        this.orario = orario;
        this.ritardo = ritardo;
        this.stato = stato;
        this.numeroGate = numeroGate;
    }

// GETTERS

    /**
     * Getter del codice
     *
     * @return il codice
     */
    public String getCodice() {return codice;}

    /**
     * Getter n posti.
     *
     * @return n posti
     */
    public int getNPosti() {return nPosti;}

    /**
     * Getter posti disponibili.
     *
     * @return posti disponibili
     */
    public int getPostiDisponibili() {return postiDisponibili;}

    /**
     * Getter compagnia aerea.
     *
     * @return compagnia aerea
     */
    public String getCompagniaAerea() {return compagniaAerea;}

    /**
     * Getter aeroporto origine.
     *
     * @return aeroporto origine
     */
    public String getAeroportoOrigine() {return aeroportoOrigine;}

    /**
     * Getter aeroporto destinazione.
     *
     * @return aeroporto destinazione
     */
    public String getAeroportoDestinazione() {return aeroportoDestinazione;}

    /**
     * Getter data.
     *
     * @return data
     */
    public LocalDate getData() {return data;}

    /**
     * Getter orario.
     *
     * @return orario
     */
    public LocalTime getOrario() {return orario;}

    /**
     * Getter ritardo.
     *
     * @return ritardo
     */
    public LocalTime getRitardo() {return ritardo;}

    /**
     * Getter stato.
     *
     * @return stato
     */
    public StatoVolo getStato() {return stato;}

    /**
     * Getter numero gate.
     *
     * @return numero gate
     */

    public Integer getNumeroGate() {return numeroGate;}

    /**
     * Getter prenotazioni.
     *
     * @return prenotazioni
     */
    public List<Prenotazione> getPrenotazioni() {return prenotazioni;}

    /**
     * Aggiunge prenotazione alla lista delle prenotazioni e rimuove un posto disponibile.
     *
     * @param prenotazione la prenotazione
     */
    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
        postiDisponibili--;
    }

// SETTERS

    /**
     * Setter compagnia aerea.
     *
     * @param compagniaAerea la compagnia aerea
     * */

    public void setCompagniaAerea(String compagniaAerea) {this.compagniaAerea = compagniaAerea;}

    /**
     * Setter aeroporto origine.
     *
     * @param aeroportoOrigine l'aeroporto di origine
     */
    public void setAeroportoOrigine(String aeroportoOrigine) {this.aeroportoOrigine = aeroportoOrigine;}

    /**
     * Setter aeroporto destinazione.
     *
     * @param aeroportoDestinazione l'aeroporto di destinazione
     */

    public void setAeroportoDestinazione(String aeroportoDestinazione) {this.aeroportoDestinazione = aeroportoDestinazione;}

    /**
     * Setter data.
     *
     * @param data la data di partenza
     */
    public void setData(LocalDate data) {this.data = data;}

    /**
     * Setter orario.
     *
     * @param orario l'orario di partenza
     */
    public void setOrario(LocalTime orario) {this.orario = orario;}

    /**
     * Setter ritardo.
     *
     * @param ritardo il ritardo (eventuale) del volo
     */
    public void setRitardo(LocalTime ritardo) {this.ritardo = ritardo;}

    /**
     * Setter stato.
     *
     * @param stato lo stato del volo
     */
    public void setStato(StatoVolo stato) {this.stato = stato;}

    /**
     * Setter numero gate.
     *
     * @param numeroGate il numero del gate
     */
    public void setNumeroGate(Integer numeroGate) {this.numeroGate = numeroGate;}

    /**
     * Setter posti disponibili.
     *
     * @param posti posti disponibili
     */
    public void setPostiDisponibili(int posti) {this.postiDisponibili = posti;}

}
