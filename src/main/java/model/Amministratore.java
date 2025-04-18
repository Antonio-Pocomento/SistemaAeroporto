package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Amministratore extends Utente {
    private ArrayList<Volo> voliGestiti = new ArrayList<Volo>();

    public Amministratore(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public void inserisciVolo(int codice, String compagniaAerea, String aeroportoOrigine, String aeroportoDestinazione, Date data,
                              LocalTime orario, LocalTime ritardo, String stato, Integer numeroGate){
        new Volo(codice,compagniaAerea,aeroportoOrigine,aeroportoDestinazione,data,orario,ritardo,stato,numeroGate);
    }

    // TEST
    public void modificaVolo(Volo volo){
        volo.setCodice(3);
        volo.setNumeroGate(13);
    }

    // ???
    public void visualizzaBagagliSmarriti()
    {
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

    // TEST
    public void modificaStatoBagaglio(){
        for(Volo volo : voliGestiti)
        {
            ArrayList<Prenotazione> prenotazioni = volo.getPrenotazioni();
            for(Prenotazione prenotazione : prenotazioni)
            {
                ArrayList<Bagaglio> bagagliPasseggero = prenotazione.getPasseggero().getBagagli();
                for(Bagaglio bagaglio : bagagliPasseggero)
                {
                    bagaglio.setStato(StatoBagaglio.caricato);
                }
            }
        }
    }

    public void gestisciVolo(Volo volo){
        voliGestiti.add(volo);
    }
}
