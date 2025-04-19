package model;

import java.util.ArrayList;

public class UtenteGenerico extends Utente {
    private ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<Prenotazione>();

    public UtenteGenerico(String nomeUtente, String email, String passwordUtente) {
        super(nomeUtente, email, passwordUtente);
    }

    public ArrayList<Prenotazione> getPrenotazioniUtente() {
        return prenotazioniUtente;
    }

    public Prenotazione getPrenotazioneUtente(int numeroBiglietto) {
        for (Prenotazione p : prenotazioniUtente) {
            if(p.getNumeroBiglietto() == numeroBiglietto) return p;
        };
        System.out.println("\nErrore: Prenotazione dell'utente non trovata\n");
        return null;
    }

    public void prenotaVolo(Volo volo) {
        if(!volo.getAeroportoOrigine().equals("Napoli")) {
            System.out.println("Non è possibile prenotare voli in arrivo");
            return;
        }
        if(volo.getPostiDisponibili()<=0)
        {
            System.out.println("Posti per questo volo esauriti");
            return;
        }
        // VALORI TEST
        Prenotazione prenotazione = new Prenotazione(volo,this);
        prenotazioniUtente.add(prenotazione);
        volo.addPrenotazione(prenotazione);
        System.out.println("\nPrenotazione aggiunta");
    }

    public void cercaPrenotazioni(int codiceVolo) {
        System.out.println("\nPrenotazioni per il volo "+codiceVolo+":");
        for(Prenotazione prenotazione : prenotazioniUtente) {
            if(prenotazione.getVolo().getCodice() == codiceVolo) {
                prenotazione.printPrenotazione();
            }
        }
    }

    public void cercaPrenotazioni(String nomePasseggero) {
        System.out.println("\nPrenotazioni per il passeggero "+nomePasseggero+":");
        for(Prenotazione prenotazione : prenotazioniUtente) {
            if(prenotazione.getPasseggero().getNome().equals(nomePasseggero)) {
                prenotazione.printPrenotazione();
            }
        }
    }

    public void segnalaSmarrimento(Bagaglio bagaglio){
        bagaglio.setStato(StatoBagaglio.smarrito);
    }

    public void modificaPrenotazione(Prenotazione prenotazione, StatoPrenotazione statoPrenotazione) {
        prenotazione.setStato(statoPrenotazione);
    }
}
