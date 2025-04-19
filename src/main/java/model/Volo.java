package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Volo {
    private int codice;
    private int nPosti;
    private int postiDisponibili;
    private String compagniaAerea;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private LocalDate data;
    private LocalTime orario;
    private LocalTime ritardo;
    private StatoVolo stato;
    private Integer numeroGate;

    private static ArrayList<Volo> voli = new ArrayList<Volo>();
    private ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

    public Volo(int codice, int nPosti, String compagniaAerea, String aeroportoOrigine, String aeroportoDestinazione, LocalDate data,
                LocalTime orario, Integer numeroGate) {
        this.codice = codice;
        this.nPosti = nPosti;
        this.postiDisponibili = nPosti;
        this.compagniaAerea = compagniaAerea;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.data = data;
        this.orario = orario;
        this.ritardo = LocalTime.of(0,0);
        this.stato = StatoVolo.programmato;
        this.numeroGate = numeroGate;
    }

    public static void printVoli(){
        System.out.println("\nVoli: ");
        for(Volo volo : voli){
            System.out.println(volo.codice + " " + volo.postiDisponibili + " " + volo.compagniaAerea + " " + volo.aeroportoOrigine + " " + volo.aeroportoDestinazione + " " + volo.data + " "
            + volo.orario.truncatedTo(ChronoUnit.MINUTES) + " " + volo.ritardo.truncatedTo(ChronoUnit.MINUTES) + " " + volo.stato + " " + volo.numeroGate);
        }
    }

    /// /// GETTERs
    public int getCodice() {return codice;}
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
    public ArrayList<Prenotazione> getPrenotazioni() {return prenotazioni;}
    public Prenotazione getPrenotazione(int numeroBiglietto){
        for(Prenotazione prenotazione : prenotazioni)
            if(prenotazione.getNumeroBiglietto() == numeroBiglietto) return prenotazione;
        System.out.println("\nErrore: Prenotazione per il volo non trovata\n");
        return null;
    }
    /// ///

    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
        postiDisponibili--;
    }
    public void addVolo() {voli.add(this);}

    /// /// SETTERs
    public void setCodice(int codice) {this.codice = codice;}
    public void setData(LocalDate data) {this.data = data;}
    public void setOrario(LocalTime orario) {this.orario = orario;}
    public void setRitardo(LocalTime ritardo) {this.ritardo = ritardo;}
    public void setStato(StatoVolo stato) {this.stato = stato;}
    public void setNumeroGate(int numeroGate) {this.numeroGate = numeroGate;}
    /// ///

}
