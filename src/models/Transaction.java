package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;

    private static int transactionCounter = 0;

    private static String generateTransactionId() {
        transactionCounter++;
        return String.format("TXN%03d", transactionCounter);
    }

    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this.transactionId = generateTransactionId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = getCurrentTimestamp();
    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
        return now.format(formatter);
    }

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
    public String getTimestamp() {
        return timestamp;
    }
}