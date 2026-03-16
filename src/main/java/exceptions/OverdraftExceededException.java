package exceptions;

/**
 * Exception thrown when a checking account exceeds its overdraft limit.
 */
public class OverdraftExceededException extends Exception {
    public OverdraftExceededException(String message) {
        super(message);
    }
}
