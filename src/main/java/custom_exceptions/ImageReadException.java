package custom_exceptions;

public class ImageReadException extends RuntimeException {
    public ImageReadException(String message) {
        super(message);
    }
}
