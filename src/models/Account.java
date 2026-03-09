package models;

public abstract class Account implements Transactable {
    // Private fields - encapsulation
    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;

    // Static field for ID generation
    private static int accountCounter = 0;

    // Constructor
    public Account(Customer customer, double initialDeposit) {
        this.accountNumber = generateAccountNumber();
        this.customer = customer;
        this.balance = initialDeposit;
        this.status = "Active";
    }

    // Static method to generate unique account number
    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("ACC%03d", accountCounter);
    }

    // Abstract methods
    public abstract void displayAccountDetails();
    public abstract String getAccountType();

    // Concrete methods
    public boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

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

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }

    public void setBalance(double balance) { this.balance = balance; }
    public void setStatus(String status) { this.status = status; }

    // Reset counter for testing
    public static void resetCounter() {
        accountCounter = 0;
    }
}