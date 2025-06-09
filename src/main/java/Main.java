import model.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    // Nessuna gestione di errore per valori null
    // Main puramente di prova
    public static void main(String[] args) {
        /*System.out.println("\nBenvenuto. Accedi utilizzando il tuo Nome Utente o Email e la Password.");
        System.out.println("Se non hai un account utente, puoi crearne uno!");
        System.out.println("\nCreazione account admin");
        Amministratore admin = new Amministratore("admin", "admin@gmail.com", "admin");
        System.out.println("Login con account admin");
        if(admin.loginUtente("admin", "admin"))
        {
            System.out.println("\nAccesso riuscito!");
            Volo volo1 = new Volo(1,150,"AEROITALIA","Napoli","Milano",
                    LocalDate.now(), LocalTime.now(),1);
            Volo volo2 = new Volo(2,150,"AEROITALIA","Napoli","Torino",
                    LocalDate.now(), LocalTime.now(),3);
            Volo volo3 = new Volo(3,250,"ITALIA TRASPORTO AEREO","Palermo","Napoli",
                    LocalDate.now(), LocalTime.now(),null);
            admin.visualizzaVoli();
            admin.inserisciVolo(volo1);
            admin.inserisciVolo(volo2);
            admin.inserisciVolo(volo3);
            admin.visualizzaVoli();
            admin.modificaCodiceVolo(volo1,111);
            admin.modificaRitardoVolo(volo2,LocalTime.of(1,0));
            admin.modificaNumeroGateVolo(volo2,8);
            admin.visualizzaVoli();
            admin.gestisciVolo(volo1);
            System.out.println("\nCreazione account utente Ciro");
            if(admin.loginUtente("admin", "admin"))
            {
                System.out.println("\nAccesso riuscito!");
                UtenteGenerico utente = new UtenteGenerico("Ciro", "ciro@gmail.com", "ciro");
                utente.prenotaVolo(volo3);
                utente.prenotaVolo(volo1);
                utente.prenotaVolo(volo2);
                utente.visualizzaVoli();
                utente.cercaPrenotazioni(111);
                utente.cercaPrenotazioni("Ciro");
                utente.segnalaSmarrimento(utente.getPrenotazioneUtente(1).getPasseggero().getBagaglio(1));
                admin.visualizzaBagagliSmarriti();
                admin.modificaStatoBagaglio(admin.getVoloGestito(111).getPrenotazione(1).getPasseggero().getBagaglio(1),StatoBagaglio.caricato);
                admin.visualizzaBagagliSmarriti();
                utente.modificaPrenotazione(utente.getPrenotazioneUtente(1), StatoPrenotazione.cancellata);
                utente.cercaPrenotazioni("Ciro");
                utente.prenotaVolo(volo1);
                utente.cercaPrenotazioni(111);
            }
            else System.out.println("Errore: Accesso non riuscito");
        }
        else System.out.println("Errore: Accesso non riuscito");*/
    }
}