package custom_exceptions;

/**
 *  La classe {@code EmailAlreadyExistsException} si occupa di gestire l'eccezione per la verifica di email già esistente
 */
public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * Costruttore EmailAlreadyExistsException
     *
     * @param message il messaggio di eccezione
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
