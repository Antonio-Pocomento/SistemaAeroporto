package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Amministratore}
 */
public class Amministratore extends Utente {
    private final ArrayList<Volo> voliGestiti = new ArrayList<>();

    /**
     * Costruttore dell'oggetto Amministratore.
     *
     * @param nomeUtente     il nome utente
     * @param email          l' email
     * @param passwordUtente la password dell'utente
     */

    public Amministratore(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    /**
     * Getter voli gestiti.
     *
     * @return i voli gestiti
     */
    public List<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    /**
     * Modifica la data del volo.
     *
     * @param volo il volo da modificare
     * @param data la nuova data
     */
    public void modificaDataVolo(Volo volo, LocalDate data){
        volo.setData(data);
    }

    /**
     * Modifica la compagnia del volo.
     *
     * @param volo      il volo da modificare
     * @param compagnia la nuova compagnia
     */
    public void modificaCompagniaVolo(Volo volo, String compagnia){
        volo.setCompagniaAerea(compagnia);
    }

    /**
     * Modifica l'aeroporto di origine.
     *
     * @param volo      il volo da modificare
     * @param aeroporto il nuovo aeroporto
     */
    public void modificaAeroportoOrigine(Volo volo, String aeroporto){
        volo.setAeroportoOrigine(aeroporto);
    }

    /**
     * Modifica l'aeroporto di destinazione.
     *
     * @param volo      il volo da modificare
     * @param aeroporto il nuovo aeroporto
     */
    public void modificaAeroportoDestinazione(Volo volo, String aeroporto){
        volo.setAeroportoDestinazione(aeroporto);
    }

    /**
     * Modifica i posti del volo.
     *
     * @param volo  il volo da modificare
     * @param posti il nuovo numero di posti disponibili
     */
    public void modificaPostiVolo(Volo volo, int posti){
        volo.setPostiDisponibili(posti);
    }

    /**
     * Modifica l'orario del volo.
     *
     * @param volo   il volo da modificare
     * @param orario il nuovo orario di partenza del volo
     */
    public void modificaOrarioVolo(Volo volo, LocalTime orario){
        volo.setOrario(orario);
    }

    /**
     * Modifica il ritardo del volo.
     *
     * @param volo    il volo da modificare
     * @param ritardo il nuovo ritardo
     */
    public void modificaRitardoVolo(Volo volo, LocalTime ritardo){
        volo.setRitardo(ritardo);
    }

    /**
     * Modifica lo stato del volo.
     *
     * @param volo  il volo da modificare
     * @param stato il nuovo stato del volo
     */
    public void modificaStatoVolo(Volo volo,StatoVolo stato){
        volo.setStato(stato);
    }

    /**
     * Modifica il numeroGate del volo.
     *
     * @param volo       il volo da modificare
     * @param numeroGate il nuovo numeroGate
     */
    public void modificaNumeroGateVolo(Volo volo, Integer numeroGate){
        volo.setNumeroGate(numeroGate);
    }

    /**
     * Aggiunge un volo alla lista dei voli gestiti dall'amministratore
     *
     * @param volo volo da gestire
     */
    public void gestisciVolo(Volo volo){
        voliGestiti.add(volo);
    }
}
