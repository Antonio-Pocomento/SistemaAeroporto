package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Amministratore extends Utente {
    private final ArrayList<Volo> voliGestiti = new ArrayList<>();

    public Amministratore(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public List<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    public void modificaDataVolo(Volo volo, LocalDate data){
        volo.setData(data);
    }
    public void modificaCompagniaVolo(Volo volo, String compagnia){
        volo.setCompagniaAerea(compagnia);
    }
    public void modificaAeroportoOrigine(Volo volo, String aeroporto){
        volo.setAeroportoOrigine(aeroporto);
    }
    public void modificaAeroportoDestinazione(Volo volo, String aeroporto){
        volo.setAeroportoDestinazione(aeroporto);
    }
    public void modificaPostiVolo(Volo volo, int posti){
        volo.setPostiDisponibili(posti);
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
    public void modificaNumeroGateVolo(Volo volo, Integer numeroGate){
        volo.setNumeroGate(numeroGate);
    }

    public void gestisciVolo(Volo volo){
        voliGestiti.add(volo);
    }
}
