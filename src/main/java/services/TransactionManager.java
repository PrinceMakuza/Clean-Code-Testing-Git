package services;

import models.Transaction;
import models.Account;
import exceptions.*;
import utils.ValidationUtils;

/**
 * Manages transaction recording, retrieval, and processing.
 */
public class TransactionManager {
    private final Transaction[] transactions;
    private int transactionCount;
    private static final int MAX_TRANSACTIONS = 200;

    public TransactionManager() {
        this.transactions = new Transaction[MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }

    /**
     * Records a new transaction in the system.
     * @param transaction The transaction to add.
     * @return true if successful, false if capacity reached.
     */
    public boolean addTransaction(Transaction transaction) {
        if (transactionCount < MAX_TRANSACTIONS) {
            transactions[transactionCount] = transaction;
            transactionCount++;
            return true;
        }
        return false;
    }

    /**
     * Performs a transfer between two accounts.
     * @param fromAccount The source account.
     * @param toAccount The destination account.
     * @param amount The amount to transfer.
     * @throws InvalidAmountException if amount is invalid.
     * @throws InsufficientFundsException if source account has insufficient funds.
     * @throws OverdraftExceededException if overdraft limit is exceeded.
     */
    public void transfer(Account fromAccount, Account toAccount, double amount)
            throws InvalidAmountException, InsufficientFundsException, OverdraftExceededException {
        
        ValidationUtils.validatePositiveAmount(amount);
        
        // Execute Withdrawal
        fromAccount.withdraw(amount);
        addTransaction(new Transaction(fromAccount.getAccountNumber(), "TRANSFER-OUT", amount, fromAccount.getBalance()));
        
        // Execute Deposit
        toAccount.deposit(amount);
        addTransaction(new Transaction(toAccount.getAccountNumber(), "TRANSFER-IN", amount, toAccount.getBalance()));
    }

    /**
     * Returns the array of transactions.
     * @return The transaction array.
     */
    public Transaction[] getTransactions() {
        return transactions;
    }

    /**
     * Returns the current number of transactions.
     * @return Transaction count.
     */
    public int getTransactionCount() {
        return transactionCount;
    }

    // Helper methods for calculations could go here if needed by other services
}