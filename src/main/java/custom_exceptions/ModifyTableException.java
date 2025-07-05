package custom_exceptions;

/**
 * La classe {@code ModifyTableException} si occupa di gestire l'eccezione per modifiche alle tabelle
 */
public class ModifyTableException extends RuntimeException {
    /**
     * Costruttore ModifyTableException
     *
     * @param message il messaggio di eccezione
     */
    public ModifyTableException(String message) {
        super(message);
    }
}
