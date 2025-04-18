package model;

import java.time.LocalTime;
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
    }

    public void printVoli(){

    }

    // getVoli

}
