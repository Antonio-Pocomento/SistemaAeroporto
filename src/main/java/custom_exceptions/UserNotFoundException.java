package custom_exceptions;

/**
 *  La classe {@code UserNotFoundException} si occupa di gestire l'eccezione per utente non trovato
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Costruttore UserNotFoundException
     *
     * @param message il messaggio di eccezione
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
