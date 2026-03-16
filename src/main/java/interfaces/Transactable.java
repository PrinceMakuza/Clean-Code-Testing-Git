package interfaces;

import exceptions.*;

/**
 * Interface for objects that can process transactions.
 */
public interface Transactable {
    /**
     * Processes a transaction.
     * @param amount The amount for the transaction.
     * @param type The type of transaction (DEPOSIT, WITHDRAWAL).
     * @throws InvalidAmountException if amount is invalid.
     * @throws InsufficientFundsException if funds are insufficient.
     * @throws OverdraftExceededException if overdraft limit is exceeded.
     */
    void processTransaction(double amount, String type) throws InvalidAmountException, InsufficientFundsException, OverdraftExceededException;
}