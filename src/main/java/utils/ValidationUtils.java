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
     * @param amount The amount to validate.
     * @throws InvalidAmountException if amount is less than or equal to zero.
     */
    public static void validatePositiveAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Error: Invalid amount. Amount must be greater than 0.");
        }
    }

    /**
     * Validates that an account is not null.
     * @param account The account to validate.
     * @throws InvalidAccountException if account is null.
     */
    public static void validateAccountExists(Account account) throws InvalidAccountException {
        if (account == null) {
            throw new InvalidAccountException("Error: Account not found. Please check the account number and try again.");
        }
    }
}
