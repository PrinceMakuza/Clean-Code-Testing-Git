package exceptions;

/**
 * Exception thrown when an account number is not found or is invalid.
 */
public class InvalidAccountException extends Exception {

  /**
   * Constructs a new InvalidAccountException with the specified detail message.
   *
   * @param message the detail message.
   */

  public InvalidAccountException(String message) {
    super(message);
  }
}

