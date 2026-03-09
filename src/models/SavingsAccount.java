package models;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 3.5; // 3.5% annually
    private static final double MINIMUM_BALANCE = 500.0;

    public SavingsAccount(Customer customer, double initialDeposit) {
        super(customer, initialDeposit);

        // Validate minimum balance requirement
        if (initialDeposit < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Savings account requires minimum balance of $" + MINIMUM_BALANCE);
        }
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Customer: " + getCustomer().getName() + " (" + getCustomer().getCustomerType() + ")");
        System.out.println("Account Type: Savings");
        System.out.printf("Balance: $%.2f%n", getBalance());
        System.out.println("Interest Rate: " + INTEREST_RATE + "% annually");
        System.out.printf("Minimum Balance: $%.2f%n", MINIMUM_BALANCE);
        System.out.println("Status: " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public boolean withdraw(double amount) {
        double newBalance = getBalance() - amount;

        // Check if withdrawal would maintain minimum balance
        if (newBalance >= MINIMUM_BALANCE) {
            setBalance(newBalance);
            return true;
        }
        return false;
    }

    public double calculateInterest() {
        return getBalance() * (INTEREST_RATE / 100);
    }

    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}