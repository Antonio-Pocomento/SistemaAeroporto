package model;

import java.time.LocalTime;
import java.util.Date;

public class Amministratore extends Utente {
    public Amministratore(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public void inserisciVolo(int codice, String compagniaAerea, String aeroportoOrigine, String aeroportoDestinazione, Date data,
                              LocalTime orario, LocalTime ritardo, String stato, Integer numeroGate){
        new Volo(codice,compagniaAerea,aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
    }

    public void modificaVolo(Volo volo){

    }
}
