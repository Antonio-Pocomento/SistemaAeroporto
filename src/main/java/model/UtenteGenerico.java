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

    public void prenotaVolo(Volo volo) {
        if(!volo.getAeroportoOrigine().equals("Napoli")) {
            System.out.println("Non è possibile prenotare voli in arrivo");
            return;
        }
        // Bagagli

        Passeggero passeggero = Prenotazione.checkIn();
        Bagaglio b = new Bagaglio(1,StatoBagaglio.caricato, passeggero);
        passeggero.addBagaglio(b);
        Prenotazione prenotazione = new Prenotazione(1,100,StatoPrenotazione.inAttesa,passeggero,volo,this);
        prenotazioniUtente.add(prenotazione);
        volo.addPrenotazione(prenotazione);
        passeggero.setPrenotazione(prenotazione);
    }

    public void cercaPrenotazioni(int codiceVolo) {
        for(Prenotazione prenotazione : prenotazioniUtente) {
            if(prenotazione.getVolo().getCodice() == codiceVolo) {
                //print
            }
        }
    }

    public void cercaPrenotazioni(String nomePasseggero) {
        for(Prenotazione prenotazione : prenotazioniUtente) {
            if(prenotazione.getPasseggero().getNome() == nomePasseggero) {
                //print
            }
        }
    }

    public void printPrenotazioni() {
        for(Prenotazione prenotazione : prenotazioniUtente) {
            prenotazione.printPrenotazione();
        }
    }

    // Da modificare
    public void segnalaSmarrimento(){
        for(Prenotazione prenotazione : prenotazioniUtente) {
            if(prenotazione.getStatoPrenotazione() == StatoPrenotazione.confermata || prenotazione.getStatoPrenotazione() == StatoPrenotazione.inAttesa) {
                ArrayList<Bagaglio> bagagli = prenotazione.getPasseggero().getBagagli();
                for(Bagaglio bagaglio : bagagli) {
                    bagaglio.setStato(StatoBagaglio.smarrito);
                }
            }
        }
    }

    public void modificaPrenotazione(Prenotazione prenotazione) {
        prenotazione.setStato(StatoPrenotazione.confermata);
    }
}
