package exceptions;

/**
 * Exception thrown when an account number is not found or is invalid.
 */
public class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
}
