package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static int transactionCounter = 0;

    private String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;

    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this.transactionId = generateTransactionId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = getCurrentTimestamp();
    }

    private static String generateTransactionId() {
        transactionCounter++;
        return String.format("TXN%03d", transactionCounter);
    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
        return now.format(formatter);
    }

    public void displayTransactionDetails() {
        System.out.printf("%-8s %-20s %-10s %+10.2f %12.2f%n",
                transactionId,
                timestamp,
                type,
                amount,
                balanceAfter
        );
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }

    // Reset counter for testing
    public static void resetCounter() {
        transactionCounter = 0;
    }
}