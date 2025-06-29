package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    public String getCodice() {return codice;}
    public int getNPosti() {return nPosti;}
    public int getPostiDisponibili() {return postiDisponibili;}
    public String getCompagniaAerea() {return compagniaAerea;}
    public String getAeroportoOrigine() {return aeroportoOrigine;}
    public String getAeroportoDestinazione() {return aeroportoDestinazione;}
    public LocalDate getData() {return data;}
    public LocalTime getOrario() {return orario;}
    public LocalTime getRitardo() {return ritardo;}
    public StatoVolo getStato() {return stato;}
    public Integer getNumeroGate() {return numeroGate;}
    public List<Prenotazione> getPrenotazioni() {return prenotazioni;}

    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
        postiDisponibili--;
    }

    // SETTERS
    public void setCompagniaAerea(String compagniaAerea) {this.compagniaAerea = compagniaAerea;}
    public void setAeroportoOrigine(String aeroportoOrigine) {this.aeroportoOrigine = aeroportoOrigine;}
    public void setAeroportoDestinazione(String aeroportoDestinazione) {this.aeroportoDestinazione = aeroportoDestinazione;}
    public void setData(LocalDate data) {this.data = data;}
    public void setOrario(LocalTime orario) {this.orario = orario;}
    public void setRitardo(LocalTime ritardo) {this.ritardo = ritardo;}
    public void setStato(StatoVolo stato) {this.stato = stato;}
    public void setNumeroGate(Integer numeroGate) {this.numeroGate = numeroGate;}
    public void setPostiDisponibili(int posti) {this.postiDisponibili = posti;}

}
