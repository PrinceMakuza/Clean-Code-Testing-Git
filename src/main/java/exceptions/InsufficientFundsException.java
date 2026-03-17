package exceptions;

/**
 * Exception thrown when an account has insufficient funds for a withdrawal or transfer.
 */
public class InsufficientFundsException extends Exception {

  /**
   * Constructs a new InsufficientFundsException with the specified detail message.
   *
   * @param message the detail message.
   */
  public InsufficientFundsException(String message) {
    super(message);
  }
}

