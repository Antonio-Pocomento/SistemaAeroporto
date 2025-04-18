import model.Amministratore;
import model.UtenteGenerico;
import model.Volo;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nBenvenuto. Accedi utilizzando il tuo Nome Utente o Email e la Password.");
        System.out.println("Se non hai un account utente, puoi crearne uno!");
        System.out.println("\nCreazione account admin");
        Amministratore admin = new Amministratore("admin", "admin@gmail.com", "admin");
        System.out.println("Login con account admin");
        if(admin.loginUtente("admin", "admin"))
        {
            System.out.println("\nAccesso riuscito!");
            admin.inserisciVolo(1,"AEROITALIA","Napoli","Milano",
                    Date.from(Instant.now()), LocalTime.now(),LocalTime.of(0,0),"OK",1);
            admin.inserisciVolo(2,"AEROITALIA","Palermo","Napoli",
                    Date.from(Instant.now()), LocalTime.now(),LocalTime.of(1,0),"OK",null);
            admin.visualizzaVoli();
            ArrayList<Volo> voli = Volo.getVoli();
            admin.modificaVolo(voli.get(0));
            admin.visualizzaVoli();
            UtenteGenerico utente = new UtenteGenerico("Ciro", "ciro@gmail.com", "ciro");
            utente.prenotaVolo(voli.get(1));
            utente.prenotaVolo(voli.get(0));
            utente.printPrenotazioni();
            utente.segnalaSmarrimento();
            admin.gestisciVolo(voli.get(0));
            admin.visualizzaBagagliSmarriti();
            admin.modificaStatoBagaglio();
            admin.visualizzaBagagliSmarriti();
            utente.modificaPrenotazione(utente.getPrenotazioniUtente().get(0));
            utente.printPrenotazioni();
        }
    }
}