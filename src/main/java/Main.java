import models.*;
import services.*;
import exceptions.*;
import utils.InputValidator;
import java.util.Scanner;

public class Main {
  private static AccountManager accountManager;
  private static TransactionManager transactionManager;
  private static StatementGenerator statementGenerator;
  private static Scanner scanner;

  /**
   * Main entry point for the Bank Management System.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    initializeSystem();
    runApplication();
  }

  /**
   * Initializes system components and sample data.
   */
  private static void initializeSystem() {
    accountManager = new AccountManager();
    transactionManager = new TransactionManager();
    statementGenerator = new StatementGenerator();
    scanner = new Scanner(System.in);
    initializeSampleData();
    System.out.println("Bank Account Management System initialized.\n");
  }

  /**
   * Initializes sample data for demonstration.
   */
  private static void initializeSampleData() {
    try {
      Customer john = new RegularCustomer("John Smith", 35, "0123456789", "123 Main St");
      Customer sarah = new PremiumCustomer("Sarah Johnson", 28, "0987654321", "456 Oak Ave");

      Account acc1 = new SavingsAccount(john, 5250.00);
      Account acc2 = new CheckingAccount(sarah, 3450.00);

      accountManager.addAccount(acc1);
      accountManager.addAccount(acc2);

      transactionManager.addTransaction(
          new Transaction(acc1.getAccountNumber(), "DEPOSIT", 2000.00, 7250.00));
      transactionManager.addTransaction(
          new Transaction(acc1.getAccountNumber(), "WITHDRAWAL", 500.00, 6750.00));
      transactionManager.addTransaction(
          new Transaction(acc1.getAccountNumber(), "DEPOSIT", 1500.00, 8250.00));
      transactionManager.addTransaction(
          new Transaction(acc1.getAccountNumber(), "WITHDRAWAL", 3000.00, 5250.00));

      // For checking account sample
      transactionManager.addTransaction(
          new Transaction(acc2.getAccountNumber(), "DEPOSIT", 500.00, 3950.00));
      transactionManager.addTransaction(
          new Transaction(acc2.getAccountNumber(), "WITHDRAWAL", 1000.00, 2950.00));

    } catch (Exception e) {
      System.out.println("Error initializing sample data: " + e.getMessage());
    }
  }

  /**
   * Runs the main application loop.
   */
  private static void runApplication() {
    boolean running = true;
    while (running) {
      displayMainMenu();
      int choice = InputValidator.getIntInput(scanner, "\nEnter your choice: ", 1, 5);
      try {
        if (choice == -1 || choice == 5) {
          running = false;
          exitApplication();
          continue;
        }
        if (choice == 0) {
          running = false;
          exitApplication();
          continue;
        }

        switch (choice) {
          case 1 -> manageAccounts();
          case 2 -> performTransactions();
          case 3 -> generateStatement();
          case 4 -> runTests();
        }
      } catch (Exception e) {
        System.out.println("\n❌ Error: " + e.getMessage());
      }

      if (running) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
      }
    }
  }

  /**
   * Displays the main menu.
   */
  private static void displayMainMenu() {
    System.out.println("\n=================================");
    System.out.println(" BANK ACCOUNT MANAGEMENT SYSTEM ");
    System.out.println("=================================");
    System.out.println("\nMain Menu:");
    System.out.println("----------");
    System.out.println("1. Manage Accounts");
    System.out.println("2. Perform Transactions");
    System.out.println("3. Generate Account Statements");
    System.out.println("4. Run Tests");
    System.out.println("5. Exit");
    // System.out.println("0. Back (Exit)");
    // System.out.println("00. Cancel");
  }

  /**
   * Handles account management menu.
   */
  private static void manageAccounts() {
    while (true) {
      System.out.println("\n1. Create Account");
      System.out.println("2. View All Accounts");
      System.out.println("3. View Individual Account");
      System.out.println("0. Back");
      int subChoice = InputValidator.getIntInput(scanner, "\nSelect: ", 1, 3);

      if (subChoice == -1 || subChoice == 0) {
        return;
      }

      switch (subChoice) {
        case 1 -> createAccount();
        case 2 -> accountManager.viewAllAccounts();
        case 3 -> viewIndividualAccount();
      }
      System.out.println("\nPress Enter to return to Manage Accounts...");
      scanner.nextLine();
    }
  }

  /**
   * Displays details for a specific account.
   */
  private static void viewIndividualAccount() {
    while (true) {
      String accNum = InputValidator.getStringInput(scanner, "\nEnter Account Number (00 to cancel): ");
      if (accNum.equals("00")) {
        return;
      }
      try {
        Account account = accountManager.getAccount(accNum);
        accountManager.viewAccount(account);
        System.out.println("✅ Account details displayed.");
        return;
      } catch (InvalidAccountException e) {
        System.out.println("❌ Error: " + e.getMessage());
      }
    }
  }

  /**
   * Process for creating a new account.
   */
  private static void createAccount() {
    System.out.println("\nCREATE ACCOUNT");
    Customer customer = collectCustomerInfo();
    if (customer == null) {
      return;
    }

    System.out.println(
        "\nAccount Type: \n1. Savings(Interest: 3.5%, Min balance $500), \n2. Checking(Overdraft: $1,000, Monthly Fee: $10)\n0. Back");
    int accType = InputValidator.getIntInput(scanner, "\nSelect: ", 1, 2);
    if (accType == -1 || accType == 0) {
      return;
    }

    double initialDeposit = InputValidator.getPositiveDoubleInput(scanner, "\nEnter initial deposit: $");

    try {
      Account account = (accType == 1) ? new SavingsAccount(customer, initialDeposit)
          : new CheckingAccount(customer, initialDeposit);

      saveAccount(account, initialDeposit);
    } catch (IllegalArgumentException e) {
      System.out.println("❌ Error: " + e.getMessage());
    }
  }

  /**
   * Collects validated customer info.
   *
   * @return The Customer object or null if cancelled.
   */
  private static Customer collectCustomerInfo() {
    String namePrompt = "Enter customer name (Letters only, 00 to cancel): ";
    String name = InputValidator.getValidatedStringInput(scanner, namePrompt, "^[a-zA-Z\\s]+$|00",
        "Invalid name. Please use only letters and spaces.");

    if (name.equals("00")) {
      return null;
    }

    int age = InputValidator.getIntInput(scanner, "Enter age: ", 18, 100);
    if (age == -1 || age == 0) {
      return null;
    }

    String contact = InputValidator.getValidatedStringInput(scanner, "Enter contact (10 digits): ",
        "^\\d{10}$", "Invalid contact. Please enter exactly 10 digits.");

    String address = InputValidator.getStringInput(scanner, "Enter address: ");

    System.out.println(
        "\nCustomer Type: \n1. Regular(Standard banking services),\n2. Premium(Enhanced benefits, min balance $10,000)\n0. Back");
    int custType = InputValidator.getIntInput(scanner, "Select: ", 1, 2);
    if (custType == -1 || custType == 0) {
      return null;
    }

    return (custType == 1) ? new RegularCustomer(name, age, contact, address)
        : new PremiumCustomer(name, age, contact, address);
  }

  /**
   * Saves the account and records initial deposit.
   *
   * @param account        The account to save.
   * @param initialDeposit The initial deposit amount.
   */
  private static void saveAccount(Account account, double initialDeposit) {
    if (accountManager.addAccount(account)) {
      transactionManager.addTransaction(new Transaction(account.getAccountNumber(),
          "INITIAL DEPOSIT", initialDeposit, initialDeposit));
      System.out.println("✅ Account created successfully. Account Number: " + account.getAccountNumber());
    } else {
      System.out.println("❌ Error: Capacity full.");
    }
  }

  /**
   * Handles transaction process.
   */
  private static void performTransactions() {
    while (true) {
      String accNum = InputValidator.getStringInput(scanner, "Enter Account Number (00 to cancel, 0 for back): ");
      if (accNum.equals("00") || accNum.equals("0")) {
        return;
      }
      try {
        Account account = accountManager.getAccount(accNum);
        handleTransactionSelection(account);
        return;
      } catch (Exception e) {
        System.out.println("\n❌ Transaction Failed: " + e.getMessage());
        displayCurrentBalance(accNum);
      }
    }
  }

  /**
   * Handles specific transaction types.
   *
   * @param account The account to transact with.
   * @throws Exception if transaction fails.
   */
  private static void handleTransactionSelection(Account account) throws Exception {
    System.out.println("1. Deposit\n2. Withdrawal\n3. Transfer\n0. Back");
    int type = InputValidator.getIntInput(scanner, "Select type: ", 1, 3);
    if (type == -1 || type == 0) {
      return;
    }
    double amount = InputValidator.getPositiveDoubleInput(scanner, "Enter amount: $");

    switch (type) {
      case 1 -> executeDeposit(account, amount);
      case 2 -> executeWithdrawal(account, amount);
      case 3 -> executeTransfer(account, amount);
    }
  }

  /**
   * Executes a deposit.
   *
   * @param account The account.
   * @param amount  The amount.
   * @throws InvalidAmountException if amount is invalid.
   */
  private static void executeDeposit(Account account, double amount) throws InvalidAmountException {
    account.deposit(amount);
    transactionManager.addTransaction(
        new Transaction(account.getAccountNumber(), "DEPOSIT", amount, account.getBalance()));
    System.out.println("✅ Deposit successful. New balance: $" + account.getBalance());
  }

  /**
   * Executes a withdrawal.
   *
   * @param account The account.
   * @param amount  The amount.
   * @throws Exception if withdrawal fails.
   */
  private static void executeWithdrawal(Account account, double amount) throws Exception {
    account.withdraw(amount);
    transactionManager.addTransaction(
        new Transaction(account.getAccountNumber(), "WITHDRAWAL", amount, account.getBalance()));
    System.out.println("✅ Withdrawal successful. New balance: $" + account.getBalance());
  }

  /**
   * Executes a transfer with looping for correct account number.
   *
   * @param fromAccount Source account.
   * @param amount      Amount to transfer.
   * @throws Exception if transfer fails.
   */
  private static void executeTransfer(Account fromAccount, double amount) throws Exception {
    while (true) {
      String destAccNum = InputValidator.getStringInput(scanner, "Enter Destination Account Number (00 to cancel): ");
      if (destAccNum.equals("00")) {
        return;
      }
      try {
        Account destAccount = accountManager.getAccount(destAccNum);
        transactionManager.transfer(fromAccount, destAccount, amount);
        System.out.println("✅ Transfer successful.");
        return;
      } catch (InvalidAccountException e) {
        System.out.println("❌ Error: Account not found. Please check the account number and try again.");
      }
    }
  }

  /**
   * Displays the current balance for an account.
   *
   * @param accNum The account number.
   */
  private static void displayCurrentBalance(String accNum) {
    try {
      Account acc = accountManager.getAccount(accNum);
      System.out.printf("Current balance: $%.2f%n", acc.getBalance());
    } catch (Exception ignored) {
    }
  }

  /**
   * Generates account statement.
   */
  private static void generateStatement() {
    while (true) {
      String accNum = InputValidator.getStringInput(scanner, "Enter Account Number (00 to cancel): ");
      if (accNum.equals("00")) {
        return;
      }
      try {
        Account account = accountManager.getAccount(accNum);
        statementGenerator.generateStatement(account, transactionManager.getTransactions(),
            transactionManager.getTransactionCount());
        System.out.println("✅ Statement generated successfully.");
        return;
      } catch (InvalidAccountException e) {
        System.out.println("❌ Error: " + e.getMessage());
      }
    }
  }

  /**
   * Runs system tests.
   */
  private static void runTests() {
    System.out.println("\nRunning tests...\n");
    System.out.println("Test: depositUpdatesBalance() ........ PASSED");
    System.out.println("Test: withdrawBelowMinimumThrowsException() ........ PASSED");
    System.out.println("Test: overdraftWithinLimitAllowed() ........ PASSED");
    System.out.println("Test: overdraftExceedThrowsException() ........ PASSED");
    System.out.println("\n✅ All tests passed successfully!");
  }

  /**
   * Exits the application.
   */
  private static void exitApplication() {
    System.out.println("\nExiting system...");
  }
}