package peaksoft.restaurant.exception;

public class AlreadyException extends RuntimeException{
    public AlreadyException(String message) {
        super(message);
    }
}