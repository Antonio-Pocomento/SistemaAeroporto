package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Amministratore extends Utente {
    private ArrayList<Volo> voliGestiti = new ArrayList<Volo>();

    public Amministratore(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public ArrayList<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    public Volo getVoloGestito(int codice)
    {
        for(Volo v : voliGestiti)
        {
            if(v.getCodice() == codice) return v;
        }
        System.out.println("Errore: Volo non trovato");
        return null;
    }

    public void inserisciVolo(Volo volo){
        volo.addVolo();
        System.out.println("Volo aggiunto");
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

    public void visualizzaBagagliSmarriti()
    {
        System.out.println("\nBagagli smarriti per i voli attualmente gestiti:");
        for(Volo volo : voliGestiti)
        {
            ArrayList<Prenotazione> prenotazioni = volo.getPrenotazioni();
            for(Prenotazione prenotazione : prenotazioni)
            {
                ArrayList<Bagaglio> bagagliPasseggero = prenotazione.getPasseggero().getBagagli();
                for(Bagaglio bagaglio : bagagliPasseggero)
                {
                    if(bagaglio.getStato() == StatoBagaglio.smarrito)
                    {
                        bagaglio.printBagaglio();
                    }
                }
            }
        }
    }

    public void modificaStatoBagaglio(Bagaglio bagaglio, StatoBagaglio stato){
        bagaglio.setStato(stato);
    }

    public void gestisciVolo(Volo volo){
        voliGestiti.add(volo);
    }
}
