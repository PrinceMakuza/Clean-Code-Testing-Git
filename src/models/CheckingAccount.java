package models;

public class CheckingAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000.0;
    private static final double MONTHLY_FEE = 10.0;

    public CheckingAccount(Customer customer, double initialDeposit) {
        super(customer, initialDeposit);
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Customer: " + getCustomer().getName() + " (" + getCustomer().getCustomerType() + ")");
        System.out.println("Account Type: Checking");
        System.out.printf("Balance: $%.2f%n", getBalance());
        System.out.printf("Overdraft Limit: $%.2f%n", OVERDRAFT_LIMIT);
        System.out.printf("Monthly Fee: $%.2f%n",
                getCustomer().hasWaivedFees() ? 0.0 : MONTHLY_FEE);
        System.out.println("Status: " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    public boolean withdraw(double amount) {
        double newBalance = getBalance() - amount;

        // Check if withdrawal exceeds overdraft limit
        if (newBalance >= -OVERDRAFT_LIMIT) {
            setBalance(newBalance);
            return true;
        }
        return false;
    }

    public void applyMonthlyFee() {
        if (!getCustomer().hasWaivedFees()) {
            setBalance(getBalance() - MONTHLY_FEE);
        }
    }

    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }

    public double getMonthlyFee() {
        return getCustomer().hasWaivedFees() ? 0.0 : MONTHLY_FEE;
    }
}