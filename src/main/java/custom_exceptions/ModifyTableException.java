package custom_exceptions;

public class ModifyTableException extends RuntimeException {
    public ModifyTableException(String message) {
        super(message);
    }
}
