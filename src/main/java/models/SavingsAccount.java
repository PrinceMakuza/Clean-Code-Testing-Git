package models;

import exceptions.*;

/**
 * Represents a savings account with a minimum balance requirement and interest rate.
 */
public class SavingsAccount extends Account {
  private static final double INTEREST_RATE = 3.5;
  private static final double MINIMUM_BALANCE = 500.0;

  /**
   * Constructs a new SavingsAccount.
   *
   * @param customer The owner of the account.
   * @param initialDeposit The initial deposit amount.
   * @throws IllegalArgumentException if initial deposit is below minimum balance.
   */
  public SavingsAccount(Customer customer, double initialDeposit) {
    super(customer, initialDeposit);
    if (initialDeposit < MINIMUM_BALANCE) {
      throw new IllegalArgumentException(
          "Savings account requires minimum balance of $" + MINIMUM_BALANCE);
    }
  }

  @Override
  public void displayAccountDetails() {
    System.out.println("Account Number: " + getAccountNumber());
    System.out.println("Customer: " + getCustomer().getName() + " ("
        + getCustomer().getCustomerType() + ")");
    System.out.println("Account Type: Savings");
    System.out.printf("Balance: $%.2f%n", getBalance());
    System.out.println("Interest Rate: " + INTEREST_RATE + "%");
    System.out.printf("Minimum Balance: $%.2f%n", MINIMUM_BALANCE);
    System.out.println("Status: " + getStatus());
  }

  @Override
  public String getAccountType() {
    return "Savings";
  }

  @Override
  public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
    validateAmount(amount);
    validateWithdrawal(amount);
    applyTransaction(-amount);
  }

  /**
   * Validates if the withdrawal is possible without dropping below minimum balance.
   *
   * @param amount The amount to withdraw.
   * @throws InsufficientFundsException if withdrawal violates minimum balance.
   */
  private void validateWithdrawal(double amount) throws InsufficientFundsException {
    if (getBalance() - amount < MINIMUM_BALANCE) {
      throw new InsufficientFundsException(
          "Insufficient funds. Withdrawal would drop balance below minimum requirement of $"
          + MINIMUM_BALANCE);
    }
  }

  /**
   * Gets the minimum balance requirement.
   *
   * @return The minimum balance.
   */
  public double getMinimumBalance() {
    return MINIMUM_BALANCE;
  }

  /**
   * Calculates interest based on the current balance.
   *
   * @return The calculated interest amount.
   */
  public double calculateInterest() {
    return getBalance() * (INTEREST_RATE / 100);
  }
}