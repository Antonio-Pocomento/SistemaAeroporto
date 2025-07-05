package custom_exceptions;

/**
 * La classe {@code UserAlreadyExistsException} si occupa di gestire l'eccezione per la verifica di utente già esistente
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Costruttore UserAlreadyExistsException
     *
     * @param message il messaggio di eccezione
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
