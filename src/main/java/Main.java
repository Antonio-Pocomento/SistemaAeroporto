import model.Amministratore;

import java.time.Instant;
import java.time.LocalTime;
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
        }
    }
}