package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Amministratore extends Utente {
    private ArrayList<Volo> voliGestiti = new ArrayList<>();

    public Amministratore(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public List<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    public void modificaCodiceVolo(Volo volo, int codice){
        volo.setCodice(codice);
    }
    public void modificaDataVolo(Volo volo, LocalDate data){
        volo.setData(data);
    }
    public void modificaOrarioVolo(Volo volo, LocalTime orario){
        volo.setOrario(orario);
    }
    public void modificaRitardoVolo(Volo volo, LocalTime ritardo){
        volo.setRitardo(ritardo);
    }
    public void modificaStatoVolo(Volo volo,StatoVolo stato){
        volo.setStato(stato);
    }
    public void modificaNumeroGateVolo(Volo volo, int numeroGate){
        volo.setNumeroGate(numeroGate);
    }

    public void modificaStatoBagaglio(Bagaglio bagaglio, StatoBagaglio stato){
        bagaglio.setStato(stato);
    }

    public void gestisciVolo(Volo volo){
        voliGestiti.add(volo);
    }
}
