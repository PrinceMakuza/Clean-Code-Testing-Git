package exceptions;

/**
 * Exception thrown when an account has insufficient funds for a withdrawal or transfer.
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
