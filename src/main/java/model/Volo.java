package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Volo {
    private int codice;
    private String compagniaAerea;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private Date data;
    private LocalTime orario;
    private LocalTime ritardo;
    private String stato;
    private Integer numeroGate;

    private static ArrayList<Volo> voli = new ArrayList<Volo>();
    private ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

    public Volo(int codice, String compagniaAerea, String aeroportoOrigine, String aeroportoDestinazione, Date data,
                LocalTime orario, LocalTime ritardo, String stato, Integer numeroGate) {
        this.codice = codice;
        this.compagniaAerea = compagniaAerea;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.data = data;
        this.orario = orario;
        this.ritardo = ritardo;
        this.stato = stato;
        this.numeroGate = numeroGate;
        voli.add(this);
    }

    public static void printVoli(){
        System.out.println("\nVoli: ");
        for(Volo volo : voli){
            System.out.println(volo.codice + " " + volo.compagniaAerea + " " + volo.aeroportoOrigine + " " + volo.aeroportoDestinazione + " " + volo.data + " "
            + volo.orario + " " + volo.ritardo + " " + volo.stato + " " + volo.numeroGate);

        }
    }

    public static ArrayList<Volo> getVoli(){
        return voli;
    }

    public String getAeroportoOrigine() {
        return aeroportoOrigine;
    }

    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public int getCodice() {
        return codice;
    }

    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setNumeroGate(int numeroGate) {
        this.numeroGate = numeroGate;
    }

}
