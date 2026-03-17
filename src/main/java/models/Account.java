package models;

import interfaces.Transactable;
import exceptions.*;

/**
 * Abstract base class representing a generic bank account.
 * Implements clean code principles and modular method design.
 */
public abstract class Account implements Transactable {
  private final String accountNumber;
  private final Customer customer;
  private double balance;
  private String status;

  private static int accountCounter = 0;

  /**
   * Generates a unique account number.
   *
   * @return Formatted account number string.
   */
  private static synchronized String generateAccountNumber() {
    accountCounter++;
    return String.format("ACC%03d", accountCounter);
  }

  /**
   * Constructs a new Account with a customer and initial deposit.
   *
   * @param customer The owner of the account.
   * @param initialDeposit The initial amount to deposit.
   */
  public Account(Customer customer, double initialDeposit) {
    this.accountNumber = generateAccountNumber();
    this.customer = customer;
    this.balance = initialDeposit;
    this.status = "Active";
  }

  /**
   * Displays the details of the account.
   */
  public abstract void displayAccountDetails();

  /**
   * Returns the type of the account.
   *
   * @return Account type as a string.
   */
  public abstract String getAccountType();

  /**
   * Deposits an amount into the account.
   *
   * @param amount The amount to deposit.
   * @throws InvalidAmountException if amount is less than or equal to 0.
   */
  public void deposit(double amount) throws InvalidAmountException {
    validateAmount(amount);
    applyTransaction(amount);
  }

  /**
   * Validates that the transaction amount is positive.
   *
   * @param amount The amount to validate.
   * @throws InvalidAmountException if amount is invalid.
   */
  protected void validateAmount(double amount) throws InvalidAmountException {
    if (amount <= 0) {
      throw new InvalidAmountException("Invalid amount. Amount must be greater than 0.");
    }
  }

  /**
   * Updates the account balance.
   *
   * @param amount The amount to add (positive or negative).
   */
  protected void applyTransaction(double amount) {
    this.balance += amount;
  }

  /**
   * Withdraws an amount from the account.
   *
   * @param amount The amount to withdraw.
   * @throws InvalidAmountException if amount is invalid.
   * @throws InsufficientFundsException if funds are insufficient.
   * @throws OverdraftExceededException if overdraft limit is exceeded.
   */
  public abstract void withdraw(double amount)
      throws InvalidAmountException, InsufficientFundsException, OverdraftExceededException;

  @Override
  public void processTransaction(double amount, String type)
      throws InvalidAmountException, InsufficientFundsException, OverdraftExceededException {
    if (type.equalsIgnoreCase("DEPOSIT")) {
      deposit(amount);
    } else if (type.equalsIgnoreCase("WITHDRAWAL")) {
      withdraw(amount);
    }
  }

  /**
   * Gets the account number.
   *
   * @return The account number.
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Gets the customer associated with the account.
   *
   * @return The customer.
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Gets the current balance.
   *
   * @return The balance.
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Gets the account status.
   *
   * @return The status.
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the account balance.
   *
   * @param balance The new balance.
   */
  protected void setBalance(double balance) {
    this.balance = balance;
  }
}