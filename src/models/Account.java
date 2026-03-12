package models;

import interfaces.Transactable;

public abstract class Account implements Transactable {
    private final String accountNumber;
    private Customer customer;
    private double balance;
    private String status;

    private static int accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("ACC%03d", accountCounter);
    }

    // Constructor
    public Account(Customer customer, double initialDeposit) {
        this.accountNumber = generateAccountNumber();
        this.customer = customer;
        this.balance = initialDeposit;
        this.status = "Active";
    }

    public abstract void displayAccountDetails();
    public abstract String getAccountType();

    // Deposit Method
    public boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    // Abstract Method for Withdraw
    public abstract boolean withdraw(double amount);

    @Override
    public boolean processTransaction(double amount, String type) {
        if (type.equalsIgnoreCase("DEPOSIT")) {
            return deposit(amount);
        } else if (type.equalsIgnoreCase("WITHDRAWAL")) {
            return withdraw(amount);
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public Customer getCustomer() {
        return customer;
    }
    public double getBalance() {
        return balance;
    }
    public String getStatus() {
        return status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Reset counter for testing
    public static void resetCounter() {
        accountCounter = 0;
    }
}