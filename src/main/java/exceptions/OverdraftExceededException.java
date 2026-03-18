package exceptions;

/**
 * Exception thrown when a checking account exceeds its overdraft limit.
 */
public class OverdraftExceededException extends Exception {

  /**
   * Constructs a new OverdraftExceededException with the specified detail message.
   *
   * @param message the detail message.
   */

  public OverdraftExceededException(String message) {
    super(message);
  }
}

