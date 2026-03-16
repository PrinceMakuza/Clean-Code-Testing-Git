package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a financial transaction within the banking system.
 */
public class Transaction {
    private final String transactionId;
    private final String accountNumber;
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final String timestamp;

    private static int transactionCounter = 0;

    /**
     * Generates a unique transaction ID.
     * @return Formatted transaction ID string.
     */
    private static synchronized String generateTransactionId() {
        transactionCounter++;
        return String.format("TXN%03d", transactionCounter);
    }

    /**
     * Constructs a new Transaction.
     * @param accountNumber The account associated with the transaction.
     * @param type The type of transaction (e.g., DEPOSIT, WITHDRAWAL).
     * @param amount The transaction amount.
     * @param balanceAfter The resulting balance after the transaction.
     */
    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this.transactionId = generateTransactionId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = getCurrentTimestamp();
    }

    /**
     * Gets the current formatted timestamp.
     * @return Formatted string of the current date and time.
     */
    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
        return now.format(formatter);
    }

    /**
     * Displays summary details for the transaction in a formatted table row.
     */
    public void displayTransactionDetails() {
        System.out.printf("%-8s | %-20s | %-10s | %-11.2f | %8.2f%n",
                transactionId, timestamp, type, amount, balanceAfter);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getTimestamp() {
        return timestamp;
    }
}