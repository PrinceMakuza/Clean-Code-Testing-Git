package models;

import exceptions.*;

/**
 * Represents a checking account with an overdraft limit and monthly fees.
 */
public class CheckingAccount extends Account {
  private static final double OVERDRAFT_LIMIT = 1000.0;
  private static final double MONTHLY_FEE = 10.0;

  /**
   * Constructs a new CheckingAccount.
   *
   * @param customer The owner of the account.
   * @param initialDeposit The initial deposit amount.
   */
  public CheckingAccount(Customer customer, double initialDeposit) {
    super(customer, initialDeposit);
  }

  @Override
  public void displayAccountDetails() {
    System.out.println("Account Number: " + getAccountNumber());
    System.out.println("Customer: " + getCustomer().getName() + " ("
        + getCustomer().getCustomerType() + ")");
    System.out.println("Account Type: Checking");
    System.out.printf("Balance: $%.2f%n", getBalance());
    System.out.printf("Overdraft Limit: $%.2f%n", OVERDRAFT_LIMIT);
    System.out.printf("Monthly Fee: $%.2f%n", getMonthlyFee());
    System.out.println("Status: " + getStatus());
  }

  @Override
  public String getAccountType() {
    return "Checking";
  }

  @Override
  public void withdraw(double amount) throws InvalidAmountException, OverdraftExceededException {
    validateAmount(amount);
    validateOverdraft(amount);
    applyTransaction(-amount);
  }

  /**
   * Validates if the withdrawal is within the overdraft limit.
   *
   * @param amount The amount to withdraw.
   * @throws OverdraftExceededException if withdrawal exceeds overdraft limit.
   */
  private void validateOverdraft(double amount) throws OverdraftExceededException {
    if (getBalance() - amount < -OVERDRAFT_LIMIT) {
      throw new OverdraftExceededException(
          "Overdraft limit exceeded. Maximum overdraft is $" + OVERDRAFT_LIMIT);
    }
  }

  /**
   * Gets the overdraft limit.
   *
   * @return The overdraft limit.
   */
  public double getOverdraftLimit() {
    return OVERDRAFT_LIMIT;
  }

  /**
   * Gets the monthly fee, considering fee waivers.
   *
   * @return The monthly fee.
   */
  public double getMonthlyFee() {
    return getCustomer().hasWaivedFees() ? 0.0 : MONTHLY_FEE;
  }

  /**
   * Applies the monthly fee to the account balance if not waived.
   */
  public void applyMonthlyFee() {
    if (!getCustomer().hasWaivedFees()) {
      applyTransaction(-getMonthlyFee());
    }
  }
}