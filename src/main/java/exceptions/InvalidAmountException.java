package exceptions;

/**
 * Exception thrown when an invalid amount is provided for a transaction.
 */
public class InvalidAmountException extends Exception {

  /**
   * Constructs a new InvalidAmountException with the specified detail message.
   *
   * @param message the detail message.
   */

  public InvalidAmountException(String message) {
    super(message);
  }
}

