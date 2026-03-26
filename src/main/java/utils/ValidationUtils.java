package utils;

import exceptions.InvalidAmountException;
import exceptions.InvalidAccountException;
import models.Account;

/**
 * Utility class for validating banking operations.
 */
public class ValidationUtils {

  /**
   * Validates that an amount is greater than zero.
   *
   * @param amount The amount to validate.
   * @throws InvalidAmountException if amount is less than or equal to zero.
   */
  public static void validatePositiveAmount(double amount) throws InvalidAmountException {
    if (amount <= 0) {
      throw new InvalidAmountException("Invalid amount. Amount must be greater than 0.\n");
    }
  }

  /**
   * Validates that an account exists.
   *
   * @param account The account to check.
   * @throws InvalidAccountException if the account is null.
   */
  public static void validateAccountExists(Object account) throws InvalidAccountException {
    if (account == null) {
      throw new InvalidAccountException(
          "Account not found. Please check the account number and try again.\n");
    }
  }
}
